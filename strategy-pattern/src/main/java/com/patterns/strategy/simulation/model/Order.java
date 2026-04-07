package com.patterns.strategy.simulation.model;

import java.time.LocalDate;

/**
 * Represents an order in the e-commerce system.
 * 
 * This is a simple data holder (could use Java record in real implementation).
 */
public class Order {
    private final double total;
    private final int itemCount;
    private final LocalDate orderDate;
    private final String customerType; // "REGULAR", "VIP", "CORPORATE"
    private final boolean isFirstTimeBuyer;
    
    public Order(double total, int itemCount, LocalDate orderDate, 
                 String customerType, boolean isFirstTimeBuyer) {
        this.total = total;
        this.itemCount = itemCount;
        this.orderDate = orderDate;
        this.customerType = customerType;
        this.isFirstTimeBuyer = isFirstTimeBuyer;
    }
    
    public double getTotal() {
        return total;
    }
    
    public int getItemCount() {
        return itemCount;
    }
    
    public LocalDate getOrderDate() {
        return orderDate;
    }
    
    public String getCustomerType() {
        return customerType;
    }
    
    public boolean isFirstTimeBuyer() {
        return isFirstTimeBuyer;
    }
}
