package com.deichor.questapi.api.quest;

import com.deichor.questapi.core.QuestManager;
import com.deichor.questapi.core.model.Quest;
import com.deichor.questapi.core.model.QuestOwner;
import com.deichor.questapi.core.model.QuestRequirement;
import com.deichor.questapi.core.model.QuestReward;

import java.util.List;
import java.util.Optional;

public interface QuestService {
    /**
     * Creates a new quest with the given requirements and rewards
     *
     * @param owner Quest owner
     * @param requirements List of requirements to complete the quest
     * @param rewards List of rewards for completing the quest
     * @return The created quest manager
     */
    <O, R> QuestManager<O> createQuest(QuestOwner<O> owner, List<QuestRequirement> requirements, List<QuestReward<R, QuestOwner<O>>> rewards);

    /**
     * Gets an existing quest by its ID
     *
     * @param questId The quest ID
     * @return Optional containing the quest if found
     */
    Optional<QuestManager<?>> getQuest(int questId);

    /**
     * Gets all quests for a specific owner
     *
     * @param owner The quest owner
     * @return List of quests owned by the given owner
     */
    List<QuestManager<?>> getQuestsByOwner(QuestOwner<?> owner);

    /**
     * Gets all quests in the system
     *
     * @return List of all quests
     */
    List<QuestManager<?>> getAllQuests();

    /**
     * Completes a quest if all requirements are met
     *
     * @param questId The quest ID
     * @return true if quest was completed successfully
     */
    boolean completeQuest(int questId);

    /**
     * Removes a quest from the system
     *
     * @param questId The quest ID to remove
     */
    void removeQuest(int questId);

    /**
     * Updates quest progress for a specific requirement
     *
     * @param questId The quest ID
     * @param requirementIndex The index of the requirement to update
     * @param progress The amount of progress to add
     */
    void updateQuestProgress(int questId, int requirementIndex, int progress);
}