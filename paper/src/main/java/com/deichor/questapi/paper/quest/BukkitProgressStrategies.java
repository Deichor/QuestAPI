package com.deichor.questapi.paper.quest;

import com.deichor.questapi.core.model.QuestRequirement;
import com.deichor.questapi.core.model.RequirementTrigger;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Factory class for creating custom progress strategies for Bukkit requirements
 */
public class BukkitProgressStrategies {

    /**
     * Creates a strategy that adds different progress amounts based on a condition
     *
     * @param condition Function to evaluate a condition on the requirement
     * @param trueValue Progress amount if condition is true
     * @param falseValue Progress amount if condition is false
     * @return The conditional progress strategy
     */
    public static <R extends QuestRequirement> RequirementTrigger.ProgressStrategy conditional(
            Function<R, Boolean> condition,
            int trueValue,
            int falseValue
    ) {
        return (requirement, amount) -> {
            try {
                @SuppressWarnings("unchecked")
                R typedRequirement = (R) requirement;
                int progress = condition.apply(typedRequirement) ? trueValue : falseValue;
                requirement.addProgress(progress);
            } catch (ClassCastException e) {
                // Fall back to default if type mismatch
                requirement.addProgress(falseValue);
            }
        };
    }

    /**
     * Creates a strategy that adds progress with a multiplier based on certain conditions
     *
     * @param multiplier The function that determines the multiplier
     * @return The multiplier progress strategy
     */
    public static <R extends QuestRequirement> RequirementTrigger.ProgressStrategy multiplier(
            Function<R, Double> multiplier
    ) {
        return (requirement, amount) -> {
            try {
                @SuppressWarnings("unchecked")
                R typedRequirement = (R) requirement;
                double multi = multiplier.apply(typedRequirement);
                requirement.addProgress((int)(amount * multi));
            } catch (ClassCastException e) {
                // Fall back to default if type mismatch
                requirement.addProgress(amount);
            }
        };
    }

    /**
     * Creates a strategy that notifies something external when progress is added
     *
     * @param progressAction The action to perform when progress is added
     * @return The notification progress strategy
     */
    public static RequirementTrigger.ProgressStrategy withNotification(
            BiConsumer<QuestRequirement, Integer> progressAction
    ) {
        return (requirement, amount) -> {
            requirement.addProgress(amount);
            progressAction.accept(requirement, amount);
        };
    }

    /**
     * Creates a strategy that has a chance to add bonus progress
     *
     * @param bonusChance Chance (0.0-1.0) to add bonus progress
     * @param bonusAmount Amount of bonus progress to add
     * @return The bonus progress strategy
     */
    public static RequirementTrigger.ProgressStrategy withBonusChance(double bonusChance, int bonusAmount) {
        return (requirement, amount) -> {
            requirement.addProgress(amount);
            
            if (Math.random() < bonusChance) {
                requirement.addProgress(bonusAmount);
            }
        };
    }
}