package com.patterns.strategy.simulation.strategy;

import com.patterns.strategy.simulation.model.PricingContext;
import com.patterns.strategy.simulation.model.PricingResult;

/**
 * Seasonal promotion pricing with 20% discount.
 * 
 * TODO: Implement the PricingStrategy interface.
 * Applies during promotional periods.
 */
public class SeasonalPromotionPricing implements PricingStrategy {
    
    private static final double PROMOTION_DISCOUNT_PERCENTAGE = 0.20; // 20%
    
    @Override
    public PricingResult calculatePrice(PricingContext context) {
        // TODO: Implement seasonal promotion pricing
        // Calculate 20% discount
        // Return a PricingResult with calculated values
        
        return null; // Replace this
    }
    
    @Override
    public String getStrategyName() {
        return "Seasonal Promotion Pricing";
    }
}
