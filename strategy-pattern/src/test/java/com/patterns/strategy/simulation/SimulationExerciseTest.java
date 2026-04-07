package com.patterns.strategy.simulation;

import com.patterns.strategy.simulation.model.Order;
import com.patterns.strategy.simulation.model.PricingContext;
import com.patterns.strategy.simulation.model.PricingResult;
import com.patterns.strategy.simulation.strategy.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Exercise 2: Real-World Simulation
 * 
 * Make these tests pass by implementing the pricing strategies and engine correctly.
 */
@DisplayName("Exercise 2: Dynamic Pricing Engine")
class SimulationExerciseTest {
    
    // ==================== Strategy Tests ====================
    
    @Test
    @DisplayName("StandardPricing should return original price with no discount")
    void testStandardPricing() {
        StandardPricing strategy = new StandardPricing();
        Order order = new Order(100.0, 5, LocalDate.now(), "REGULAR", false);
        PricingContext context = new PricingContext(order, false);
        
        PricingResult result = strategy.calculatePrice(context);
        
        assertThat(result.getOriginalPrice()).isEqualTo(100.0);
        assertThat(result.getFinalPrice()).isEqualTo(100.0);
        assertThat(result.getDiscountAmount()).isEqualTo(0.0);
        assertThat(result.getStrategyName()).isEqualTo("Standard Pricing");
    }
    
    @Test
    @DisplayName("VipMemberPricing should apply 15% discount")
    void testVipMemberPricing() {
        VipMemberPricing strategy = new VipMemberPricing();
        Order order = new Order(100.0, 5, LocalDate.now(), "VIP", false);
        PricingContext context = new PricingContext(order, false);
        
        PricingResult result = strategy.calculatePrice(context);
        
        assertThat(result.getOriginalPrice()).isEqualTo(100.0);
        assertThat(result.getFinalPrice()).isEqualTo(85.0); // 15% off
        assertThat(result.getDiscountAmount()).isEqualTo(15.0);
        assertThat(result.getStrategyName()).isEqualTo("VIP Member Pricing");
    }
    
    @Test
    @DisplayName("BulkDiscountPricing should apply correct tier discounts")
    void testBulkDiscountPricing() {
        BulkDiscountPricing strategy = new BulkDiscountPricing();
        
        // 5 items - no discount
        Order order1 = new Order(100.0, 5, LocalDate.now(), "REGULAR", false);
        PricingContext context1 = new PricingContext(order1, false);
        PricingResult result1 = strategy.calculatePrice(context1);
        assertThat(result1.getFinalPrice()).isEqualTo(100.0);
        assertThat(result1.getDiscountAmount()).isEqualTo(0.0);
        
        // 10 items - 5% discount
        Order order2 = new Order(100.0, 10, LocalDate.now(), "REGULAR", false);
        PricingContext context2 = new PricingContext(order2, false);
        PricingResult result2 = strategy.calculatePrice(context2);
        assertThat(result2.getFinalPrice()).isEqualTo(95.0);
        assertThat(result2.getDiscountAmount()).isEqualTo(5.0);
        
        // 50 items - 10% discount
        Order order3 = new Order(100.0, 50, LocalDate.now(), "REGULAR", false);
        PricingContext context3 = new PricingContext(order3, false);
        PricingResult result3 = strategy.calculatePrice(context3);
        assertThat(result3.getFinalPrice()).isEqualTo(90.0);
        assertThat(result3.getDiscountAmount()).isEqualTo(10.0);
        
        // 100 items - 15% discount
        Order order4 = new Order(100.0, 100, LocalDate.now(), "REGULAR", false);
        PricingContext context4 = new PricingContext(order4, false);
        PricingResult result4 = strategy.calculatePrice(context4);
        assertThat(result4.getFinalPrice()).isEqualTo(85.0);
        assertThat(result4.getDiscountAmount()).isEqualTo(15.0);
    }
    
    @Test
    @DisplayName("SeasonalPromotionPricing should apply 20% discount")
    void testSeasonalPromotionPricing() {
        SeasonalPromotionPricing strategy = new SeasonalPromotionPricing();
        Order order = new Order(100.0, 5, LocalDate.now(), "REGULAR", false);
        PricingContext context = new PricingContext(order, true);
        
        PricingResult result = strategy.calculatePrice(context);
        
        assertThat(result.getOriginalPrice()).isEqualTo(100.0);
        assertThat(result.getFinalPrice()).isEqualTo(80.0); // 20% off
        assertThat(result.getDiscountAmount()).isEqualTo(20.0);
        assertThat(result.getStrategyName()).isEqualTo("Seasonal Promotion Pricing");
    }
    
    @Test
    @DisplayName("FirstTimeBuyerPricing should apply 10% discount")
    void testFirstTimeBuyerPricing() {
        FirstTimeBuyerPricing strategy = new FirstTimeBuyerPricing();
        Order order = new Order(100.0, 5, LocalDate.now(), "REGULAR", true);
        PricingContext context = new PricingContext(order, false);
        
        PricingResult result = strategy.calculatePrice(context);
        
        assertThat(result.getOriginalPrice()).isEqualTo(100.0);
        assertThat(result.getFinalPrice()).isEqualTo(90.0); // 10% off
        assertThat(result.getDiscountAmount()).isEqualTo(10.0);
        assertThat(result.getStrategyName()).isEqualTo("First Time Buyer Pricing");
    }
    
    // ==================== Pricing Engine Tests ====================
    
    @Test
    @DisplayName("PricingEngine should use StandardPricing when no other strategies apply")
    void testEngineWithStandardPricing() {
        PricingEngine engine = new PricingEngine();
        Order order = new Order(100.0, 5, LocalDate.now(), "REGULAR", false);
        PricingContext context = new PricingContext(order, false);
        
        PricingResult result = engine.calculatePrice(context);
        
        assertThat(result.getFinalPrice()).isEqualTo(100.0);
        assertThat(result.getStrategyName()).isEqualTo("Standard Pricing");
    }
    
    @Test
    @DisplayName("PricingEngine should select VipMemberPricing for VIP customers")
    void testEngineWithVipCustomer() {
        PricingEngine engine = new PricingEngine();
        Order order = new Order(100.0, 5, LocalDate.now(), "VIP", false);
        PricingContext context = new PricingContext(order, false);
        
        PricingResult result = engine.calculatePrice(context);
        
        assertThat(result.getFinalPrice()).isEqualTo(85.0);
        assertThat(result.getStrategyName()).isEqualTo("VIP Member Pricing");
    }
    
    @Test
    @DisplayName("PricingEngine should select BulkDiscountPricing for bulk orders")
    void testEngineWithBulkOrder() {
        PricingEngine engine = new PricingEngine();
        Order order = new Order(100.0, 50, LocalDate.now(), "REGULAR", false);
        PricingContext context = new PricingContext(order, false);
        
        PricingResult result = engine.calculatePrice(context);
        
        assertThat(result.getFinalPrice()).isEqualTo(90.0);
        assertThat(result.getStrategyName()).isEqualTo("Bulk Discount Pricing");
    }
    
    @Test
    @DisplayName("PricingEngine should select SeasonalPromotionPricing during promotions")
    void testEngineWithSeasonalPromotion() {
        PricingEngine engine = new PricingEngine();
        Order order = new Order(100.0, 5, LocalDate.now(), "REGULAR", false);
        PricingContext context = new PricingContext(order, true);
        
        PricingResult result = engine.calculatePrice(context);
        
        assertThat(result.getFinalPrice()).isEqualTo(80.0);
        assertThat(result.getStrategyName()).isEqualTo("Seasonal Promotion Pricing");
    }
    
    @Test
    @DisplayName("PricingEngine should select FirstTimeBuyerPricing for first-time buyers")
    void testEngineWithFirstTimeBuyer() {
        PricingEngine engine = new PricingEngine();
        Order order = new Order(100.0, 5, LocalDate.now(), "REGULAR", true);
        PricingContext context = new PricingContext(order, false);
        
        PricingResult result = engine.calculatePrice(context);
        
        assertThat(result.getFinalPrice()).isEqualTo(90.0);
        assertThat(result.getStrategyName()).isEqualTo("First Time Buyer Pricing");
    }
    
    @Test
    @DisplayName("PricingEngine should select best strategy when multiple apply (VIP + Bulk)")
    void testEngineSelectsBestStrategyVipAndBulk() {
        PricingEngine engine = new PricingEngine();
        
        // VIP customer with bulk order (100 items)
        // VIP discount: 15% -> $85
        // Bulk discount (100+ items): 15% -> $85
        // Both give same price, either is acceptable
        Order order = new Order(100.0, 100, LocalDate.now(), "VIP", false);
        PricingContext context = new PricingContext(order, false);
        
        PricingResult result = engine.calculatePrice(context);
        
        assertThat(result.getFinalPrice()).isEqualTo(85.0);
        // Either VIP or Bulk pricing is acceptable since they give the same discount
    }
    
    @Test
    @DisplayName("PricingEngine should select best strategy when multiple apply (Seasonal + FirstTime)")
    void testEngineSelectsBestStrategySeasonalAndFirstTime() {
        PricingEngine engine = new PricingEngine();
        
        // First-time buyer during seasonal promotion
        // Seasonal: 20% -> $80 (BEST)
        // First-time: 10% -> $90
        Order order = new Order(100.0, 5, LocalDate.now(), "REGULAR", true);
        PricingContext context = new PricingContext(order, true);
        
        PricingResult result = engine.calculatePrice(context);
        
        assertThat(result.getFinalPrice()).isEqualTo(80.0);
        assertThat(result.getStrategyName()).isEqualTo("Seasonal Promotion Pricing");
    }
    
    @Test
    @DisplayName("PricingEngine should select best strategy in complex scenario")
    void testEngineComplexScenarioAllStrategies() {
        PricingEngine engine = new PricingEngine();
        
        // VIP customer, first-time buyer, bulk order, during promotion
        // VIP: 15% -> $85
        // Bulk (10 items): 5% -> $95
        // Seasonal: 20% -> $80 (BEST)
        // First-time: 10% -> $90
        Order order = new Order(100.0, 10, LocalDate.now(), "VIP", true);
        PricingContext context = new PricingContext(order, true);
        
        PricingResult result = engine.calculatePrice(context);
        
        // Best discount is seasonal promotion (20%)
        assertThat(result.getFinalPrice()).isEqualTo(80.0);
        assertThat(result.getStrategyName()).isEqualTo("Seasonal Promotion Pricing");
    }
    
    // ==================== Edge Cases ====================
    
    @Test
    @DisplayName("PricingEngine should handle zero amount orders")
    void testEngineWithZeroAmount() {
        PricingEngine engine = new PricingEngine();
        Order order = new Order(0.0, 5, LocalDate.now(), "VIP", false);
        PricingContext context = new PricingContext(order, false);
        
        PricingResult result = engine.calculatePrice(context);
        
        assertThat(result.getOriginalPrice()).isEqualTo(0.0);
        assertThat(result.getFinalPrice()).isEqualTo(0.0);
    }
    
    @Test
    @DisplayName("PricingEngine should handle large order amounts correctly")
    void testEngineWithLargeAmount() {
        PricingEngine engine = new PricingEngine();
        Order order = new Order(10000.0, 100, LocalDate.now(), "REGULAR", false);
        PricingContext context = new PricingContext(order, false);
        
        PricingResult result = engine.calculatePrice(context);
        
        // Bulk discount (100+ items): 15%
        assertThat(result.getOriginalPrice()).isEqualTo(10000.0);
        assertThat(result.getFinalPrice()).isEqualTo(8500.0);
        assertThat(result.getDiscountAmount()).isEqualTo(1500.0);
    }
    
    @Test
    @DisplayName("All pricing results should preserve original price")
    void testOriginalPriceIsPreserved() {
        PricingEngine engine = new PricingEngine();
        Order order = new Order(100.0, 5, LocalDate.now(), "VIP", false);
        PricingContext context = new PricingContext(order, false);
        
        PricingResult result = engine.calculatePrice(context);
        
        // Original price should always be preserved for audit trail
        assertThat(result.getOriginalPrice()).isEqualTo(100.0);
        assertThat(result.getFinalPrice()).isLessThan(result.getOriginalPrice());
    }
}
