package com.patterns.state.simulation;

import java.math.BigDecimal;

/**
 * State when order has been successfully delivered.
 * Can be refunded if needed.
 */
public class DeliveredState implements OrderState {
    
    @Override
    public void pay(Order order, BigDecimal amount) {
        // TODO: Throw IllegalStateException - already paid
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void ship(Order order, String trackingNumber) {
        // TODO: Throw IllegalStateException - already shipped and delivered
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void deliver(Order order) {
        // TODO: Throw IllegalStateException - already delivered
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void cancel(Order order, String reason) {
        // TODO: Throw IllegalStateException - cannot cancel delivered order
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void refund(Order order) {
        // TODO: Transition to RefundedState
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getStateName() {
        return "Delivered";
    }
    
    @Override
    public boolean canCancel() {
        return false;
    }
    
    @Override
    public boolean canShip() {
        return false;
    }
}
