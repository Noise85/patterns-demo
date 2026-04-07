package com.patterns.strategy.simulation;

import com.patterns.strategy.simulation.model.PricingContext;
import com.patterns.strategy.simulation.model.PricingResult;
import com.patterns.strategy.simulation.strategy.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Pricing engine that selects and applies the appropriate pricing strategy.
 * 
 * This class is responsible for:
 * 1. Determining which pricing strategies are eligible for a given context
 * 2. Selecting the best strategy (the one that gives the lowest final price)
 * 3. Applying the strategy and returning the result
 * 
 * TODO: Complete the implementation to select and apply pricing strategies.
 */
public class PricingEngine {
    
    // Available strategies
    private final StandardPricing standardPricing = new StandardPricing();
    private final VipMemberPricing vipMemberPricing = new VipMemberPricing();
    private final BulkDiscountPricing bulkDiscountPricing = new BulkDiscountPricing();
    private final SeasonalPromotionPricing seasonalPromotionPricing = new SeasonalPromotionPricing();
    private final FirstTimeBuyerPricing firstTimeBuyerPricing = new FirstTimeBuyerPricing();
    
    /**
     * Calculate the best price for the given context.
     * 
     * TODO: Implement the strategy selection and application logic.
     * 
     * Steps:
     * 1. Find all eligible strategies for this context
     * 2. Calculate price using each eligible strategy
     * 3. Return the result with the lowest final price (best for customer)
     * 4. If no strategies apply, use standard pricing
     * 
     * @param context pricing context containing order and customer information
     * @return the pricing result from the best strategy
     */
    public PricingResult calculatePrice(PricingContext context) {
        // TODO: Implement strategy selection logic
        // 
        // Hint: You can:
        // 1. Create a list of eligible strategies based on context
        // 2. Calculate price with each strategy
        // 3. Compare final prices and pick the lowest
        //
        // Or:
        // 1. Check conditions and apply the appropriate strategy directly
        //
        // Remember: When multiple strategies apply, choose the best one for the customer
        
        return null; // Replace this
    }
    
    /**
     * Helper method to determine if a strategy is eligible for the given context.
     * 
     * TODO: Implement eligibility logic for each strategy type.
     * 
     * @param strategy the strategy to check
     * @param context the pricing context
     * @return true if the strategy should be considered
     */
    private boolean isEligible(PricingStrategy strategy, PricingContext context) {
        // TODO: Implement eligibility rules
        // 
        // VipMemberPricing: eligible if customerType is "VIP"
        // BulkDiscountPricing: eligible if itemCount >= 10
        // SeasonalPromotionPricing: eligible if isPromotionalPeriod is true
        // FirstTimeBuyerPricing: eligible if isFirstTimeBuyer is true
        // StandardPricing: always eligible (fallback)
        //
        // Hint: Use instanceof or strategy.getStrategyName() to identify strategy types
        
        return false; // Replace this
    }
    
    /**
     * Helper method to find the best result (lowest final price).
     * 
     * TODO: Implement comparison logic to find the result with the lowest final price.
     * 
     * @param results list of pricing results from eligible strategies
     * @return the result with the lowest final price
     */
    private PricingResult findBestResult(List<PricingResult> results) {
        // TODO: Compare results and return the one with the lowest finalPrice
        // Hint: You can iterate through results and track the minimum
        
        return null; // Replace this
    }
}
