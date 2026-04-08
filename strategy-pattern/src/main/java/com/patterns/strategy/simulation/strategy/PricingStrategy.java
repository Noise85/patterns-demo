package com.patterns.strategy.simulation.strategy;

import com.patterns.strategy.simulation.model.PricingContext;
import com.patterns.strategy.simulation.model.PricingResult;

import java.util.Objects;

/**
 * Strategy interface for pricing calculations.
 * 
 * Each concrete strategy implements a different pricing algorithm.
 */
public interface PricingStrategy {

    double NO_DISCOUNT_RATE = 0.0d;
    
    /**
     * Calculate the final price based on the pricing context.
     * 
     * @param context contains all information needed for pricing decisions
     * @return pricing result with final price and audit information
     */
    PricingResult calculatePrice(PricingContext context);
    boolean isApplicable(PricingContext context);


    /**
     * Calculate the final price with a discount percentage.
     * @param context the pricing context
     * @param discountPercentage the discount percentage
     * @return the pricing result with the discounted price
     */
    default PricingResult calculatePrice(PricingContext context, double discountPercentage) {
        Objects.requireNonNull(context, "context must not be null");
        Objects.requireNonNull(context.getOrder(), "Order must not be null");
        var order = context.getOrder();
        return new PricingResult(order.getTotal(),order.getTotal()*(1-discountPercentage),
                order.getTotal()*discountPercentage, getStrategyName());
    }
    
    /**
     * Get the name of this pricing strategy (for audit trail).
     * 
     * @return strategy name
     */
    default String getStrategyName() {
        return PricingStrategyRegistry.nameFor(this.getClass());
    }
}
