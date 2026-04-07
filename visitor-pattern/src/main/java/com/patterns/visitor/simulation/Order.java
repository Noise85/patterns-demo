package com.patterns.visitor.simulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer order containing various components.
 * This is the composite in the Composite pattern and the element in the Visitor pattern.
 */
public class Order implements OrderComponent {
    
    private final String orderId;
    private final String customerId;
    private final List<OrderComponent> components;
    
    /**
     * Creates a new order.
     *
     * @param orderId    unique order identifier
     * @param customerId customer identifier
     */
    public Order(String orderId, String customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.components = new ArrayList<>();
    }
    
    /**
     * Adds a component to this order.
     *
     * @param component the component to add (line item, discount, etc.)
     */
    public void addComponent(OrderComponent component) {
        // TODO: Add component to the components list
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void accept(OrderVisitor visitor) {
        // TODO: Implement the accept method
        // 1. Let the visitor visit this order first
        // 2. Then let the visitor visit all components
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public List<OrderComponent> getComponents() {
        return new ArrayList<>(components);  // Defensive copy
    }
    
    public int getComponentCount() {
        return components.size();
    }
}
