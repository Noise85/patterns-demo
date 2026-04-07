package com.patterns.strategy.simulation.model;

import java.time.LocalDate;

/**
 * Context object containing all information needed for pricing decisions.
 * 
 * This object is passed to pricing strategies so they can make decisions.
 */
public class PricingContext {
    private final Order order;
    private final boolean isPromotionalPeriod;
    
    public PricingContext(Order order, boolean isPromotionalPeriod) {
        this.order = order;
        this.isPromotionalPeriod = isPromotionalPeriod;
    }
    
    public Order getOrder() {
        return order;
    }
    
    public boolean isPromotionalPeriod() {
        return isPromotionalPeriod;
    }
    
    // Convenience methods
    public double getOrderTotal() {
        return order.getTotal();
    }
    
    public int getItemCount() {
        return order.getItemCount();
    }
    
    public String getCustomerType() {
        return order.getCustomerType();
    }
    
    public boolean isFirstTimeBuyer() {
        return order.isFirstTimeBuyer();
    }
    
    public LocalDate getOrderDate() {
        return order.getOrderDate();
    }
}
