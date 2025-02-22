package com.deichor.questapi.paper.quest.rewards;

import com.deichor.questapi.core.model.QuestReward;
import com.deichor.questapi.paper.quest.BukkitOwner;
import com.deichor.questapi.paper.quest.owners.PlayerOwner;

public class ExperienceReward implements QuestReward<Integer, BukkitOwner<?>> {

    private final int experience;

    public ExperienceReward(int experience) {
        this.experience = experience;
    }

    @Override
    public Integer getReward() {
        return experience;
    }

    @Override
    public void giveReward(BukkitOwner<?> owner) {
        if (owner.getOwner() instanceof PlayerOwner player) {
            player.getOwner().giveExp(experience);
        } else {
            throw new IllegalArgumentException("Owner is not a valid Bukkit entity.");
        }
    }
}


