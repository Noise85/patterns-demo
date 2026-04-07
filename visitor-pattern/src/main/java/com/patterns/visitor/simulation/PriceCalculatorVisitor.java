package com.patterns.visitor.simulation;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Visitor that calculates pricing for an order.
 */
public class PriceCalculatorVisitor implements OrderVisitor {
    
    private static final BigDecimal TAX_RATE = new BigDecimal("0.08");
    private static final BigDecimal STANDARD_SHIPPING = new BigDecimal("5.00");
    private static final BigDecimal EXPRESS_SHIPPING = new BigDecimal("12.00");
    private static final BigDecimal OVERNIGHT_SHIPPING = new BigDecimal("25.00");
    
    private BigDecimal subtotal;
    private BigDecimal discountAmount;
    private BigDecimal shippingCost;
    private BigDecimal giftWrapCost;
    
    public PriceCalculatorVisitor() {
        this.subtotal = BigDecimal.ZERO;
        this.discountAmount = BigDecimal.ZERO;
        this.shippingCost = BigDecimal.ZERO;
        this.giftWrapCost = BigDecimal.ZERO;
    }
    
    @Override
    public void visitOrder(Order order) {
        // TODO: Reset state for calculating a new order
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void visitLineItem(LineItem item) {
        // TODO: Calculate line item total and add to subtotal
        // Line total = quantity × unit price
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void visitDiscountCoupon(DiscountCoupon coupon) {
        // TODO: Calculate discount amount based on type
        // PERCENTAGE: subtotal × (discountValue / 100)
        // FIXED_AMOUNT: min(discountValue, subtotal)
        // Remember to use RoundingMode.HALF_UP for divisions!
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void visitShippingInfo(ShippingInfo shipping) {
        // TODO: Determine shipping cost based on method
        // STANDARD: $5.00
        // EXPRESS: $12.00
        // OVERNIGHT: $25.00
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void visitGiftWrap(GiftWrap giftWrap) {
        // TODO: Add gift wrap cost
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public BigDecimal getSubtotal() {
        return subtotal;
    }
    
    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }
    
    public BigDecimal getShippingCost() {
        return shippingCost;
    }
    
    public BigDecimal getGiftWrapCost() {
        return giftWrapCost;
    }
    
    /**
     * Calculates tax on the discounted subtotal.
     * Tax rate: 8%
     *
     * @return tax amount
     */
    public BigDecimal getTaxAmount() {
        // TODO: Calculate tax
        // Tax applies to (subtotal - discount)
        // Use TAX_RATE constant
        // Set scale to 2 with HALF_UP rounding
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Calculates the grand total.
     *
     * @return grand total (subtotal - discount + tax + shipping + gift wrap)
     */
    public BigDecimal getGrandTotal() {
        // TODO: Calculate grand total
        // subtotal - discount + tax + shipping + giftWrap
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
