package com.deichor.questapi.paper.listener;

import com.deichor.questapi.core.model.RequirementTrigger;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract base class for requirement listeners that handle Bukkit/Spigot events
 * @param <T> The type of Bukkit event this listener handles
 * @param <O> The type of owner (usually Player) for the requirements
 */
public abstract class RequirementListener<T extends Event, O> implements Listener {
    
    private final Set<RequirementTrigger<T>> requirements;

    public RequirementListener() {
        this.requirements = new HashSet<>();
    }

    /**
     * Registers a requirement to be tracked by this listener
     *
     * @param requirement The requirement to register
     */
    public void registerRequirement(RequirementTrigger<T> requirement) {
        requirements.add(requirement);
    }

    /**
     * Unregisters a requirement from this listener
     *
     * @param requirement The requirement to unregister
     */
    public void unregisterRequirement(RequirementTrigger<T> requirement) {
        requirements.remove(requirement);
    }

    /**
     * Handles the event and passes it to all registered requirements
     *
     * @param event The event to handle
     */
    protected void handleEvent(T event) {
        requirements.forEach(requirement -> requirement.handleEvent(event));
    }
}