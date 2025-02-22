package com.deichor.questapi.core.storage;

import com.deichor.questapi.core.QuestManager;
import com.deichor.questapi.core.model.QuestOwner;

import java.util.List;
import java.util.Optional;

public interface QuestStorage {
    void saveQuest(int questId, QuestManager<?> quest);
    void removeQuest(int questId);
    void removeQuestsByOwner(QuestOwner<?> owner);
    Optional<QuestManager<?>> getQuest(int questId);
    List<QuestManager<?>> getQuestsByOwner(QuestOwner<?> owner);
    List<QuestManager<?>> getAllQuests();
    void close();
}