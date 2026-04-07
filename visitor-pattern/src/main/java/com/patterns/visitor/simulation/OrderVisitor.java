package com.patterns.visitor.simulation;

/**
 * Visitor interface for order processing operations.
 */
public interface OrderVisitor {
    
    /**
     * Visits an order.
     *
     * @param order the order to visit
     */
    void visitOrder(Order order);
    
    /**
     * Visits a line item.
     *
     * @param item the line item to visit
     */
    void visitLineItem(LineItem item);
    
    /**
     * Visits a discount coupon.
     *
     * @param coupon the discount coupon to visit
     */
    void visitDiscountCoupon(DiscountCoupon coupon);
    
    /**
     * Visits shipping information.
     *
     * @param shipping the shipping info to visit
     */
    void visitShippingInfo(ShippingInfo shipping);
    
    /**
     * Visits gift wrap information.
     *
     * @param giftWrap the gift wrap to visit
     */
    void visitGiftWrap(GiftWrap giftWrap);
}
