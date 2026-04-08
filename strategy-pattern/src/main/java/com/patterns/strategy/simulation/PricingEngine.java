package com.patterns.strategy.simulation;

import com.patterns.strategy.simulation.model.PricingContext;
import com.patterns.strategy.simulation.model.PricingResult;
import com.patterns.strategy.simulation.strategy.PricingStrategy;
import com.patterns.strategy.simulation.strategy.PricingStrategyRegistry;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Pricing engine that selects and applies the appropriate pricing strategy.
 * 
 * This class is responsible for:
 * 1. Determining which pricing strategies are eligible for a given context
 * 2. Selecting the best strategy (the one that gives the lowest final price)
 * 3. Applying the strategy and returning the result
 *
 */
public class PricingEngine {

    private final List<PricingStrategy> availableStrategies;

    public PricingEngine() {
        this.availableStrategies = Arrays.stream(PricingStrategyRegistry.values())
                .map(PricingStrategyRegistry::getInstanceOfStrategy).toList();
    }

    /**
     * Calculate the best price for the given context.
     * Steps:
     * 1. Find all eligible strategies for this context
     * 2. Calculate price using each eligible strategy
     * 3. Return the result with the lowest final price (best for customer)
     * 4. If no strategies apply, use standard pricing
     * 
     * @param context pricing context containing order and customer information
     * @return the pricing result from the best strategy
     */
    public PricingResult calculatePrice(PricingContext context) {
        return this.availableStrategies.stream()
                .filter(strategy -> strategy.isApplicable(context))
                .map(strategy -> strategy.calculatePrice(context))
                .min(Comparator.comparing(PricingResult::getFinalPrice))
                .orElseThrow(() -> new IllegalStateException("No pricing strategies available for the given context"));
    }
}
