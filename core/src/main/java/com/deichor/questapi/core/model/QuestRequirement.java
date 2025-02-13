package com.deichor.questapi.core.model;

public interface QuestRequirement {

    String getName();
    void addProgress(int progress);
    int getProgress();
    void complete();
    boolean isComplete();

}
