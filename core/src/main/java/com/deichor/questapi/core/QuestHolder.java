package com.deichor.questapi.core;

import com.deichor.questapi.core.model.QuestOwner;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.PriorityQueue;

public class QuestHolder {
    private static final Map<Integer, QuestManager<?>> quests = new HashMap<>();
    private static final AtomicInteger nextId = new AtomicInteger(1);
    private static final PriorityQueue<Integer> availableIds = new PriorityQueue<>();

    public static Integer addQuest(QuestManager<?> quest) {
        int questId;
        if (!availableIds.isEmpty()) {
            questId = availableIds.poll();
        } else {
            questId = nextId.getAndIncrement();
        }
        quests.put(questId, quest);
        return questId;
    }

    public static void removeQuest(Integer questId) {
        if (quests.remove(questId) != null) {
            availableIds.offer(questId);
        }
    }
    public static void removeAllQuestsFromOwner(QuestOwner<?> owner) {
        quests.entrySet().removeIf(entry -> {
            QuestManager<?> quest = entry.getValue();
            if (quest.getQuest().getOwner().equals(owner)) {
                availableIds.offer(entry.getKey());
                return true;
            }
            return false;
        });
    }

    public static boolean finishQuest(Integer questId) {
        QuestManager<?> quest = quests.get(questId);
        if (quest != null) {
            if(quest.finishQuest()) {
                removeQuest(questId);
                return true;
            }
            return false;
        }
        return false;
    }

    public static QuestManager<?> getQuest(Integer questId) {
        return quests.get(questId);
    }

    public static Collection<QuestManager<?>> getAllQuests() {
        return quests.values();
    }

    public static boolean hasQuest(Integer questId) {
        return quests.containsKey(questId);
    }

}
