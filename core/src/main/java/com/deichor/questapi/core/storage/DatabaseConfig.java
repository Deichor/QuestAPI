package com.deichor.questapi.core.storage;

public class DatabaseConfig {
    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;
    private final String sqlitePath;
    private final boolean isSqlite;

    public DatabaseConfig(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.sqlitePath = null;
        this.isSqlite = false;
    }

    public DatabaseConfig(String sqlitePath) {
        this.sqlitePath = sqlitePath;
        this.host = null;
        this.port = 0;
        this.database = null;
        this.username = null;
        this.password = null;
        this.isSqlite = true;
    }

    public String getHost() { return host; }
    public int getPort() { return port; }
    public String getDatabase() { return database; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getSqlitePath() { return sqlitePath; }
    public boolean isSqlite() { return isSqlite; }
    
    public String getJdbcUrl() {
        if (isSqlite) {
            return String.format("jdbc:sqlite:%s", sqlitePath);
        }
        return String.format("jdbc:mysql://%s:%d/%s", host, port, database);
    }
}