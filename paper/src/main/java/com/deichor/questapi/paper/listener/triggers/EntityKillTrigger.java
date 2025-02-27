package com.deichor.questapi.paper.listener.triggers;

import com.deichor.questapi.core.model.QuestRequirement;
import com.deichor.questapi.core.model.RequirementTrigger;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityKillTrigger extends RequirementTrigger<EntityDeathEvent> {
    private final String entityType;
    private final int requiredKills;
    private int currentKills;

    public EntityKillTrigger(QuestRequirement requirement, String entityType, int requiredKills) {
        super(requirement);
        this.entityType = entityType;
        this.requiredKills = requiredKills;
        this.currentKills = 0;
    }

    @Override
    public boolean handleEvent(EntityDeathEvent event) {
        // Öldüren bir oyuncu mu kontrol et
        if (!(event.getEntity().getKiller() instanceof Player killer)) {
            return false;
        }

        // Öldürülen varlık türü doğru mu kontrol et
        if (!event.getEntityType().name().equals(entityType)) {
            return false;
        }

        // Kill sayısını artır
        currentKills++;

        // Gerekli kill sayısına ulaşıldı mı kontrol et
        return currentKills >= requiredKills;
    }

    public int getCurrentKills() {
        return currentKills;
    }

    public int getRequiredKills() {
        return requiredKills;
    }

    public String getEntityType() {
        return entityType;
    }
}
