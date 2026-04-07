package com.patterns.visitor.simulation;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Visitor that collects analytics about an order.
 */
public class OrderAnalyticsVisitor implements OrderVisitor {
    
    private int totalItemCount;
    private final Set<String> uniqueProducts;
    private BigDecimal mostExpensiveItemPrice;
    private boolean hasDiscount;
    private boolean hasGiftWrap;
    
    public OrderAnalyticsVisitor() {
        this.totalItemCount = 0;
        this.uniqueProducts = new HashSet<>();
        this.mostExpensiveItemPrice = BigDecimal.ZERO;
        this.hasDiscount = false;
        this.hasGiftWrap = false;
    }
    
    @Override
    public void visitOrder(Order order) {
        // TODO: Reset state for analyzing a new order
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void visitLineItem(LineItem item) {
        // TODO: Collect line item analytics
        // 1. Add quantity to total item count
        // 2. Add product ID to unique products set
        // 3. Update most expensive item if this item's unit price is higher
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void visitDiscountCoupon(DiscountCoupon coupon) {
        // TODO: Mark that order has discount
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void visitShippingInfo(ShippingInfo shipping) {
        // TODO: Nothing to track for shipping in basic analytics
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void visitGiftWrap(GiftWrap giftWrap) {
        // TODO: Mark that order has gift wrap
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public int getTotalItemCount() {
        return totalItemCount;
    }
    
    public int getUniqueProductCount() {
        return uniqueProducts.size();
    }
    
    public BigDecimal getMostExpensiveItemPrice() {
        return mostExpensiveItemPrice;
    }
    
    public boolean hasDiscount() {
        return hasDiscount;
    }
    
    public boolean hasGiftWrap() {
        return hasGiftWrap;
    }
}
