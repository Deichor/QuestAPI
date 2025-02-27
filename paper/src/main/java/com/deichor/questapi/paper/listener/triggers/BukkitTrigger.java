package com.deichor.questapi.paper.listener.triggers;

import com.deichor.questapi.core.model.QuestRequirement;
import com.deichor.questapi.core.model.RequirementCondition;
import com.deichor.questapi.core.model.RequirementTrigger;
import com.deichor.questapi.paper.listener.BukkitCondition;
import com.deichor.questapi.paper.quest.BukkitOwner;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Abstract base class for Bukkit triggers that simplifies owner management
 * 
 * @param <T> The Bukkit event type
 */
public abstract class BukkitTrigger<T extends Event> extends RequirementTrigger<T> {

    private final List<BukkitOwner<?>> owners = new ArrayList<>();
    
    /**
     * Creates a new Bukkit trigger with the specified requirement
     *
     * @param requirement The requirement to track
     */
    public BukkitTrigger(QuestRequirement requirement) {
        super(requirement);
    }
    
    /**
     * Creates a new Bukkit trigger with the specified requirement and owner
     * The owner will automatically be added as a condition
     *
     * @param requirement The requirement to track
     * @param owner The owner of this requirement
     */
    public BukkitTrigger(QuestRequirement requirement, BukkitOwner<?> owner) {
        super(requirement);
        addOwner(owner);
    }
    
    /**
     * Creates a new Bukkit trigger with the specified requirement and owners
     * All owners will automatically be added as conditions
     *
     * @param requirement The requirement to track
     * @param owners The owners of this requirement
     */
    public BukkitTrigger(QuestRequirement requirement, BukkitOwner<?>... owners) {
        super(requirement);
        addOwners(Arrays.asList(owners));
    }
    
    /**
     * Creates a new Bukkit trigger with the specified requirement and a custom condition
     *
     * @param requirement The requirement to track
     * @param condition A custom condition
     */
    public BukkitTrigger(QuestRequirement requirement, RequirementCondition condition) {
        super(requirement, condition);
    }
    
    /**
     * Adds an owner to this trigger
     * The owner's validation condition will be added automatically
     *
     * @param owner The owner to add
     * @return This trigger for method chaining
     */
    public BukkitTrigger<T> addOwner(BukkitOwner<?> owner) {
        if (owner != null) {
            owners.add(owner);
            addCondition(owner.createCondition());
        }
        return this;
    }
    
    /**
     * Adds multiple owners to this trigger
     * Each owner's validation condition will be added automatically
     *
     * @param owners The collection of owners to add
     * @return This trigger for method chaining
     */
    public BukkitTrigger<T> addOwners(Collection<BukkitOwner<?>> owners) {
        for (BukkitOwner<?> owner : owners) {
            addOwner(owner);
        }
        return this;
    }
    
    /**
     * Removes an owner from this trigger
     * Also removes the associated condition
     *
     * @param owner The owner to remove
     * @return This trigger for method chaining
     */
    public BukkitTrigger<T> removeOwner(BukkitOwner<?> owner) {
        if (owner != null && owners.remove(owner)) {
            // Since we can't directly access the condition created by the owner,
            // we need to rebuild the condition list
            List<RequirementCondition> currentConditions = getConditions();
            clearConditions();
            
            // Re-add all conditions for remaining owners
            for (BukkitOwner<?> remainingOwner : owners) {
                addCondition(remainingOwner.createCondition());
            }
            
            // Re-add any other conditions that weren't created by owners
            for (RequirementCondition condition : currentConditions) {
                if (!(condition instanceof BukkitCondition builtinCondition) ||
                        !builtinCondition.getOwner().equals(owner)) {
                    addCondition(condition);
                }
            }
        }
        return this;
    }
    
    /**
     * Gets all owners for this trigger
     *
     * @return A list of all owners
     */
    public List<BukkitOwner<?>> getOwners() {
        return new ArrayList<>(owners);
    }
}