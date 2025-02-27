package com.deichor.questapi.core;

import com.deichor.questapi.core.extend.BaseQuest;
import com.deichor.questapi.core.model.QuestRequirement;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages quest operations and lifecycle
 *
 * @param <O> The owner type of the quest
 */
public abstract class QuestManager<O> {
    private final BaseQuest<O, ?> quest;

    /**
     * Creates a new QuestManager for the specified quest
     * 
     * @param quest The quest to manage
     */
    public QuestManager(BaseQuest<O, ?> quest) {
        this.quest = quest;
        quest.start();
    }

    /**
     * Distributes all rewards to the quest owner
     */
    public void giveReward() {
        quest.getRewards().forEach(reward -> reward.giveReward(quest.getOwner()));
    }

    /**
     * Attempts to finish the quest if all requirements are met
     * 
     * @return true if the quest was completed, false otherwise
     */
    public boolean finishQuest() {
        if (quest.getRequirements().stream().allMatch(QuestRequirement::isCompleted)) {
            quest.complete();
            giveReward();
            return true;
        }
        return false;
    }
    
    /**
     * Checks if the quest is complete
     * 
     * @return true if all requirements are completed, false otherwise
     */
    public boolean isQuestCompleted() {
        return quest.getRequirements().stream().allMatch(QuestRequirement::isCompleted);
    }
    
    /**
     * Gets the overall progress of the quest as a percentage
     * 
     * @return The total progress percentage (0-100)
     */
    public double getQuestProgress() {
        List<QuestRequirement> requirements = quest.getRequirements();
        if (requirements.isEmpty()) {
            return 100.0;
        }
        
        double totalProgress = requirements.stream()
                .map(QuestRequirement::getCompletionPercentage)
                .reduce(0.0, Double::sum);
                
        return totalProgress / requirements.size();
    }
    
    /**
     * Gets the overall progress for each requirement
     * 
     * @return A list of current/target progress for each requirement
     */
    public List<String> getRequirementsProgress() {
        return quest.getRequirements().stream()
                .map(req -> String.format("%s: %d/%d", 
                        req.getName(), 
                        req.getProgress(), 
                        req.getTargetProgress()))
                .collect(Collectors.toList());
    }

    /**
     * Gets the quest being managed
     * 
     * @return The quest
     */
    public BaseQuest<O, ?> getQuest() {
        return quest;
    }
}
