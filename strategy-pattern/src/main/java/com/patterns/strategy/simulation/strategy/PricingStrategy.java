package com.patterns.strategy.simulation.strategy;

import com.patterns.strategy.simulation.model.PricingContext;
import com.patterns.strategy.simulation.model.PricingResult;

/**
 * Strategy interface for pricing calculations.
 * 
 * Each concrete strategy implements a different pricing algorithm.
 */
public interface PricingStrategy {
    
    /**
     * Calculate the final price based on the pricing context.
     * 
     * @param context contains all information needed for pricing decisions
     * @return pricing result with final price and audit information
     */
    PricingResult calculatePrice(PricingContext context);
    
    /**
     * Get the name of this pricing strategy (for audit trail).
     * 
     * @return strategy name
     */
    String getStrategyName();
}
