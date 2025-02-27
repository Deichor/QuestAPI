package com.deichor.questapi.paper.examples;

import com.deichor.questapi.core.model.BaseQuestRequirement;
import com.deichor.questapi.core.model.QuestRequirement;
import com.deichor.questapi.core.model.RequirementTrigger;
import com.deichor.questapi.paper.listener.implementation.BlockBreakListener;
import com.deichor.questapi.paper.listener.triggers.BlockBreakTrigger;
import com.deichor.questapi.paper.listener.triggers.BukkitTrigger;
import com.deichor.questapi.paper.quest.BukkitOwner;
import com.deichor.questapi.paper.quest.owners.PlayerOwner;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Example class demonstrating how to use the improved quest system
 * with automatic progress and owner management
 */
public class ImprovedQuestExample {
    
    private final JavaPlugin plugin;
    
    public ImprovedQuestExample(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Creates a block breaking requirement
     * 
     * @param player The player to create the requirement for
     * @param material The material type to break
     * @param targetProgress The amount of blocks to break
     * @return The created quest requirement
     */
    public QuestRequirement createBlockBreakRequirement(Player player, Material material, int targetProgress) {
        // Create the requirement
        QuestRequirement requirement = new BaseQuestRequirement("Break " + material.name(), targetProgress);
        
        // Create the trigger with player owner
        BlockBreakTrigger blockBreakTrigger = new BlockBreakTrigger(
                requirement,
                new PlayerOwner(player),
                material
        );
        
        // Configure block break settings
        blockBreakTrigger.setProgressPerBreak(1);
        blockBreakTrigger.setRequireCorrectTool(true);
        
        // Register with the listener
        plugin.getServer().getPluginManager().registerEvents(
                new BlockBreakListener().registerRequirement(blockBreakTrigger),
                plugin
        );
        
        // Set completion callback
        requirement.setCompletionCallback(() -> 
                player.sendMessage("§aYou've completed the block breaking task!"));
        
        return requirement;
    }
    
    /**
     * Creates a tiered mining requirement where progress increases based on mining streak
     * 
     * @param player The player to create the requirement for
     * @return The created quest requirement
     */
    public QuestRequirement createTieredMiningRequirement(Player player) {
        // Create the requirement with a high target
        QuestRequirement requirement = new BaseQuestRequirement("Mining Master", 100);
        
        // Create owner
        BukkitOwner<?> owner = new PlayerOwner(player);
        
        // Create stone mining trigger
        BlockBreakTrigger stoneTrigger = new BlockBreakTrigger(requirement, owner, Material.STONE);
        
        // Create an array to store the player's mining streak (use array for mutable int in lambda)
        final int[] streak = {0};
        final long[] lastMineTime = {0};
        
        // Create a custom progress strategy that increases progress based on streak
        RequirementTrigger.ProgressStrategy streakStrategy = (req, amount) -> {
            long currentTime = System.currentTimeMillis();
            
            // If last mine was within 2 seconds, increase streak, otherwise reset
            if (currentTime - lastMineTime[0] < 2000) {
                streak[0]++;
            } else {
                streak[0] = 1; // Reset to 1 (current block)
            }
            
            // Update last mine time
            lastMineTime[0] = currentTime;
            
            // Calculate progress bonus based on streak
            int progressToAdd = amount;
            if (streak[0] >= 10) {
                progressToAdd = amount * 3; // Triple points for 10+ streak
                player.sendMessage("§b§lMINING FRENZY! §r+3x points!");
            } else if (streak[0] >= 5) {
                progressToAdd = amount * 2; // Double points for 5+ streak
                player.sendMessage("§a§lMINING STREAK! §r+2x points!");
            }
            
            // Add progress
            req.addProgress(progressToAdd);
            
            // Show streak to player
            player.sendMessage("§7Mining streak: " + streak[0] + " §8(+" + progressToAdd + " progress)");
        };
        
        // Set the streak strategy
        stoneTrigger.setProgressStrategy(streakStrategy);
        
        // Register with listener
        plugin.getServer().getPluginManager().registerEvents(
                new BlockBreakListener().registerRequirement(stoneTrigger),
                plugin
        );
        
        return requirement;
    }
    
    /**
     * Adds a new owner to an existing trigger
     * 
     * @param trigger The trigger to modify
     * @param newPlayer The new player to add
     * @return The modified trigger
     */
    public <T extends BukkitTrigger<?>> T addPlayerToTrigger(T trigger, Player newPlayer) {
        trigger.addOwner(new PlayerOwner(newPlayer));
        return trigger;
    }
}