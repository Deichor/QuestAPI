package com.deichor.questapi.core.storage;

import com.deichor.questapi.core.QuestManager;
import com.deichor.questapi.core.model.QuestOwner;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLQuestStorage implements QuestStorage {
    private final HikariDataSource dataSource;

    public MySQLQuestStorage(DatabaseConfig config) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getJdbcUrl());
        hikariConfig.setUsername(config.getUsername());
        hikariConfig.setPassword(config.getPassword());
        
        this.dataSource = new HikariDataSource(hikariConfig);
        initializeDatabase();
    }

    private void initializeDatabase() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(MySQLQueries.CREATE_QUESTS_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    @Override
    public void saveQuest(int questId, QuestManager<?> quest) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(MySQLQueries.INSERT_OR_UPDATE_QUEST)) {
            stmt.setInt(1, questId);
            stmt.setString(2, quest.getQuest().getOwner().toString());
            String serializedQuest = serializeQuest(quest);
            stmt.setString(3, serializedQuest);
            stmt.setString(4, serializedQuest);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save quest", e);
        }
    }

    @Override
    public void removeQuest(int questId) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(MySQLQueries.DELETE_QUEST)) {
            stmt.setInt(1, questId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove quest", e);
        }
    }

    @Override
    public void removeQuestsByOwner(QuestOwner<?> owner) {

    }

    @Override
    public Optional<QuestManager<?>> getQuest(int questId) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(MySQLQueries.SELECT_QUEST_BY_ID)) {
            stmt.setInt(1, questId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.ofNullable(deserializeQuest(rs.getString("quest_data")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get quest", e);
        }
        return Optional.empty();
    }

    @Override
    public List<QuestManager<?>> getQuestsByOwner(QuestOwner<?> owner) {
        List<QuestManager<?>> quests = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(MySQLQueries.SELECT_QUESTS_BY_OWNER)) {
            stmt.setString(1, owner.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                QuestManager<?> quest = deserializeQuest(rs.getString("quest_data"));
                if (quest != null) {
                    quests.add(quest);
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
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(MySQLQueries.SELECT_ALL_QUESTS);
            while (rs.next()) {
                QuestManager<?> quest = deserializeQuest(rs.getString("quest_data"));
                if (quest != null) {
                    quests.add(quest);
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
    }

    // These methods would need to be implemented with a proper serialization strategy
    private String serializeQuest(QuestManager<?> quest) {
        // TODO: Implement proper serialization
        return "";
    }

    private QuestManager<?> deserializeQuest(String data) {
        // TODO: Implement proper deserialization
        return null;
    }
}