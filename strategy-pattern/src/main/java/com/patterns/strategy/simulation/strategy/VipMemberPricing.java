package com.patterns.strategy.simulation.strategy;

import com.patterns.strategy.simulation.model.PricingContext;
import com.patterns.strategy.simulation.model.PricingResult;

/**
 * VIP member pricing with 15% discount.
 * 
 * TODO: Implement the PricingStrategy interface.
 * Applies to customers with customerType "VIP".
 */
public class VipMemberPricing implements PricingStrategy {
    
    private static final double VIP_DISCOUNT_PERCENTAGE = 0.15; // 15%
    
    @Override
    public PricingResult calculatePrice(PricingContext context) {
        // TODO: Implement VIP member pricing
        // Calculate 15% discount
        // Return a PricingResult with:
        // - originalPrice = context.getOrderTotal()
        // - discountAmount = original * VIP_DISCOUNT_PERCENTAGE
        // - finalPrice = original - discount
        // - strategyName = this.getStrategyName()
        
        return null; // Replace this
    }
    
    @Override
    public String getStrategyName() {
        return "VIP Member Pricing";
    }
}
