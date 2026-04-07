package com.patterns.strategy.simulation.strategy;

import com.patterns.strategy.simulation.model.PricingContext;
import com.patterns.strategy.simulation.model.PricingResult;

/**
 * First-time buyer pricing with 10% discount.
 * 
 * TODO: Implement the PricingStrategy interface.
 * Applies to customers making their first purchase.
 */
public class FirstTimeBuyerPricing implements PricingStrategy {
    
    private static final double FIRST_TIMER_DISCOUNT_PERCENTAGE = 0.10; // 10%
    
    @Override
    public PricingResult calculatePrice(PricingContext context) {
        // TODO: Implement first-time buyer pricing
        // Calculate 10% discount
        // Return a PricingResult with calculated values
        
        return null; // Replace this
    }
    
    @Override
    public String getStrategyName() {
        return "First Time Buyer Pricing";
    }
}
