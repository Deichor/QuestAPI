package com.deichor.questapi.core.extend;

import java.util.ArrayList;
import java.util.List;

import com.deichor.questapi.core.model.Quest;
import com.deichor.questapi.core.model.QuestOwner;
import com.deichor.questapi.core.model.QuestRequirement;
import com.deichor.questapi.core.model.QuestReward;

public abstract class BaseQuest implements Quest {

    private QuestOwner<?> owner;
    private boolean isComplete;
    private final List<QuestRequirement> requirements = new ArrayList<>();
    private final List<QuestReward<?, ? extends QuestOwner<?>>> rewards = new ArrayList<>();

    @Override
    public void complete() {
        isComplete = true;
    }

    @Override
    public QuestOwner<?> getOwner() {
        return this.owner;
    }

    @Override
    public List<QuestRequirement> getRequirements() {
        return this.requirements;
    }

    @Override
    public List<QuestReward<?, ? extends QuestOwner<?>>> getRewards() {
        return this.rewards;
    }

    @Override
    public void start() {
        isComplete = false;
    }

    public boolean isComplete() {
        return isComplete;
    }

}
