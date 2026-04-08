package com.patterns.strategy.simulation.strategy;

import com.patterns.strategy.simulation.model.PricingContext;
import com.patterns.strategy.simulation.model.PricingResult;

import java.util.Objects;

/**
 * VIP member pricing with 15% discount.
 * Applies to customers with customerType "VIP".
 */
public class VipMemberPricing implements PricingStrategy {
    
    private static final double VIP_DISCOUNT_PERCENTAGE = 0.15; // 15%
    
    @Override
    public PricingResult calculatePrice(PricingContext context) {
        if(isApplicable(context)) {
            return calculatePrice(context, VIP_DISCOUNT_PERCENTAGE);
        }
        return calculatePrice(context, NO_DISCOUNT_RATE);
    }

    @Override
    public boolean isApplicable(PricingContext context) {
        Objects.requireNonNull(context.getOrder());
        return "VIP".equals(context.getOrder().getCustomerType());
    }

}
