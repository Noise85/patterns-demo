package com.patterns.state.simulation;

import java.math.BigDecimal;

/**
 * Initial state when order is created.
 * Awaiting payment or can be cancelled.
 */
public class PendingState implements OrderState {
    
    @Override
    public void pay(Order order, BigDecimal amount) {
        // TODO: Validate amount (must be positive)
        // Store payment amount in order
        // Transition to PaidState
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void ship(Order order, String trackingNumber) {
        // TODO: Throw IllegalStateException - cannot ship unpaid order
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void deliver(Order order) {
        // TODO: Throw IllegalStateException - cannot deliver unpaid order
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void cancel(Order order, String reason) {
        // TODO: Validate reason (not null/empty)
        // Store cancellation reason in order
        // Transition to CancelledState
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void refund(Order order) {
        // TODO: Throw IllegalStateException - cannot refund unpaid order
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getStateName() {
        return "Pending";
    }
    
    @Override
    public boolean canCancel() {
        return true;
    }
    
    @Override
    public boolean canShip() {
        return false;
    }
}
