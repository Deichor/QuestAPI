package com.deichor.questapi.core.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for defining custom quest requirement triggers.
 * Implementations can define their own trigger conditions and event handlers.
 *
 * @param <T> The type of event that this requirement handles
 */
public abstract class RequirementTrigger<T> {

    private final QuestRequirement requirement;
    private final List<RequirementCondition> conditions = new ArrayList<>();
    private ProgressStrategy progressStrategy = ProgressStrategy.DEFAULT;

    /**
     * Creates a new trigger with the specified requirement
     *
     * @param requirement The requirement this trigger is associated with
     */
    public RequirementTrigger(QuestRequirement requirement) {
        this.requirement = requirement;
    }
    
    /**
     * Creates a new trigger with the specified requirement and condition
     *
     * @param requirement The requirement this trigger is associated with
     * @param condition The condition to check before handling events
     */
    public RequirementTrigger(QuestRequirement requirement, RequirementCondition condition) {
        this.requirement = requirement;
        addCondition(condition);
    }

    /**
     * Adds a condition to this trigger
     *
     * @param condition The condition to add
     * @return This trigger for method chaining
     */
    public RequirementTrigger<T> addCondition(RequirementCondition condition) {
        if (condition != null) {
            this.conditions.add(condition);
        }
        return this;
    }
    
    /**
     * Removes a condition from this trigger
     *
     * @param condition The condition to remove
     * @return This trigger for method chaining
     */
    public RequirementTrigger<T> removeCondition(RequirementCondition condition) {
        this.conditions.remove(condition);
        return this;
    }
    
    /**
     * Clears all conditions from this trigger
     *
     * @return This trigger for method chaining
     */
    public RequirementTrigger<T> clearConditions() {
        this.conditions.clear();
        return this;
    }
    
    /**
     * Gets all conditions for this trigger
     *
     * @return The list of conditions
     */
    public List<RequirementCondition> getConditions() {
        return new ArrayList<>(conditions);
    }

    /**
     * Sets the progress strategy for this trigger
     * 
     * @param progressStrategy The strategy to use for adding progress
     * @return This trigger for method chaining
     */
    public RequirementTrigger<T> setProgressStrategy(ProgressStrategy progressStrategy) {
        this.progressStrategy = progressStrategy;
        return this;
    }

    /**
     * Gets the current progress strategy
     * 
     * @return The current progress strategy
     */
    public ProgressStrategy getProgressStrategy() {
        return progressStrategy;
    }

    /**
     * Checks if all conditions allow the event to be processed
     * If no conditions are set, returns true
     *
     * @return true if no conditions are set or if all conditions test passes
     */
    protected boolean checkConditions() {
        if (conditions.isEmpty()) {
            return true;
        }
        
        for (RequirementCondition condition : conditions) {
            if (!condition.test()) {
                return false;
            }
        }
        
        return true;
    }

    /**
     * Handles the incoming event and checks if it satisfies the requirement
     *
     * @param event The event to process
     * @return true if the requirement is met, false otherwise
     */
    public boolean handleEvent(T event) {
        if (!checkConditions()) {
            return false;
        }
        
        ProcessResult result = processEvent(event);
        if (result.success()) {
            addProgress(result.progressAmount());
            return true;
        }
        return false;
    }
    
    /**
     * Adds progress to the requirement based on the current progress strategy
     *
     * @param amount The amount of progress to add
     */
    protected void addProgress(int amount) {
        progressStrategy.addProgress(requirement, amount);
    }
    
    /**
     * Processes the event after condition check
     * 
     * @param event The event to process
     * @return A ProcessResult containing information about success and progress amount
     */
    protected abstract ProcessResult processEvent(T event);

    /**
     * Gets the requirement object
     *
     * @return The requirement object
     */
    public QuestRequirement getRequirement(){
        return requirement;
    }
    
    /**
     * Record representing the result of processing an event
     * 
     * @param success Whether the event was processed successfully
     * @param progressAmount The amount of progress to add if successful
     */
    public record ProcessResult(boolean success, int progressAmount) {
        /**
         * Create a successful result with the given progress amount
         * 
         * @param progressAmount The amount of progress to add
         * @return A successful process result
         */
        public static ProcessResult success(int progressAmount) {
            return new ProcessResult(true, progressAmount);
        }
        
        /**
         * Create a failed result with no progress
         * 
         * @return A failed process result
         */
        public static ProcessResult failure() {
            return new ProcessResult(false, 0);
        }
    }
    
    /**
     * Strategy for handling progress additions to requirements
     */
    @FunctionalInterface
    public interface ProgressStrategy {
        /**
         * Add progress to the requirement
         * 
         * @param requirement The requirement to add progress to
         * @param amount The amount of progress to add
         */
        void addProgress(QuestRequirement requirement, int amount);
        
        /**
         * Default strategy that simply adds the progress amount to the requirement
         */
        ProgressStrategy DEFAULT = QuestRequirement::addProgress;
        
        /**
         * Strategy that doubles the progress amount
         */
        ProgressStrategy DOUBLE = (req, amount) -> req.addProgress(amount * 2);
        
        /**
         * Strategy that adds progress and logs it
         */
        ProgressStrategy LOGGED = (req, amount) -> {
            req.addProgress(amount);
            System.out.println("Added " + amount + " progress to requirement " + req);
        };
    }
}
