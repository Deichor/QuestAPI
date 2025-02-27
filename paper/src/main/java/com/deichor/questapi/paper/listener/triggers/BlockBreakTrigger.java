package com.deichor.questapi.paper.listener.triggers;

import com.deichor.questapi.core.model.QuestRequirement;
import com.deichor.questapi.core.model.RequirementCondition;
import com.deichor.questapi.paper.quest.BukkitOwner;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * A trigger that responds to block break events
 */
public class BlockBreakTrigger extends BukkitTrigger<BlockBreakEvent> {
    private final Material material;
    private boolean requireCorrectTool = false;
    private int progressPerBreak = 1;

    /**
     * Creates a new block break trigger
     *
     * @param requirement The quest requirement
     * @param material The material type that needs to be broken
     */
    public BlockBreakTrigger(QuestRequirement requirement, Material material) {
        super(requirement);
        this.material = material;
    }

    /**
     * Creates a new block break trigger with an owner
     *
     * @param requirement The quest requirement
     * @param owner The owner of this requirement
     * @param material The material type that needs to be broken
     */
    public BlockBreakTrigger(QuestRequirement requirement, BukkitOwner<?> owner, Material material) {
        super(requirement, owner);
        this.material = material;
    }
    
    /**
     * Creates a new block break trigger with a condition
     *
     * @param requirement The quest requirement
     * @param condition The condition to evaluate
     * @param material The material type that needs to be broken
     */
    public BlockBreakTrigger(QuestRequirement requirement, RequirementCondition condition, Material material) {
        super(requirement, condition);
        this.material = material;
    }
    
    /**
     * Sets whether the player needs to use the correct tool for the block
     * 
     * @param requireCorrectTool True if the correct tool is required
     * @return This trigger for chaining
     */
    public BlockBreakTrigger setRequireCorrectTool(boolean requireCorrectTool) {
        this.requireCorrectTool = requireCorrectTool;
        return this;
    }
    
    /**
     * Sets how much progress is added per block broken
     * 
     * @param progressPerBreak Progress amount per break
     * @return This trigger for chaining
     */
    public BlockBreakTrigger setProgressPerBreak(int progressPerBreak) {
        this.progressPerBreak = progressPerBreak;
        return this;
    }

    @Override
    protected ProcessResult processEvent(BlockBreakEvent event) {
        if (event.getBlock().getType() == material) {
            // Check if we need to verify the tool used
            if (requireCorrectTool) {
                ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
                if (!isCorrectTool(tool, material)) {
                    return ProcessResult.failure();
                }
            }
            
            return ProcessResult.success(progressPerBreak);
        }
        return ProcessResult.failure();
    }

    /**
     * Simple method to check if a tool is appropriate for a block type
     * This is a placeholder - in a real implementation you'd have proper checks
     */
    private boolean isCorrectTool(ItemStack tool, Material blockType) {
        // This is a simplified example - real implementation would be more complex
        if (blockType == Material.STONE && tool.getType().name().contains("PICKAXE")) {
            return true;
        }
        if ((blockType == Material.OAK_LOG || blockType.name().contains("WOOD")) && 
            tool.getType().name().contains("AXE")) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Gets the material type that needs to be broken
     *
     * @return The material type
     */
    public Material getMaterial() {
        return material;
    }
}