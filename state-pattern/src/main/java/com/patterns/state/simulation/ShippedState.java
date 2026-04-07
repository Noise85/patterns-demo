package com.patterns.state.simulation;

import java.math.BigDecimal;

/**
 * State when order is in transit.
 * Cannot be cancelled, awaiting delivery.
 */
public class ShippedState implements OrderState {
    
    @Override
    public void pay(Order order, BigDecimal amount) {
        // TODO: Throw IllegalStateException - already paid
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void ship(Order order, String trackingNumber) {
        // TODO: Throw IllegalStateException - already shipped
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void deliver(Order order) {
        // TODO: Transition to DeliveredState
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void cancel(Order order, String reason) {
        // TODO: Throw IllegalStateException - cannot cancel shipped order
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void refund(Order order) {
        // TODO: Throw IllegalStateException - cannot refund before delivery
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getStateName() {
        return "Shipped";
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
