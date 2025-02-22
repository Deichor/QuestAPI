package com.deichor.questapi.core.storage;

public final class MySQLQueries {
    private MySQLQueries() {
        // Utility class, prevent instantiation
    }

    public static final String CREATE_QUESTS_TABLE = """
        CREATE TABLE IF NOT EXISTS quests (
            quest_id INT PRIMARY KEY,
            owner_id VARCHAR(36),
            quest_data TEXT,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        )""";

    public static final String INSERT_OR_UPDATE_QUEST = 
        "INSERT INTO quests (quest_id, owner_id, quest_data) VALUES (?, ?, ?) " +
        "ON DUPLICATE KEY UPDATE quest_data = ?";

    public static final String DELETE_QUEST = 
        "DELETE FROM quests WHERE quest_id = ?";

    public static final String SELECT_QUEST_BY_ID = 
        "SELECT quest_data FROM quests WHERE quest_id = ?";

    public static final String SELECT_QUESTS_BY_OWNER = 
        "SELECT quest_data FROM quests WHERE owner_id = ?";

    public static final String SELECT_ALL_QUESTS = 
        "SELECT quest_data FROM quests";
}