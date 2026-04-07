package com.patterns.strategy.simulation.model;

/**
 * Result of a pricing calculation.
 * 
 * Contains the final price, original price, discount applied, and which strategy was used.
 * This provides a complete audit trail for pricing decisions.
 */
public class PricingResult {
    private final double originalPrice;
    private final double finalPrice;
    private final double discountAmount;
    private final String strategyName;
    
    public PricingResult(double originalPrice, double finalPrice, 
                        double discountAmount, String strategyName) {
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.discountAmount = discountAmount;
        this.strategyName = strategyName;
    }
    
    public double getOriginalPrice() {
        return originalPrice;
    }
    
    public double getFinalPrice() {
        return finalPrice;
    }
    
    public double getDiscountAmount() {
        return discountAmount;
    }
    
    public String getStrategyName() {
        return strategyName;
    }
    
    public double getDiscountPercentage() {
        if (originalPrice == 0) {
            return 0;
        }
        return (discountAmount / originalPrice) * 100;
    }
    
    @Override
    public String toString() {
        return String.format("PricingResult{original=$%.2f, final=$%.2f, discount=$%.2f (%.1f%%), strategy='%s'}",
            originalPrice, finalPrice, discountAmount, getDiscountPercentage(), strategyName);
    }
}
