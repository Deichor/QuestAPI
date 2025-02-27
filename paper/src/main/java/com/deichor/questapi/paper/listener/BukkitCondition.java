package com.deichor.questapi.paper.listener;

import com.deichor.questapi.core.model.RequirementCondition;
import com.deichor.questapi.paper.quest.BukkitOwner;
import com.deichor.questapi.paper.quest.BukkitOwnerTypes;
import com.deichor.questapi.paper.quest.owners.PlayerOwner;

public class BukkitCondition implements RequirementCondition {

    private final BukkitOwner<?> owner;
    
    public BukkitCondition(BukkitOwner<?> owner) {
        this.owner = owner;
    }

    @Override
    public boolean test() {
        if(BukkitOwnerTypes.valueOf(owner.getOwnerType()) != null) {
            //Player check
            if(owner instanceof PlayerOwner playerOwner) {
                return playerOwner.getOwner() != null;
            }
        }
        return false;
    }
}
