package com.deichor.questapi.paper.listener;

import com.deichor.questapi.core.model.RequirementTrigger;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract base class for requirement listeners that handle Bukkit/Spigot events
 * @param <T> The type of Bukkit event this listener handles
 */
public abstract class RequirementListener<T extends Event> implements Listener {
    
    private final Set<RequirementTrigger<T>> requirements = new HashSet<>();
    
    /**
     * Registers a requirement to be tracked by this listener
     *
     * @param requirement The requirement to register
     * @return This listener for method chaining
     */
    public RequirementListener<T> registerRequirement(RequirementTrigger<T> requirement) {
        requirements.add(requirement);
        return this;
    }
    
    /**
     * Registers multiple requirements at once
     * 
     * @param requirements The requirements to register
     * @return This listener for method chaining
     */
    public RequirementListener<T> registerRequirements(Set<RequirementTrigger<T>> requirements) {
        this.requirements.addAll(requirements);
        return this;
    }

    /**
     * Unregisters a requirement from this listener
     *
     * @param requirement The requirement to unregister
     * @return This listener for method chaining
     */
    public RequirementListener<T> unregisterRequirement(RequirementTrigger<T> requirement) {
        requirements.remove(requirement);
        return this;
    }
    
    /**
     * Gets all registered requirements
     * 
     * @return An unmodifiable set of all registered requirements
     */
    public Set<RequirementTrigger<T>> getRequirements() {
        return Collections.unmodifiableSet(requirements);
    }

    /**
     * Handles the event and passes it to all registered requirements
     *
     * @param event The event to handle
     */
    protected void handleEvent(T event) {
        requirements.forEach(requirement -> requirement.handleEvent(event));
    }
    
    /**
     * Clears all registered requirements
     */
    public void clearRequirements() {
        requirements.clear();
    }
}