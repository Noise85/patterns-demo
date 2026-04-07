package com.patterns.strategy.simulation.strategy;

import com.patterns.strategy.simulation.model.PricingContext;
import com.patterns.strategy.simulation.model.PricingResult;

/**
 * Standard pricing with no discounts.
 * 
 * TODO: Implement the PricingStrategy interface.
 * This is the default/fallback strategy when no other strategy applies.
 */
public class StandardPricing implements PricingStrategy {
    
    @Override
    public PricingResult calculatePrice(PricingContext context) {
        // TODO: Implement standard pricing (no discount)
        // Return a PricingResult with:
        // - originalPrice = context.getOrderTotal()
        // - finalPrice = same as original (no discount)
        // - discountAmount = 0
        // - strategyName = this.getStrategyName()
        
        return null; // Replace this
    }
    
    @Override
    public String getStrategyName() {
        return "Standard Pricing";
    }
}
