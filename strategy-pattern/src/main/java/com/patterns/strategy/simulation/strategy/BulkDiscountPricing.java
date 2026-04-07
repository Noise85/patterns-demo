package com.patterns.strategy.simulation.strategy;

import com.patterns.strategy.simulation.model.PricingContext;
import com.patterns.strategy.simulation.model.PricingResult;

/**
 * Bulk discount pricing based on item quantity.
 * 
 * TODO: Implement the PricingStrategy interface.
 * 
 * Discount tiers:
 * - 10-49 items: 5% off
 * - 50-99 items: 10% off
 * - 100+ items: 15% off
 * - Less than 10 items: no discount
 */
public class BulkDiscountPricing implements PricingStrategy {
    
    @Override
    public PricingResult calculatePrice(PricingContext context) {
        // TODO: Implement bulk discount pricing
        // Determine discount percentage based on item count:
        // - itemCount >= 100: 15% discount
        // - itemCount >= 50: 10% discount
        // - itemCount >= 10: 5% discount
        // - itemCount < 10: 0% discount
        //
        // Return a PricingResult with calculated values
        
        return null; // Replace this
    }
    
    @Override
    public String getStrategyName() {
        return "Bulk Discount Pricing";
    }
}
