package com.deichor.questapi.core.storage;

import com.deichor.questapi.core.QuestManager;
import com.deichor.questapi.core.model.QuestOwner;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MySQLQuestStorage implements QuestStorage {
    private final HikariDataSource dataSource;
    private final ExecutorService executorService;

    public MySQLQuestStorage(DatabaseConfig config) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getJdbcUrl());
        hikariConfig.setUsername(config.getUsername());
        hikariConfig.setPassword(config.getPassword());
        
        this.dataSource = new HikariDataSource(hikariConfig);
        this.executorService = Executors.newFixedThreadPool(3);
        initializeDatabase();
    }

    private void initializeDatabase() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(MySQLQueries.CREATE_OWNERS_TABLE);
            stmt.execute(MySQLQueries.CREATE_QUESTS_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    private Long saveOrUpdateOwner(Connection conn, QuestOwner<?> owner) throws SQLException {
        // Check if owner already has an ID in the database using getOwnerId()
        Long ownerId = owner.getId();
        if (ownerId != null) {
            // Look up the database ID using the owner ID
            try (PreparedStatement stmt = conn.prepareStatement("SELECT id FROM quest_owners WHERE owner_id = ? AND owner_type = ?")) {
                stmt.setLong(1, ownerId);
                stmt.setString(2, owner.getOwnerType());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getLong("id");
                    }
                }
            }
        }

        // If we get here, we need to insert a new owner
        try (PreparedStatement stmt = conn.prepareStatement(MySQLQueries.INSERT_OR_UPDATE_OWNER, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, owner.getOwnerType());
            String ownerContent = serializeOwner(owner);
            stmt.setString(2, ownerContent);
            stmt.setString(3, ownerContent);
            stmt.executeUpdate();

            // Get the generated ID
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
                throw new SQLException("Failed to get generated owner ID");
            }
        }
    }

    @Override
    public void saveQuest(int questId, QuestManager<?> quest) {
        CompletableFuture.runAsync(() -> {
            try (Connection conn = dataSource.getConnection()) {
                conn.setAutoCommit(false);
                try {
                    // First save/update the owner and get its ID
                    Long ownerId = saveOrUpdateOwner(conn, quest.getQuest().getOwner());
                    
                    // Then save/update the quest
                    try (PreparedStatement stmt = conn.prepareStatement(MySQLQueries.INSERT_OR_UPDATE_QUEST)) {
                        stmt.setInt(1, questId);
                        stmt.setLong(2, ownerId);
                        stmt.setString(3, quest.getQuest().getOwner().getOwnerType());
                        String serializedQuest = serializeQuest(quest);
                        stmt.setString(4, serializedQuest);
                        stmt.setString(5, serializedQuest);
                        stmt.executeUpdate();
                    }
                    
                    conn.commit();
                } catch (SQLException e) {
                    conn.rollback();
                    throw e;
                } finally {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Failed to save quest", e);
            }
        }, executorService);
    }

    @Override
    public void removeQuest(int questId) {
        CompletableFuture.runAsync(() -> {
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(MySQLQueries.DELETE_QUEST)) {
                stmt.setInt(1, questId);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Failed to remove quest", e);
            }
        }, executorService);
    }

    @Override
    public void removeQuestsByOwner(QuestOwner<?> owner) {
        CompletableFuture.runAsync(() -> {
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(MySQLQueries.DELETE_OWNER)) {
                stmt.setLong(1, owner.getId());
                stmt.setString(2, owner.getOwnerType());
                stmt.executeUpdate(); // Will cascade delete all related quests
            } catch (SQLException e) {
                throw new RuntimeException("Failed to remove quests by owner", e);
            }
        }, executorService);
    }

    @Override
    public Optional<QuestManager<?>> getQuest(int questId) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(MySQLQueries.SELECT_QUEST)) {
            stmt.setInt(1, questId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String questData = rs.getString("quest_data");
                    String ownerContent = rs.getString("owner_content");
                    return Optional.of(deserializeQuest(questData, ownerContent));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get quest", e);
        }
    }

    @Override
    public List<QuestManager<?>> getQuestsByOwner(QuestOwner<?> owner) {
        List<QuestManager<?>> quests = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(MySQLQueries.SELECT_QUESTS_BY_OWNER)) {
            stmt.setLong(1, owner.getId());
            stmt.setString(2, owner.getOwnerType());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String questData = rs.getString("quest_data");
                    String ownerContent = rs.getString("owner_content");
                    quests.add(deserializeQuest(questData, ownerContent));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get quests by owner", e);
        }
        return quests;
    }

    @Override
    public List<QuestManager<?>> getAllQuests() {
        List<QuestManager<?>> quests = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(MySQLQueries.SELECT_ALL_QUESTS)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String questData = rs.getString("quest_data");
                    String ownerContent = rs.getString("owner_content");
                    quests.add(deserializeQuest(questData, ownerContent));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all quests", e);
        }
        return quests;
    }

    @Override
    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    private String serializeQuest(QuestManager<?> quest) {
        // TODO: Implement proper serialization
        return quest.toString();
    }

    private String serializeOwner(QuestOwner<?> owner) {
        return owner.serialize();
    }

    private QuestManager<?> deserializeQuest(String questData, String ownerContent) {
        // TODO: Implement proper deserialization using both quest data and owner content
        throw new UnsupportedOperationException("Deserialization not implemented");
    }
}