package com.patterns.strategy.simulation.strategy;

import com.patterns.strategy.simulation.model.PricingContext;
import com.patterns.strategy.simulation.model.PricingResult;

import java.util.Objects;

/**
 * Bulk discount pricing based on item quantity.
 * Discount tiers:
 * - TIER_1: 10-49 items: 5% off
 * - TIER_2: 50-99 items: 10% off
 * - TIER_3: 100+ items: 15% off
 * - Less than TIER_1: no discount
 */
public class BulkDiscountPricing implements PricingStrategy {

    private static final double TIER_3_THRESHOLD = 100;
    private static final double TIER_3_DISCOUNT = 0.15;
    private static final double TIER_2_THRESHOLD = 50;
    private static final double TIER_2_DISCOUNT = 0.10;
    private static final double TIER_1_THRESHOLD = 10;
    private static final double TIER_1_DISCOUNT = 0.05;

    @Override
    public PricingResult calculatePrice(PricingContext context) {
        if (isApplicable(context)) {
            return calculatePrice(context, resolveDiscountRate(context.getOrder().getItemCount()));
        } else {
            return calculatePrice(context, NO_DISCOUNT_RATE);
        }
    }

    @Override
    public boolean isApplicable(PricingContext context) {
        Objects.requireNonNull(context, "context must not be null");
        Objects.requireNonNull(context.getOrder(), "order must not be null");
        return context.getOrder().getItemCount()>=TIER_1_THRESHOLD;
    }

    private double resolveDiscountRate(int itemCount) {
        if (itemCount >= TIER_3_THRESHOLD) return TIER_3_DISCOUNT;
        if (itemCount >= TIER_2_THRESHOLD) return TIER_2_DISCOUNT;
        if (itemCount >= TIER_1_THRESHOLD) return TIER_1_DISCOUNT;
        return NO_DISCOUNT_RATE;
    }

}
