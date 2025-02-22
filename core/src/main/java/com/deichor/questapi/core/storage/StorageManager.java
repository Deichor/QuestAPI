package com.deichor.questapi.core.storage;

import com.deichor.questapi.core.QuestManager;
import com.deichor.questapi.core.model.QuestOwner;
import java.util.List;
import java.util.Optional;

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

    public void saveQuest(int questId, QuestManager<?> quest) {
        primaryStorage.saveQuest(questId, quest);
        if (secondaryStorage != null) {
            secondaryStorage.saveQuest(questId, quest);
        }
    }

    public void removeQuest(int questId) {
        primaryStorage.removeQuest(questId);
        if (secondaryStorage != null) {
            secondaryStorage.removeQuest(questId);
        }
    }

    public void removeQuestsByOwner(QuestOwner<?> owner) {
        primaryStorage.removeQuestsByOwner(owner);
        if (secondaryStorage != null) {
            secondaryStorage.removeQuestsByOwner(owner);
        }
    }

    public Optional<QuestManager<?>> getQuest(int questId) {
        return primaryStorage.getQuest(questId);
    }

    public List<QuestManager<?>> getQuestsByOwner(QuestOwner<?> owner) {
        return primaryStorage.getQuestsByOwner(owner);
    }

    public List<QuestManager<?>> getAllQuests() {
        return primaryStorage.getAllQuests();
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