package com.deichor.questapi.paper.listener;

import com.deichor.questapi.core.model.RequirementCondition;
import com.deichor.questapi.paper.quest.BukkitOwner;
import com.deichor.questapi.paper.quest.BukkitOwnerTypes;
import com.deichor.questapi.paper.quest.owners.PlayerOwner;

/**
 * A condition implementation for Bukkit owners
 * This class is kept for backward compatibility.
 */
public class BukkitCondition implements RequirementCondition {

    private final BukkitOwner<?> owner;
    private final String description;
    
    /**
     * Creates a new Bukkit condition for the specified owner
     * 
     * @param owner The Bukkit owner
     */
    public BukkitCondition(BukkitOwner<?> owner) {
        this.owner = owner;
        this.description = "Condition for " + owner.getOwnerType();
    }
    
    /**
     * Creates a new Bukkit condition with a custom description
     *
     * @param owner The Bukkit owner
     * @param description The condition description
     */
    public BukkitCondition(BukkitOwner<?> owner, String description) {
        this.owner = owner;
        this.description = description;
    }

    @Override
    public boolean test() {
        return owner.isValid();
    }
    
    @Override
    public String getDescription() {
        return description;
    }
    
    /**
     * Gets the owner associated with this condition
     *
     * @return The Bukkit owner
     */
    public BukkitOwner<?> getOwner() {
        return owner;
    }
}
