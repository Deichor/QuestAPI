package com.deichor.questapi.core.model;

/**
 * Abstract class for defining custom quest requirements.
 * Implementations can define their own trigger conditions and event handlers.
 *
 * @param <T> The type of event that this requirement handles
 */
public abstract class RequirementTrigger<T> {

    private final QuestRequirement requirement;

    public RequirementTrigger(QuestRequirement requirement) {
        this.requirement = requirement;
    }

    /**
     * Handles the incoming event and checks if it satisfies the requirement
     *
     * @param event The event to process
     * @return true if the requirement is met, false otherwise
     */
    public abstract boolean handleEvent(T event);

    /**
     *
     * @return The requirement object
     */
    public QuestRequirement getRequirement(){
        return requirement;
    };

}
