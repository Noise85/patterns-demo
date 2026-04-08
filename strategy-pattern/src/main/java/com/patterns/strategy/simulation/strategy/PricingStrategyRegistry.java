package com.patterns.strategy.simulation.strategy;


import java.util.stream.Stream;

/**
 * Enum representing different pricing strategies.
 * Each strategy has a name and an instance of the strategy class.
 * This enum is used to map strategy names to their respective instances.
 * 1. Standard pricing
 * 2. VIP member pricing
 * 3. Bulk discount pricing
 * 4. Seasonal promotion pricing
 * 5. First-time buyer pricing
 */
public enum PricingStrategyRegistry {
    STANDARD("Standard Pricing", new StandardPricing()),
    VIP_MEMBER("VIP Member Pricing", new VipMemberPricing()),
    BULK_DISCOUNT("Bulk Discount Pricing", new BulkDiscountPricing()),
    SEASONAL_PROMOTION("Seasonal Promotion Pricing", new SeasonalPromotionPricing()),
    FIRST_TIME_BUYER("First Time Buyer Pricing", new FirstTimeBuyerPricing());

    private final String name;
    private final PricingStrategy strategy;

    PricingStrategyRegistry(String name, PricingStrategy strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    public static String nameFor(Class<? extends PricingStrategy> clazz) {
        if( clazz == null ) {
            throw new IllegalArgumentException("Strategy class cannot be null");
        }
        return Stream.of(values())
                .filter(s -> s.strategy.getClass().equals(clazz))
                .map(e -> e.name)
                .findFirst().orElseThrow();
    }

    public PricingStrategy getInstanceOfStrategy() {
        return strategy;
    }

}