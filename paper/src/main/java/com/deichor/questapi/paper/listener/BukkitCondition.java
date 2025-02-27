package com.deichor.questapi.paper.listener;

import com.deichor.questapi.core.model.RequirementCondition;
import com.deichor.questapi.paper.quest.BukkitOwner;
import com.deichor.questapi.paper.quest.BukkitOwnerTypes;
import com.deichor.questapi.paper.quest.owners.PlayerOwner;

import java.util.Arrays;

public class BukkitCondition<T> implements RequirementCondition {

    private final BukkitOwner<T> owner;
    public BukkitCondition(BukkitOwner<T> owner) {
        this.owner = owner;
    }

    @Override
    public boolean test() {

        if(Arrays.stream(BukkitOwnerTypes.values()).toList().contains(owner)) {

            //Player check
            if(owner instanceof PlayerOwner playerOwner) {
                if(playerOwner.getOwner() != null) {

                    return true;
                }
            }

        }
        return false;
    }
}
