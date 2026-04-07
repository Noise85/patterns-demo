package com.patterns.visitor.simulation;

import java.math.BigDecimal;

/**
 * Represents a discount coupon applied to an order.
 */
public class DiscountCoupon implements OrderComponent {
    
    private final String couponCode;
    private final DiscountType discountType;
    private final BigDecimal discountValue;
    private final BigDecimal minimumOrderAmount;
    
    /**
     * Creates a new discount coupon.
     *
     * @param couponCode         coupon code (e.g., "SAVE20")
     * @param discountType       type of discount (percentage or fixed amount)
     * @param discountValue      discount value (20 for 20%, or actual amount)
     * @param minimumOrderAmount minimum order amount to apply discount
     */
    public DiscountCoupon(String couponCode, DiscountType discountType, 
                         BigDecimal discountValue, BigDecimal minimumOrderAmount) {
        this.couponCode = couponCode;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minimumOrderAmount = minimumOrderAmount;
    }
    
    @Override
    public void accept(OrderVisitor visitor) {
        // TODO: Call the appropriate visitor method
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public String getCouponCode() {
        return couponCode;
    }
    
    public DiscountType getDiscountType() {
        return discountType;
    }
    
    public BigDecimal getDiscountValue() {
        return discountValue;
    }
    
    public BigDecimal getMinimumOrderAmount() {
        return minimumOrderAmount;
    }
}
