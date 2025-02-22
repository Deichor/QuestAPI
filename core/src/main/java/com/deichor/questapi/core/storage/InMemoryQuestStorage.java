package com.deichor.questapi.core.storage;

import com.deichor.questapi.core.QuestManager;
import com.deichor.questapi.core.model.QuestOwner;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryQuestStorage implements QuestStorage {
    private final Map<Integer, QuestManager<?>> quests = new ConcurrentHashMap<>();

    @Override
    public void saveQuest(int questId, QuestManager<?> quest) {
        quests.put(questId, quest);
    }

    @Override
    public void removeQuest(int questId) {
        quests.remove(questId);
    }

    @Override
    public void removeQuestsByOwner(QuestOwner<?> owner) {

    }

    @Override
    public Optional<QuestManager<?>> getQuest(int questId) {
        return Optional.ofNullable(quests.get(questId));
    }

    @Override
    public List<QuestManager<?>> getQuestsByOwner(QuestOwner<?> owner) {
        return quests.values().stream()
                .filter(quest -> quest.getQuest().getOwner().equals(owner))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestManager<?>> getAllQuests() {
        return new ArrayList<>(quests.values());
    }

    @Override
    public void close() {
        quests.clear();
    }
}