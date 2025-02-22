package com.deichor.questapi.core.storage;

public final class MySQLQueries {
    private MySQLQueries() {
        // Utility class, prevent instantiation
    }

    public static final String CREATE_OWNERS_TABLE = """
        CREATE TABLE IF NOT EXISTS quest_owners (
            owner_id VARCHAR(255),
            owner_type VARCHAR(50),
            owner_content TEXT,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            PRIMARY KEY (owner_id, owner_type)
        )""";

    public static final String CREATE_QUESTS_TABLE = """
        CREATE TABLE IF NOT EXISTS quests (
            quest_id INT PRIMARY KEY,
            owner_id VARCHAR(255),
            owner_type VARCHAR(50),
            quest_data TEXT,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            FOREIGN KEY (owner_id, owner_type) 
                REFERENCES quest_owners(owner_id, owner_type)
                ON DELETE CASCADE,
            INDEX owner_idx (owner_id, owner_type)
        )""";

    public static final String INSERT_OR_UPDATE_OWNER = 
        "INSERT INTO quest_owners (owner_id, owner_type, owner_content) VALUES (?, ?, ?) " +
        "ON DUPLICATE KEY UPDATE owner_content = ?";

    public static final String INSERT_OR_UPDATE_QUEST = 
        "INSERT INTO quests (quest_id, owner_id, owner_type, quest_data) VALUES (?, ?, ?, ?) " +
        "ON DUPLICATE KEY UPDATE quest_data = ?";

    public static final String DELETE_QUEST = 
        "DELETE FROM quests WHERE quest_id = ?";

    public static final String DELETE_OWNER = 
        "DELETE FROM quest_owners WHERE owner_id = ? AND owner_type = ?";

    public static final String SELECT_QUEST = 
        "SELECT q.*, o.owner_content FROM quests q " +
        "JOIN quest_owners o ON q.owner_id = o.owner_id AND q.owner_type = o.owner_type " +
        "WHERE q.quest_id = ?";

    public static final String SELECT_QUESTS_BY_OWNER = 
        "SELECT q.*, o.owner_content FROM quests q " +
        "JOIN quest_owners o ON q.owner_id = o.owner_id AND q.owner_type = o.owner_type " +
        "WHERE q.owner_id = ? AND q.owner_type = ?";

    public static final String SELECT_ALL_QUESTS = 
        "SELECT q.*, o.owner_content FROM quests q " +
        "JOIN quest_owners o ON q.owner_id = o.owner_id AND q.owner_type = o.owner_type";
}