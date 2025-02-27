package com.deichor.questapi.core.model;

/**
 * Base implementation of QuestRequirement interface
 */
public class BaseQuestRequirement implements QuestRequirement {
    private final String name;
    private final int targetProgress;
    private int progress;
    private Runnable completionCallback;
    
    /**
     * Creates a new quest requirement
     * 
     * @param name The name of the requirement
     * @param targetProgress The target progress needed to complete this requirement
     */
    public BaseQuestRequirement(String name, int targetProgress) {
        this.name = name;
        this.targetProgress = targetProgress > 0 ? targetProgress : 1;
        this.progress = 0;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getProgress() {
        return progress;
    }
    
    @Override
    public int getTargetProgress() {
        return targetProgress;
    }
    
    @Override
    public int addProgress(int amount) {
        boolean wasCompleted = isCompleted();
        
        progress += amount;
        
        // Ensure progress doesn't exceed target
        if (progress > targetProgress) {
            progress = targetProgress;
        }
        
        // Check if the requirement was just completed
        if (!wasCompleted && isCompleted() && completionCallback != null) {
            completionCallback.run();
        }
        
        return progress;
    }
    
    @Override
    public void setProgress(int progress) {
        boolean wasCompleted = isCompleted();
        
        this.progress = progress;
        
        // Ensure progress doesn't go below 0
        if (this.progress < 0) {
            this.progress = 0;
        }
        
        // Check if the requirement was just completed
        if (!wasCompleted && isCompleted() && completionCallback != null) {
            completionCallback.run();
        }
    }
    
    @Override
    public boolean isCompleted() {
        return progress >= targetProgress;
    }

    @Override
    public void setCompletionCallback(Runnable callback) {
        this.completionCallback = callback;
        
        // If the requirement is already completed, run the callback immediately
        if (isCompleted() && callback != null) {
            callback.run();
        }
    }
    
    @Override
    public String toString() {
        return String.format("%s: %d/%d (%.1f%%)", 
            name, progress, targetProgress, getCompletionPercentage());
    }
}