package com.deichor.questapi.paper.listener.implementation;

import com.deichor.questapi.paper.listener.RequirementListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;

/**
 * Listener for entity item pickup events
 */
public class ItemPickupListener extends RequirementListener<EntityPickupItemEvent> {

    /**
     * Processes the EntityPickupItemEvent and passes it to registered triggers
     * 
     * @param event The EntityPickupItemEvent to handle
     */
    @EventHandler
    public void onEntityItemPickup(EntityPickupItemEvent event) {
        handleEvent(event);
    }
}