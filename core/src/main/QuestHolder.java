package com.deichor.questapi.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public class QuestHolder<T> {
    private final Map<T, QuestManager> quests;

    public QuestHolder() {
        this.quests = new HashMap<>();
    }

    public void addQuest(T questId, QuestManager quest) {
        quests.put(questId, quest);
    }

    public void removeQuest(T questId) {
        quests.remove(questId);
    }

    public QuestManager getQuest(T questId) {
        return quests.get(questId);
    }

    public Collection<QuestManager> getAllQuests() {
        return quests.values();
    }

    public boolean hasQuest(T questId) {
        return quests.containsKey(questId);
    }
}
