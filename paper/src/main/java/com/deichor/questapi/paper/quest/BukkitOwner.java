package com.deichor.questapi.paper.quest;

import com.deichor.questapi.core.model.RequirementCondition;

/**
 * Represents an owner in the Bukkit environment
 * @param <T> The type of the owner
 */
public interface BukkitOwner<T> {
    
    /**
     * Gets the owner object
     * 
     * @return The owner object
     */
    T getOwner();
    
    /**
     * Gets the type of the owner
     * 
     * @return The owner type
     */
    String getOwnerType();
    
    /**
     * Checks if this owner is valid for the current context
     * This method should be implemented by all owner types to define
     * their own validation logic
     * 
     * @return true if the owner is valid, false otherwise
     */
    boolean isValid();
    
    /**
     * Creates a condition that tests if this owner is valid
     * 
     * @return A requirement condition for this owner
     */
    default RequirementCondition createCondition() {
        return new RequirementCondition() {
            @Override
            public boolean test() {
                return isValid();
            }
            
            @Override
            public String getDescription() {
                return "Owner validity check for " + getOwnerType();
            }
        };
    }
    
    /**
     * Creates a condition with a custom description
     * 
     * @param description The description for the condition
     * @return A requirement condition with the specified description
     */
    default RequirementCondition createCondition(String description) {
        return new RequirementCondition() {
            @Override
            public boolean test() {
                return isValid();
            }
            
            @Override
            public String getDescription() {
                return description;
            }
        };
    }
}
