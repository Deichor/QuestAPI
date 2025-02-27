package com.deichor.questapi.paper.listener.triggers;

import com.deichor.questapi.core.model.QuestRequirement;
import com.deichor.questapi.core.model.RequirementTrigger;
import com.deichor.questapi.paper.listener.BukkitCondition;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityKillTrigger extends RequirementTrigger<EntityDeathEvent> {
    private final EntityType entityType;
    private final BukkitCondition condition;
    private int currentKills;

    public EntityKillTrigger(QuestRequirement requirement, BukkitCondition condition, EntityType entityType) {
        super(requirement, condition);
        this.condition = condition;
        this.entityType = entityType;
        this.currentKills = 0;
    }

    @Override
    public boolean handleEvent(EntityDeathEvent event) {
        if(condition.test()) {
            if(event.getEntity().getType() == entityType) {
                currentKills++;
            }
        }
        return currentKills >= 1;
    }

    public int getCurrentKills() {
        return currentKills;
    }

    public EntityType getEntityType() {
        return entityType;
    }
}
