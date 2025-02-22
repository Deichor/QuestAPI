package com.deichor.questapi.api.quest;

import com.deichor.questapi.core.QuestManager;
import com.deichor.questapi.core.extend.BaseQuest;
import com.deichor.questapi.core.model.QuestOwner;
import com.deichor.questapi.core.model.QuestRequirement;
import com.deichor.questapi.core.model.QuestReward;
import com.deichor.questapi.core.storage.StorageManager;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultQuestService implements QuestService {
    private final StorageManager storageManager;
    private final AtomicInteger questIdGenerator;

    public DefaultQuestService(StorageManager storageManager) {
        this.storageManager = storageManager;
        this.questIdGenerator = new AtomicInteger(0);
    }

    @Override
    public <O, R> QuestManager<O> createQuest(QuestOwner<O> owner, List<QuestRequirement> requirements, List<QuestReward<R, QuestOwner<O>>> rewards) {
        BaseQuest<O, R> quest = new BaseQuest<>() {};
        quest.setOwner(owner);
        quest.getRequirements().addAll(requirements);
        quest.getRewards().addAll(rewards);
        
        QuestManager<O> questManager = new QuestManager<>(quest) {};
        int questId = questIdGenerator.incrementAndGet();
        storageManager.saveQuest(questId, questManager);
        
        return questManager;
    }

    @Override
    public Optional<QuestManager<?>> getQuest(int questId) {
        return storageManager.getQuest(questId);
    }

    @Override
    public List<QuestManager<?>> getQuestsByOwner(QuestOwner<?> owner) {
        return storageManager.getQuestsByOwner(owner);
    }

    @Override
    public List<QuestManager<?>> getAllQuests() {
        return storageManager.getAllQuests();
    }

    @Override
    public boolean completeQuest(int questId) {
        return getQuest(questId)
                .map(QuestManager::finishQuest)
                .orElse(false);
    }

    @Override
    public void removeQuest(int questId) {
        storageManager.removeQuest(questId);
    }

    @Override
    public void updateQuestProgress(int questId, int requirementIndex, int progress) {
        getQuest(questId).ifPresent(questManager -> {
            List<QuestRequirement> requirements = questManager.getQuest().getRequirements();
            if (requirementIndex >= 0 && requirementIndex < requirements.size()) {
                requirements.get(requirementIndex).addProgress(progress);
                storageManager.saveQuest(questId, questManager);
            }
        });
    }
}