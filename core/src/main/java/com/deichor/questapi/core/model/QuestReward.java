package com.deichor.questapi.core.model;

public interface QuestReward<T, O> {

    T getReward();
    void giveReward(O owner);
}
