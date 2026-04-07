package com.patterns.state.simulation;

import java.math.BigDecimal;

/**
 * Terminal state when order is refunded.
 * No further operations allowed.
 */
public class RefundedState implements OrderState {
    
    @Override
    public void pay(Order order, BigDecimal amount) {
        // TODO: Throw IllegalStateException - order is refunded
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void ship(Order order, String trackingNumber) {
        // TODO: Throw IllegalStateException - order is refunded
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void deliver(Order order) {
        // TODO: Throw IllegalStateException - order is refunded
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void cancel(Order order, String reason) {
        // TODO: Throw IllegalStateException - order is refunded
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void refund(Order order) {
        // TODO: Throw IllegalStateException - already refunded
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getStateName() {
        return "Refunded";
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
