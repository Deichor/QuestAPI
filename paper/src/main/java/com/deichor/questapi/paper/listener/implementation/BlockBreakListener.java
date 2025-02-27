package com.deichor.questapi.paper.listener.implementation;

import com.deichor.questapi.paper.listener.RequirementListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Listener for block break events
 */
public class BlockBreakListener extends RequirementListener<BlockBreakEvent> {

    /**
     * Processes the BlockBreakEvent and passes it to registered triggers
     * 
     * @param event The BlockBreakEvent to handle
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        handleEvent(event);
    }
}