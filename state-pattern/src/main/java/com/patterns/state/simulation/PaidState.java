package com.patterns.state.simulation;

import java.math.BigDecimal;

/**
 * State when payment has been received.
 * Order can be shipped or cancelled.
 */
public class PaidState implements OrderState {
    
    @Override
    public void pay(Order order, BigDecimal amount) {
        // TODO: Throw IllegalStateException - already paid
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void ship(Order order, String trackingNumber) {
        // TODO: Validate tracking number (not null/empty)
        // Store tracking number in order
        // Transition to ShippedState
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void deliver(Order order) {
        // TODO: Throw IllegalStateException - must ship before delivery
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
        // TODO: Throw IllegalStateException - cannot refund before delivery
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getStateName() {
        return "Paid";
    }
    
    @Override
    public boolean canCancel() {
        return true;
    }
    
    @Override
    public boolean canShip() {
        return true;
    }
}
