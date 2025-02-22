package com.deichor.questapi.core;

import com.deichor.questapi.core.storage.DatabaseConfig;
import com.deichor.questapi.core.storage.StorageManager;
import com.deichor.questapi.core.storage.StorageTypes;

public class QuestApiBuilder {
    private StorageTypes storageType = StorageTypes.IN_MEMORY;
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private String sqlitePath;
    private StorageManager storageManager;

    public QuestApiBuilder storageType(StorageTypes storageType) {
        this.storageType = storageType;
        return this;
    }

    public QuestApiBuilder database(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        return this;
    }

    public QuestApiBuilder sqlitePath(String path) {
        this.sqlitePath = path;
        return this;
    }

    public QuestApiBuilder build() {
        DatabaseConfig config = null;
        
        switch (storageType) {
            case MYSQL, MYSQL_AND_MEMORY -> {
                if (host == null || database == null || username == null || password == null) {
                    throw new IllegalStateException("Database configuration is required for MySQL storage type");
                }
                config = new DatabaseConfig(host, port, database, username, password);
            }
            case SQLITE -> {
                if (sqlitePath == null) {
                    throw new IllegalStateException("SQLite path is required for SQLite storage type");
                }
                config = new DatabaseConfig(sqlitePath);
            }
        }
        
        this.storageManager = new StorageManager(storageType, config);
        return this;
    }

    public StorageManager getStorageManager() {
        if (storageManager == null) {
            throw new IllegalStateException("Builder must be built before getting StorageManager");
        }
        return storageManager;
    }

    public static QuestApiBuilder builder() {
        return new QuestApiBuilder();
    }
}
