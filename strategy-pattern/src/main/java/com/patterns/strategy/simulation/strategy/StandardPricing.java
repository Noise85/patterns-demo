package com.patterns.strategy.simulation.strategy;

import com.patterns.strategy.simulation.model.PricingContext;
import com.patterns.strategy.simulation.model.PricingResult;

/**
 * Standard pricing with no discounts.
 * This is the default/fallback strategy when no other strategy applies.
 */
public class StandardPricing implements PricingStrategy {
    
    @Override
    public PricingResult calculatePrice(PricingContext context) {
        return calculatePrice(context, NO_DISCOUNT_RATE);
    }

    @Override
    public boolean isApplicable(PricingContext context) {
        return true;
    }

}
