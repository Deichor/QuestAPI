package com.deichor.questapi.api.quest;

import com.deichor.questapi.core.model.QuestOwner;
import com.deichor.questapi.core.model.QuestReward;
import java.util.function.Consumer;

public class SimpleQuestReward<R, O extends QuestOwner<?>> implements QuestReward<R, O> {
    private final R reward;
    private final Consumer<O> rewardAction;
    private final String description;

    public SimpleQuestReward(R reward, Consumer<O> rewardAction, String description) {
        this.reward = reward;
        this.rewardAction = rewardAction;
        this.description = description;
    }

    @Override
    public void giveReward(O owner) {
        rewardAction.accept(owner);
    }

    @Override
    public R getReward() {
        return reward;
    }

    public String getDescription() {
        return description;
    }
}