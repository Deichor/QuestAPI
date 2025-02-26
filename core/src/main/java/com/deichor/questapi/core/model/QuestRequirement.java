package com.deichor.questapi.core.model;

public interface QuestRequirement {

    RequirementType getType();
    void addProgress(int progress);
    int getProgress();
    void complete();
    boolean isComplete();
    RequirementTrigger<?> getTrigger();

}
