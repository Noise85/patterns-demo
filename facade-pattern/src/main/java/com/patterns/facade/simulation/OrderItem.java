package com.patterns.facade.simulation;

/**
 * Represents an item in an order.
 */
public record OrderItem(
    String productId,
    int quantity,
    long unitPrice  // price in cents
) {
    public OrderItem {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (unitPrice < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative");
        }
    }
    
    public long getTotalPrice() {
        return unitPrice * quantity;
    }
}
