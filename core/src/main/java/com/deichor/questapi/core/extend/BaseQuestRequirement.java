package com.deichor.questapi.core.extend;

import com.deichor.questapi.core.model.QuestRequirement;

public abstract class BaseQuestRequirement implements QuestRequirement {

    private int progress;
    private boolean complete;

    @Override
    public int addProgress(int progress) {
        this.progress += progress;
        return this.progress;
    }

    @Override
    public int getProgress() {
        return this.progress;
    }



}
