package com.deichor.questapi.core.storage;

public class StorageManager {
    private final QuestStorage primaryStorage;
    private final QuestStorage secondaryStorage;
    private final StorageTypes storageType;

    public StorageManager(StorageTypes storageType, DatabaseConfig config) {
        this.storageType = storageType;
        
        switch (storageType) {
            case MYSQL -> {
                this.primaryStorage = new MySQLQuestStorage(config);
                this.secondaryStorage = null;
            }
            case MYSQL_AND_MEMORY -> {
                this.primaryStorage = new MySQLQuestStorage(config);
                this.secondaryStorage = new InMemoryQuestStorage();
            }
            case IN_MEMORY -> {
                this.primaryStorage = new InMemoryQuestStorage();
                this.secondaryStorage = null;
            }
            default -> throw new IllegalArgumentException("Unsupported storage type: " + storageType);
        }
    }

    public QuestStorage getPrimaryStorage() {
        return primaryStorage;
    }

    public QuestStorage getSecondaryStorage() {
        return secondaryStorage;
    }

    public StorageTypes getStorageType() {
        return storageType;
    }

    public void close() {
        if (primaryStorage != null) {
            primaryStorage.close();
        }
        if (secondaryStorage != null) {
            secondaryStorage.close();
        }
    }
}