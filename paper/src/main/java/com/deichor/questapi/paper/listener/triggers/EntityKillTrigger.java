package com.deichor.questapi.paper.listener.triggers;

import com.deichor.questapi.core.model.QuestRequirement;
import com.deichor.questapi.core.model.RequirementTrigger;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityKillTrigger extends RequirementTrigger<EntityDeathEvent> {

    public EntityKillTrigger(QuestRequirement requirement) {
        super(requirement);
    }

    @Override
    public boolean handleEvent(EntityDeathEvent event) {

        return false;
    }
}
