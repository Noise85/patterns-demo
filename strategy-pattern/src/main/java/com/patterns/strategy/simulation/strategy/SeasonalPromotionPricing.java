package com.patterns.strategy.simulation.strategy;

import com.patterns.strategy.simulation.model.PricingContext;
import com.patterns.strategy.simulation.model.PricingResult;

import java.util.Objects;

/**
 * Seasonal promotion pricing with 20% discount.
 * Applies during promotional periods.
 */
public class SeasonalPromotionPricing implements PricingStrategy {
    
    private static final double PROMOTION_DISCOUNT_PERCENTAGE = 0.20; // 20%
    
    @Override
    public PricingResult calculatePrice(PricingContext context) {
        if(isApplicable(context)) {
            return calculatePrice(context, PROMOTION_DISCOUNT_PERCENTAGE);
        }
        return calculatePrice(context, NO_DISCOUNT_RATE);
    }

    @Override
    public boolean isApplicable(PricingContext context) {
        Objects.requireNonNull(context);
        return context.isPromotionalPeriod();
    }

}
