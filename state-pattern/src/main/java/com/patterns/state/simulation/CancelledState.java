package com.patterns.state.simulation;

import java.math.BigDecimal;

/**
 * Terminal state when order is cancelled.
 * No further operations allowed.
 */
public class CancelledState implements OrderState {
    
    @Override
    public void pay(Order order, BigDecimal amount) {
        // TODO: Throw IllegalStateException - order is cancelled
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void ship(Order order, String trackingNumber) {
        // TODO: Throw IllegalStateException - order is cancelled
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void deliver(Order order) {
        // TODO: Throw IllegalStateException - order is cancelled
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void cancel(Order order, String reason) {
        // TODO: Throw IllegalStateException - already cancelled
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void refund(Order order) {
        // TODO: Throw IllegalStateException - order is cancelled (use refund for delivered orders)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getStateName() {
        return "Cancelled";
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
