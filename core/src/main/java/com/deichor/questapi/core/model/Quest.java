package com.deichor.questapi.core.model;

import java.util.List;

public interface Quest {
    
    QuestOwner getOwner();
    List<QuestReward> getRewards();
    List<QuestRequirement> getRequirements();

    void start();
    void complete();

}
