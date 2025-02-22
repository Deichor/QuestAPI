package com.deichor.questapi.core.model;

import java.util.List;

public interface Quest<R, O> {
    
    QuestOwner<O> getOwner();
    List<QuestReward<R, QuestOwner<O>>> getRewards();
    List<QuestRequirement> getRequirements();

    void start();
    void complete();

}
