package com.patterns.state.simulation;

import java.math.BigDecimal;

/**
 * State interface for order fulfillment workflow.
 * Defines all possible operations on an order.
 */
public interface OrderState {
    
    /**
     * Process payment for the order.
     *
     * @param order  the order being paid
     * @param amount payment amount
     * @throws IllegalStateException if payment not allowed in this state
     * @throws IllegalArgumentException if amount is invalid
     */
    void pay(Order order, BigDecimal amount);
    
    /**
     * Ship the order.
     *
     * @param order          the order being shipped
     * @param trackingNumber shipping tracking number
     * @throws IllegalStateException if shipping not allowed in this state
     * @throws IllegalArgumentException if tracking number is invalid
     */
    void ship(Order order, String trackingNumber);
    
    /**
     * Mark order as delivered.
     *
     * @param order the order being delivered
     * @throws IllegalStateException if delivery not allowed in this state
     */
    void deliver(Order order);
    
    /**
     * Cancel the order.
     *
     * @param order  the order being cancelled
     * @param reason cancellation reason
     * @throws IllegalStateException if cancellation not allowed in this state
     * @throws IllegalArgumentException if reason is invalid
     */
    void cancel(Order order, String reason);
    
    /**
     * Process refund for the order.
     *
     * @param order the order being refunded
     * @throws IllegalStateException if refund not allowed in this state
     */
    void refund(Order order);
    
    /**
     * Returns the name of this state.
     *
     * @return state name
     */
    String getStateName();
    
    /**
     * Checks if the order can be cancelled in this state.
     *
     * @return true if cancellation is allowed
     */
    boolean canCancel();
    
    /**
     * Checks if the order can be shipped in this state.
     *
     * @return true if shipping is allowed
     */
    boolean canShip();
}
