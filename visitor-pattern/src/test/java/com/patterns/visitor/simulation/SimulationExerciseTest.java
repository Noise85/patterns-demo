package com.patterns.visitor.simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Visitor Pattern - Simulation Exercise (E-Commerce Order).
 */
@DisplayName("Visitor Pattern - E-Commerce Order Processing")
class SimulationExerciseTest {
    
    private Order order;
    private LineItem laptop;
    private LineItem mouse;
    
    @BeforeEach
    void setUp() {
        order = new Order("ORD-12345", "CUST-789");
        laptop = new LineItem("PROD-1", "Laptop", 1, new BigDecimal("999.99"));
        mouse = new LineItem("PROD-2", "Mouse", 2, new BigDecimal("29.99"));
    }
    
    @Test
    @DisplayName("Should add component to order")
    void testAddComponent() {
        order.addComponent(laptop);
        
        assertThat(order.getComponentCount()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("Should validate order with line items")
    void testValidateWithLineItems() {
        order.addComponent(laptop);
        order.addComponent(new ShippingInfo(ShippingMethod.STANDARD, "123 Main St", "Springfield", "12345"));
        
        OrderValidationVisitor validator = new OrderValidationVisitor();
        order.accept(validator);
        
        assertThat(validator.isValid()).isTrue();
    }
    
    @Test
    @DisplayName("Should detect missing line items")
    void testValidateMissingLineItems() {
        OrderValidationVisitor validator = new OrderValidationVisitor();
        order.accept(validator);
        
        assertThat(validator.isValid()).isFalse();
        assertThat(validator.getErrors()).isNotEmpty();
    }
    
    @Test
    @DisplayName("Should detect invalid quantity")
    void testValidateInvalidQuantity() {
        order.addComponent(new LineItem("PROD-1", "Invalid", 0, new BigDecimal("10.00")));
        
        OrderValidationVisitor validator = new OrderValidationVisitor();
        order.accept(validator);
        
        assertThat(validator.isValid()).isFalse();
        assertThat(validator.getErrors()).anyMatch(e -> e.contains("quantity"));
    }
    
    @Test
    @DisplayName("Should validate discount minimum order amount")
    void testValidateDiscountMinimum() {
        order.addComponent(new LineItem("PROD-1", "Cheap Item", 1, new BigDecimal("10.00")));
        order.addComponent(new DiscountCoupon("SAVE20", DiscountType.PERCENTAGE, 
                                              new BigDecimal("20"), new BigDecimal("100.00")));
        
        OrderValidationVisitor validator = new OrderValidationVisitor();
        order.accept(validator);
        
        assertThat(validator.isValid()).isFalse();
        assertThat(validator.getErrors()).anyMatch(e -> e.contains("minimum"));
    }
    
    @Test
    @DisplayName("Should validate shipping address")
    void testValidateShippingAddress() {
        order.addComponent(laptop);
        order.addComponent(new ShippingInfo(ShippingMethod.STANDARD, "", "Springfield", "12345"));
        
        OrderValidationVisitor validator = new OrderValidationVisitor();
        order.accept(validator);
        
        assertThat(validator.isValid()).isFalse();
        assertThat(validator.getErrors()).anyMatch(e -> e.contains("address") || e.contains("Address"));
    }
    
    @Test
    @DisplayName("Should calculate subtotal")
    void testCalculateSubtotal() {
        order.addComponent(laptop);
        order.addComponent(mouse);
        
        PriceCalculatorVisitor calculator = new PriceCalculatorVisitor();
        order.accept(calculator);
        
        // Laptop: $999.99, Mouse: 2 × $29.99 = $59.98
        assertThat(calculator.getSubtotal()).isEqualByComparingTo(new BigDecimal("1059.97"));
    }
    
    @Test
    @DisplayName("Should calculate percentage discount")
    void testCalculatePercentageDiscount() {
        order.addComponent(laptop);
        order.addComponent(mouse);
        order.addComponent(new DiscountCoupon("SAVE20", DiscountType.PERCENTAGE, 
                                              new BigDecimal("20"), new BigDecimal("500")));
        
        PriceCalculatorVisitor calculator = new PriceCalculatorVisitor();
        order.accept(calculator);
        
        // 20% of $1059.97 = $211.99
        assertThat(calculator.getDiscountAmount()).isEqualByComparingTo(new BigDecimal("211.99"));
    }
    
    @Test
    @DisplayName("Should calculate fixed amount discount")
    void testCalculateFixedDiscount() {
        order.addComponent(laptop);
        order.addComponent(new DiscountCoupon("SAVE50", DiscountType.FIXED_AMOUNT, 
                                              new BigDecimal("50.00"), new BigDecimal("100")));
        
        PriceCalculatorVisitor calculator = new PriceCalculatorVisitor();
        order.accept(calculator);
        
        assertThat(calculator.getDiscountAmount()).isEqualByComparingTo(new BigDecimal("50.00"));
    }
    
    @Test
    @DisplayName("Should cap fixed discount at subtotal")
    void testFixedDiscountCapped() {
        order.addComponent(new LineItem("PROD-1", "Cheap", 1, new BigDecimal("10.00")));
        order.addComponent(new DiscountCoupon("HUGE", DiscountType.FIXED_AMOUNT, 
                                              new BigDecimal("1000.00"), BigDecimal.ZERO));
        
        PriceCalculatorVisitor calculator = new PriceCalculatorVisitor();
        order.accept(calculator);
        
        assertThat(calculator.getDiscountAmount()).isEqualByComparingTo(new BigDecimal("10.00"));
    }
    
    @Test
    @DisplayName("Should calculate standard shipping cost")
    void testStandardShipping() {
        order.addComponent(laptop);
        order.addComponent(new ShippingInfo(ShippingMethod.STANDARD, "123 Main", "City", "12345"));
        
        PriceCalculatorVisitor calculator = new PriceCalculatorVisitor();
        order.accept(calculator);
        
        assertThat(calculator.getShippingCost()).isEqualByComparingTo(new BigDecimal("5.00"));
    }
    
    @Test
    @DisplayName("Should calculate express shipping cost")
    void testExpressShipping() {
        order.addComponent(laptop);
        order.addComponent(new ShippingInfo(ShippingMethod.EXPRESS, "123 Main", "City", "12345"));
        
        PriceCalculatorVisitor calculator = new PriceCalculatorVisitor();
        order.accept(calculator);
        
        assertThat(calculator.getShippingCost()).isEqualByComparingTo(new BigDecimal("12.00"));
    }
    
    @Test
    @DisplayName("Should calculate overnight shipping cost")
    void testOvernightShipping() {
        order.addComponent(laptop);
        order.addComponent(new ShippingInfo(ShippingMethod.OVERNIGHT, "123 Main", "City", "12345"));
        
        PriceCalculatorVisitor calculator = new PriceCalculatorVisitor();
        order.accept(calculator);
        
        assertThat(calculator.getShippingCost()).isEqualByComparingTo(new BigDecimal("25.00"));
    }
    
    @Test
    @DisplayName("Should calculate gift wrap cost")
    void testGiftWrapCost() {
        order.addComponent(laptop);
        order.addComponent(new GiftWrap("Happy Birthday!", "Premium", new BigDecimal("9.99")));
        
        PriceCalculatorVisitor calculator = new PriceCalculatorVisitor();
        order.accept(calculator);
        
        assertThat(calculator.getGiftWrapCost()).isEqualByComparingTo(new BigDecimal("9.99"));
    }
    
    @Test
    @DisplayName("Should calculate tax on discounted amount")
    void testTaxCalculation() {
        order.addComponent(new LineItem("PROD-1", "Item", 1, new BigDecimal("100.00")));
        order.addComponent(new DiscountCoupon("SAVE10", DiscountType.FIXED_AMOUNT, 
                                              new BigDecimal("10.00"), BigDecimal.ZERO));
        
        PriceCalculatorVisitor calculator = new PriceCalculatorVisitor();
        order.accept(calculator);
        
        // Tax on $90 (after discount) at 8% = $7.20
        assertThat(calculator.getTaxAmount()).isEqualByComparingTo(new BigDecimal("7.20"));
    }
    
    @Test
    @DisplayName("Should calculate grand total")
    void testGrandTotal() {
        order.addComponent(laptop);
        order.addComponent(mouse);
        order.addComponent(new DiscountCoupon("SAVE20", DiscountType.PERCENTAGE, 
                                              new BigDecimal("20"), new BigDecimal("500")));
        order.addComponent(new ShippingInfo(ShippingMethod.EXPRESS, "123 Main", "City", "12345"));
        order.addComponent(new GiftWrap("Enjoy!", "Premium", new BigDecimal("9.99")));
        
        PriceCalculatorVisitor calculator = new PriceCalculatorVisitor();
        order.accept(calculator);
        
        // Subtotal: $1059.97
        // Discount: -$211.99
        // Tax (8% on $847.98): $67.84
        // Shipping: $12.00
        // Gift Wrap: $9.99
        // Total: $925.81
        assertThat(calculator.getGrandTotal()).isEqualByComparingTo(new BigDecimal("925.81"));
    }
    
    @Test
    @DisplayName("Should generate invoice with header")
    void testInvoiceHeader() {
        order.addComponent(laptop);
        
        InvoiceGeneratorVisitor invoiceGen = new InvoiceGeneratorVisitor();
        order.accept(invoiceGen);
        
        String invoice = invoiceGen.getInvoice();
        
        assertThat(invoice).contains("ORD-12345");
        assertThat(invoice).contains("CUST-789");
    }
    
    @Test
    @DisplayName("Should generate invoice with line items")
    void testInvoiceLineItems() {
        order.addComponent(laptop);
        order.addComponent(mouse);
        
        InvoiceGeneratorVisitor invoiceGen = new InvoiceGeneratorVisitor();
        order.accept(invoiceGen);
        
        String invoice = invoiceGen.getInvoice();
        
        assertThat(invoice).contains("Laptop");
        assertThat(invoice).contains("Mouse");
        assertThat(invoice).contains("999.99");
        assertThat(invoice).contains("29.99");
    }
    
    @Test
    @DisplayName("Should generate invoice with discount")
    void testInvoiceDiscount() {
        order.addComponent(laptop);
        order.addComponent(new DiscountCoupon("SAVE20", DiscountType.PERCENTAGE, 
                                              new BigDecimal("20"), new BigDecimal("500")));
        
        InvoiceGeneratorVisitor invoiceGen = new InvoiceGeneratorVisitor();
        order.accept(invoiceGen);
        
        String invoice = invoiceGen.getInvoice();
        
        assertThat(invoice).containsIgnoringCase("discount");
        assertThat(invoice).contains("SAVE20");
    }
    
    @Test
    @DisplayName("Should collect total item count")
    void testAnalyticsTotalItems() {
        order.addComponent(laptop);  // quantity 1
        order.addComponent(mouse);   // quantity 2
        
        OrderAnalyticsVisitor analytics = new OrderAnalyticsVisitor();
        order.accept(analytics);
        
        assertThat(analytics.getTotalItemCount()).isEqualTo(3);
    }
    
    @Test
    @DisplayName("Should count unique products")
    void testAnalyticsUniqueProducts() {
        order.addComponent(laptop);
        order.addComponent(mouse);
        order.addComponent(new LineItem("PROD-1", "Laptop", 2, new BigDecimal("999.99")));
        
        OrderAnalyticsVisitor analytics = new OrderAnalyticsVisitor();
        order.accept(analytics);
        
        assertThat(analytics.getUniqueProductCount()).isEqualTo(2);
    }
    
    @Test
    @DisplayName("Should track most expensive item")
    void testAnalyticsMostExpensive() {
        order.addComponent(laptop);
        order.addComponent(mouse);
        
        OrderAnalyticsVisitor analytics = new OrderAnalyticsVisitor();
        order.accept(analytics);
        
        assertThat(analytics.getMostExpensiveItemPrice()).isEqualByComparingTo(new BigDecimal("999.99"));
    }
    
    @Test
    @DisplayName("Should detect discount presence")
    void testAnalyticsHasDiscount() {
        order.addComponent(laptop);
        order.addComponent(new DiscountCoupon("SAVE10", DiscountType.PERCENTAGE, 
                                              new BigDecimal("10"), BigDecimal.ZERO));
        
        OrderAnalyticsVisitor analytics = new OrderAnalyticsVisitor();
        order.accept(analytics);
        
        assertThat(analytics.hasDiscount()).isTrue();
    }
    
    @Test
    @DisplayName("Should detect gift wrap presence")
    void testAnalyticsHasGiftWrap() {
        order.addComponent(laptop);
        order.addComponent(new GiftWrap("Gift", "Standard", new BigDecimal("5.00")));
        
        OrderAnalyticsVisitor analytics = new OrderAnalyticsVisitor();
        order.accept(analytics);
        
        assertThat(analytics.hasGiftWrap()).isTrue();
    }
    
    @Test
    @DisplayName("Should use multiple visitors on same order")
    void testMultipleVisitors() {
        order.addComponent(laptop);
        order.addComponent(mouse);
        order.addComponent(new ShippingInfo(ShippingMethod.STANDARD, "123 Main", "City", "12345"));
        
        OrderValidationVisitor validator = new OrderValidationVisitor();
        PriceCalculatorVisitor calculator = new PriceCalculatorVisitor();
        OrderAnalyticsVisitor analytics = new OrderAnalyticsVisitor();
        
        order.accept(validator);
        order.accept(calculator);
        order.accept(analytics);
        
        assertThat(validator.isValid()).isTrue();
        assertThat(calculator.getSubtotal()).isGreaterThan(BigDecimal.ZERO);
        assertThat(analytics.getTotalItemCount()).isEqualTo(3);
    }
    
    @Test
    @DisplayName("Should verify line item total calculation")
    void testLineItemTotal() {
        LineItem item = new LineItem("P1", "Product", 5, new BigDecimal("10.50"));
        
        assertThat(item.getLineTotal()).isEqualByComparingTo(new BigDecimal("52.50"));
    }
    
    @Test
    @DisplayName("Should handle order with no discount")
    void testNoDiscount() {
        order.addComponent(laptop);
        
        PriceCalculatorVisitor calculator = new PriceCalculatorVisitor();
        order.accept(calculator);
        
        assertThat(calculator.getDiscountAmount()).isEqualByComparingTo(BigDecimal.ZERO);
    }
    
    @Test
    @DisplayName("Should handle order with no shipping")
    void testNoShipping() {
        order.addComponent(laptop);
        
        PriceCalculatorVisitor calculator = new PriceCalculatorVisitor();
        order.accept(calculator);
        
        assertThat(calculator.getShippingCost()).isEqualByComparingTo(BigDecimal.ZERO);
    }
    
    @Test
    @DisplayName("Should verify enum values")
    void testEnumValues() {
        assertThat(DiscountType.values()).contains(DiscountType.PERCENTAGE, DiscountType.FIXED_AMOUNT);
        assertThat(ShippingMethod.values()).contains(
            ShippingMethod.STANDARD, 
            ShippingMethod.EXPRESS, 
            ShippingMethod.OVERNIGHT
        );
    }
}
