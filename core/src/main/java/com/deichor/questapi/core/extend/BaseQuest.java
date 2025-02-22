package com.deichor.questapi.core.extend;

import java.util.ArrayList;
import java.util.List;

import com.deichor.questapi.core.model.Quest;
import com.deichor.questapi.core.model.QuestOwner;
import com.deichor.questapi.core.model.QuestRequirement;
import com.deichor.questapi.core.model.QuestReward;

public abstract class BaseQuest<O, R> implements Quest<R, O> {

    private QuestOwner<O> owner;
    private boolean isComplete;
    private final List<QuestRequirement> requirements = new ArrayList<>();
    private final List<QuestReward<R, QuestOwner<O>>> rewards = new ArrayList<>();


    public void setOwner(QuestOwner<O> owner){
        this.owner = owner;
    }

    @Override
    public void complete() {
        isComplete = true;
    }

    @Override
    public QuestOwner<O> getOwner() {
        return this.owner;
    }

    @Override
    public List<QuestRequirement> getRequirements() {
        return this.requirements;
    }

    @Override
    public List<QuestReward<R, QuestOwner<O>>> getRewards() {
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
