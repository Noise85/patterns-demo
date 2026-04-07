package com.patterns.state.simulation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Context class representing an order in the fulfillment system.
 * Delegates all operations to the current state and maintains order data.
 */
public class Order {
    
    private final String orderId;
    private final String shippingAddress;
    private OrderState currentState;
    private BigDecimal paymentAmount;
    private String trackingNumber;
    private String cancellationReason;
    private final List<StateTransition> stateHistory;
    
    /**
     * Creates a new order in Pending state.
     *
     * @param orderId         unique order identifier
     * @param shippingAddress delivery address
     */
    public Order(String orderId, String shippingAddress) {
        this.orderId = orderId;
        this.shippingAddress = shippingAddress;
        this.currentState = new PendingState();
        this.stateHistory = new ArrayList<>();
        addToHistory(currentState.getStateName());
    }
    
    /**
     * Processes payment (delegates to current state).
     *
     * @param amount payment amount
     * @throws IllegalStateException if payment not allowed in current state
     */
    public void pay(BigDecimal amount) {
        // TODO: Delegate to currentState.pay(this, amount)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Ships the order (delegates to current state).
     *
     * @param trackingNumber shipping tracking number
     * @throws IllegalStateException if shipping not allowed in current state
     */
    public void ship(String trackingNumber) {
        // TODO: Delegate to currentState.ship(this, trackingNumber)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Marks order as delivered (delegates to current state).
     *
     * @throws IllegalStateException if delivery not allowed in current state
     */
    public void deliver() {
        // TODO: Delegate to currentState.deliver(this)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Cancels the order (delegates to current state).
     *
     * @param reason cancellation reason
     * @throws IllegalStateException if cancellation not allowed in current state
     */
    public void cancel(String reason) {
        // TODO: Delegate to currentState.cancel(this, reason)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Processes refund (delegates to current state).
     *
     * @throws IllegalStateException if refund not allowed in current state
     */
    public void refund() {
        // TODO: Delegate to currentState.refund(this)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Changes the current state and records in history.
     * Called by state objects to transition to new states.
     *
     * @param newState the new state
     */
    public void setState(OrderState newState) {
        // TODO: 
        // 1. Set currentState to newState
        // 2. Add to state history: addToHistory(newState.getStateName())
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Adds a state transition to history.
     *
     * @param stateName name of the state
     */
    private void addToHistory(String stateName) {
        // TODO: Add new StateTransition to stateHistory
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    // Getters and setters for state management
    
    public String getOrderId() {
        return orderId;
    }
    
    public String getShippingAddress() {
        return shippingAddress;
    }
    
    public String getCurrentStateName() {
        // TODO: Return currentState.getStateName()
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }
    
    public void setPaymentAmount(BigDecimal paymentAmount) {
        // TODO: Set paymentAmount
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public String getTrackingNumber() {
        return trackingNumber;
    }
    
    public void setTrackingNumber(String trackingNumber) {
        // TODO: Set trackingNumber
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public String getCancellationReason() {
        return cancellationReason;
    }
    
    public void setCancellationReason(String cancellationReason) {
        // TODO: Set cancellationReason
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public List<StateTransition> getStateHistory() {
        // TODO: Return defensive copy of stateHistory
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public LocalDateTime getEnteredCurrentStateAt() {
        // TODO: Return timestamp of most recent transition from stateHistory
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public boolean canCancel() {
        // TODO: Return currentState.canCancel()
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public boolean canShip() {
        // TODO: Return currentState.canShip()
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
