package com.patterns.visitor.simulation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Visitor that validates all components in an order.
 */
public class OrderValidationVisitor implements OrderVisitor {
    
    private final List<String> errors;
    private int lineItemCount;
    private BigDecimal currentSubtotal;
    
    public OrderValidationVisitor() {
        this.errors = new ArrayList<>();
        this.lineItemCount = 0;
        this.currentSubtotal = BigDecimal.ZERO;
    }
    
    @Override
    public void visitOrder(Order order) {
        // TODO: Reset state for validating a new order
        // Clear errors, reset counters
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void visitLineItem(LineItem item) {
        // TODO: Validate line item
        // 1. Check quantity > 0
        // 2. Increment line item count
        // 3. Add to current subtotal (for discount validation)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void visitDiscountCoupon(DiscountCoupon coupon) {
        // TODO: Validate discount coupon
        // Check if current subtotal meets minimum order amount
        // Add error if not met
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void visitShippingInfo(ShippingInfo shipping) {
        // TODO: Validate shipping information
        // Check that address, city, and postal code are not empty
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void visitGiftWrap(GiftWrap giftWrap) {
        // TODO: Validate gift wrap (if needed)
        // Gift wrap validation is optional for this exercise
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Checks if the order is valid.
     *
     * @return true if no errors, false otherwise
     */
    public boolean isValid() {
        // TODO: Check if there are any errors
        // Also verify at least one line item exists
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Returns the list of validation errors.
     *
     * @return list of error messages
     */
    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }
}
