package com.deichor.questapi.core.extend;

import com.deichor.questapi.core.model.QuestRequirement;

public abstract class BaseQuestRequirement implements QuestRequirement {

    private int progress;
    private boolean complete;
    private String name;

    @Override
    public void addProgress(int progress) {
        this.progress += progress;
    }

    @Override
    public void complete() {
        this.complete = true;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getProgress() {
        return this.progress;
    }

    @Override
    public boolean isComplete() {
        return this.complete;
    }
    

}
