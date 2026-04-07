package com.patterns.facade.simulation;

import java.util.List;

/**
 * Order request containing all information needed to process an order.
 */
public record OrderRequest(
    String orderId,
    String customerId,
    List<OrderItem> items,
    Address shippingAddress,
    PaymentMethod paymentMethod
) {
    public OrderRequest {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID cannot be blank");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one item");
        }
        items = List.copyOf(items); // defensive copy for immutability
    }
    
    public long getTotalAmount() {
        return items.stream()
            .mapToLong(OrderItem::getTotalPrice)
            .sum();
    }
}
