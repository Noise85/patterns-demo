package com.patterns.strategy.simulation.strategy;

import com.patterns.strategy.simulation.model.PricingContext;
import com.patterns.strategy.simulation.model.PricingResult;

import java.util.Objects;

/**
 * First-time buyer pricing with 10% discount.
 * Applies to customers making their first purchase.
 */
public class FirstTimeBuyerPricing implements PricingStrategy {
    
    private static final double FIRST_TIMER_DISCOUNT_PERCENTAGE = 0.10;
    
    @Override
    public PricingResult calculatePrice(PricingContext context) {
        if(isApplicable(context)) {
            return calculatePrice(context, FIRST_TIMER_DISCOUNT_PERCENTAGE);
        }
        return calculatePrice(context, NO_DISCOUNT_RATE);
    }

    @Override
    public boolean isApplicable(PricingContext context) {
        Objects.requireNonNull(context.getOrder());
        return context.isFirstTimeBuyer();
    }

}
