package com.deichor.questapi.core.model;

/**
 * Interface defining the requirements for quest completion
 */
public interface QuestRequirement {

    /**
     * Gets the name of this requirement
     *
     * @return The requirement name
     */
    String getName();
    
    /**
     * Gets the current progress towards completing this requirement
     *
     * @return The current progress
     */
    int getProgress();
    
    /**
     * Gets the target progress needed to complete this requirement
     *
     * @return The target progress
     */
    int getTargetProgress();
    
    /**
     * Adds progress to this requirement
     *
     * @param amount The amount of progress to add
     * @return The new progress value
     */
    int addProgress(int amount);
    
    /**
     * Sets the progress of this requirement
     *
     * @param progress The progress to set
     */
    void setProgress(int progress);
    
    /**
     * Checks if this requirement is completed
     *
     * @return true if the requirement is completed, false otherwise
     */
    boolean isCompleted();
    
    /**
     * Sets the completion callback that will be executed when this requirement is completed
     *
     * @param callback The callback to execute
     */
    void setCompletionCallback(Runnable callback);
    
    /**
     * Gets the completion percentage of this requirement
     *
     * @return The completion percentage (0-100)
     */
    default double getCompletionPercentage() {
        if (getTargetProgress() == 0) return 100.0;
        return Math.min(100.0, ((double) getProgress() / getTargetProgress()) * 100.0);
    }
}
