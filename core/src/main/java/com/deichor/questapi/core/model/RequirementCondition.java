package com.deichor.questapi.core.model;

/**
 * Interface representing a condition that can be tested to determine
 * if a quest requirement is applicable or not.
 */
public interface RequirementCondition {

    /**
     * Tests if the condition is met
     *
     * @return true if the condition is satisfied, false otherwise
     */
    boolean test();
    
    /**
     * Gets a description of this condition
     *
     * @return a human-readable description of the condition
     */
    default String getDescription() {
        return "Requirement condition";
    }
}
