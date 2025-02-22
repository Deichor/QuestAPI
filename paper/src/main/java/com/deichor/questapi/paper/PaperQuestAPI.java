package com.deichor.questapi.paper;

import com.deichor.questapi.api.QuestAPI;
import com.deichor.questapi.api.QuestApiBuilder;
import com.deichor.questapi.api.quest.DefaultQuestService;
import com.deichor.questapi.api.quest.QuestService;
import com.deichor.questapi.core.storage.StorageManager;
import com.deichor.questapi.core.storage.StorageTypes;
import org.bukkit.plugin.Plugin;

public class PaperQuestAPI implements QuestAPI {

    private static PaperQuestAPI INSTANCE;
    private final StorageManager storageManager;
    private final Plugin plugin;
    private QuestService questService;

    private PaperQuestAPI(Plugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(new PaperListeners(), plugin);
        this.storageManager = null; // Initially null, will be set by builder
        this.questService = null;
    }

    public static class Builder {
        private final Plugin plugin;
        private StorageTypes storageType = StorageTypes.IN_MEMORY; // Default storage type
        private String host;
        private int port;
        private String database;
        private String username;
        private String password;
        private String sqlitePath;

        public Builder(Plugin plugin) {
            this.plugin = plugin;
        }

        public Builder withStorageType(StorageTypes storageType) {
            this.storageType = storageType;
            return this;
        }

        public Builder withMySQLDatabase(String host, int port, String database, String username, String password) {
            this.host = host;
            this.port = port;
            this.database = database;
            this.username = username;
            this.password = password;
            return this;
        }

        public Builder withSQLitePath(String path) {
            this.sqlitePath = path;
            return this;
        }

        public PaperQuestAPI build() {
            if (INSTANCE == null) {
                INSTANCE = new PaperQuestAPI(plugin);
                QuestApiBuilder apiBuilder = QuestApiBuilder.builder()
                        .storageType(storageType);

                if (storageType == StorageTypes.MYSQL || storageType == StorageTypes.MYSQL_AND_MEMORY) {
                    apiBuilder.database(host, port, database, username, password);
                } else if (storageType == StorageTypes.SQLITE) {
                    apiBuilder.sqlitePath(sqlitePath);
                }

                StorageManager storageManager = apiBuilder.build().getStorageManager();
                INSTANCE.setStorageManager(storageManager);
                INSTANCE.setQuestService();
                QuestAPI.registerAPI(INSTANCE);
            }
            return INSTANCE;
        }
    }

    private void setStorageManager(StorageManager storageManager) {
        if (this.storageManager == null) {
            try {
                var field = this.getClass().getDeclaredField("storageManager");
                field.setAccessible(true);
                field.set(this, storageManager);
            } catch (Exception e) {
                throw new RuntimeException("Failed to set storage manager", e);
            }
        }
    }

    public static Builder builder(Plugin plugin) {
        return new Builder(plugin);
    }

    public static PaperQuestAPI getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("QuestPaperAPI has not been initialized. Use builder() first.");
        }
        return INSTANCE;
    }

    @Override
    public StorageManager getStorageManager() {
        if (storageManager == null) {
            throw new IllegalStateException("StorageManager has not been initialized");
        }
        return storageManager;
    }

    @Override
    public QuestService getQuestService() {
        if(questService == null) {
            throw new IllegalStateException("QuestService has not been initialized");
        }
        return questService;
    }

    private void setQuestService() {
        if (this.questService == null) {
            if(storageManager != null) {
                this.questService = new DefaultQuestService(this.getStorageManager());
            }
        }
    }

    @Override
    public String getIdentifier() {
        return plugin.getName();
    }
}
