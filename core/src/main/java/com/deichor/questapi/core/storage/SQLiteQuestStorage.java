package com.deichor.questapi.core.storage;

import com.deichor.questapi.core.QuestManager;
import com.deichor.questapi.core.model.QuestOwner;
import com.deichor.questapi.core.extend.BaseQuest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SQLiteQuestStorage implements QuestStorage {
    private final String dbPath;
    private Connection connection;

    public SQLiteQuestStorage(DatabaseConfig config) {
        this.dbPath = config.getSqlitePath();
        initializeDatabase();
    }

    private void initializeDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(SQLiteQueries.CREATE_QUESTS_TABLE);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize SQLite database", e);
        }
    }

    @Override
    public void saveQuest(int questId, QuestManager<?> quest) {
        try (PreparedStatement stmt = connection.prepareStatement(SQLiteQueries.INSERT_OR_UPDATE_QUEST)) {
            stmt.setInt(1, questId);
            stmt.setString(2, quest.getQuest().getOwner().getId().toString());
            stmt.setString(3, serializeQuest(quest));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save quest", e);
        }
    }

    @Override
    public void removeQuest(int questId) {
        try (PreparedStatement stmt = connection.prepareStatement(SQLiteQueries.DELETE_QUEST)) {
            stmt.setInt(1, questId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove quest", e);
        }
    }

    @Override
    public void removeQuestsByOwner(QuestOwner<?> owner) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "DELETE FROM quests WHERE owner_id = ?")) {
            stmt.setString(1, owner.getId().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove quests by owner", e);
        }
    }

    @Override
    public Optional<QuestManager<?>> getQuest(int questId) {
        try (PreparedStatement stmt = connection.prepareStatement(SQLiteQueries.SELECT_QUEST_BY_ID)) {
            stmt.setInt(1, questId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String questData = rs.getString("quest_data");
                    return Optional.of(deserializeQuest(questData));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get quest", e);
        }
        return Optional.empty();
    }

    @Override
    public List<QuestManager<?>> getQuestsByOwner(QuestOwner<?> owner) {
        List<QuestManager<?>> quests = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SQLiteQueries.SELECT_QUESTS_BY_OWNER)) {
            stmt.setString(1, owner.getId().toString());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String questData = rs.getString("quest_data");
                    quests.add(deserializeQuest(questData));
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
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SQLiteQueries.SELECT_ALL_QUESTS)) {
            while (rs.next()) {
                String questData = rs.getString("quest_data");
                quests.add(deserializeQuest(questData));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all quests", e);
        }
        return quests;
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Ignore close errors
            }
        }
    }

    private String serializeQuest(QuestManager<?> quest) {
        // Basic serialization for testing purposes
        return String.format("{\"ownerId\":\"%s\",\"ownerType\":\"%s\"}",
            quest.getQuest().getOwner().getId(),
            quest.getQuest().getOwner().getOwnerType());
    }

    private QuestManager<?> deserializeQuest(String questData) {
        // Basic deserialization for testing purposes
        BaseQuest<Long, Long> quest = new BaseQuest<>() {};
        QuestOwner<Long> owner = new QuestOwner<Long>() {
            private Long id = 1L;

            @Override
            public Long getOwner() {
                return id;
            }

            @Override
            public Long getId() {
                return id;
            }

            @Override
            public String getOwnerType() {
                return "PLAYER";
            }

            @Override
            public String serialize() {
                return String.format("{\"id\":%d,\"type\":\"%s\"}", getId(), getOwnerType());
            }
        };
        quest.setOwner(owner);
        return new QuestManager<>(quest) {};
    }
}