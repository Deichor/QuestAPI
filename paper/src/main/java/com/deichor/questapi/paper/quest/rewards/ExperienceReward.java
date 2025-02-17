package com.deichor.questapi.paper.quest.rewards;

import com.deichor.questapi.core.model.QuestReward;
import com.deichor.questapi.paper.quest.BukkitPlayerOwner;

public class ExperienceReward implements QuestReward<Integer, BukkitPlayerOwner> {

    private final int experience;

    public ExperienceReward(int experience) {
        this.experience = experience;
    }

    @Override
    public Integer getReward() {
        return experience;
    }

    @Override
    public void giveReward(BukkitPlayerOwner owner) {
        owner.getOwner().giveExp(experience);
    }
}


