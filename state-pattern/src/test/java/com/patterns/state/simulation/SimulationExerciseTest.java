package com.patterns.state.simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for State Pattern - Simulation Exercise (Order Fulfillment).
 */
@DisplayName("State Pattern - Order Fulfillment System")
class SimulationExerciseTest {
    
    private Order order;
    
    @BeforeEach
    void setUp() {
        order = new Order("ORD-12345", "123 Main St, Springfield, 12345");
    }
    
    @Test
    @DisplayName("Should start in Pending state")
    void testInitialState() {
        assertThat(order.getCurrentStateName()).isEqualTo("Pending");
    }
    
    @Test
    @DisplayName("Should record initial state in history")
    void testInitialStateHistory() {
        List<StateTransition> history = order.getStateHistory();
        
        assertThat(history).hasSize(1);
        assertThat(history.get(0).stateName()).isEqualTo("Pending");
    }
    
    @Test
    @DisplayName("Should allow payment in Pending state")
    void testPaymentInPending() {
        order.pay(new BigDecimal("99.99"));
        
        assertThat(order.getCurrentStateName()).isEqualTo("Paid");
        assertThat(order.getPaymentAmount()).isEqualByComparingTo(new BigDecimal("99.99"));
    }
    
    @Test
    @DisplayName("Should reject negative payment amount")
    void testNegativePayment() {
        assertThatThrownBy(() -> order.pay(new BigDecimal("-10.00")))
            .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    @DisplayName("Should reject zero payment amount")
    void testZeroPayment() {
        assertThatThrownBy(() -> order.pay(BigDecimal.ZERO))
            .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    @DisplayName("Should not allow shipping unpaid order")
    void testCannotShipUnpaidOrder() {
        assertThatThrownBy(() -> order.ship("TRACK-123"))
            .isInstanceOf(IllegalStateException.class);
    }
    
    @Test
    @DisplayName("Should allow cancellation from Pending state")
    void testCancelPending() {
        order.cancel("Customer changed mind");
        
        assertThat(order.getCurrentStateName()).isEqualTo("Cancelled");
        assertThat(order.getCancellationReason()).isEqualTo("Customer changed mind");
    }
    
    @Test
    @DisplayName("Should reject cancellation without reason")
    void testCancelWithoutReason() {
        assertThatThrownBy(() -> order.cancel(""))
            .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    @DisplayName("Should allow shipping after payment")
    void testShipAfterPayment() {
        order.pay(new BigDecimal("99.99"));
        order.ship("TRACK-ABC123");
        
        assertThat(order.getCurrentStateName()).isEqualTo("Shipped");
        assertThat(order.getTrackingNumber()).isEqualTo("TRACK-ABC123");
    }
    
    @Test
    @DisplayName("Should reject shipping without tracking number")
    void testShipWithoutTracking() {
        order.pay(new BigDecimal("99.99"));
        
        assertThatThrownBy(() -> order.ship(""))
            .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    @DisplayName("Should allow cancellation from Paid state")
    void testCancelPaid() {
        order.pay(new BigDecimal("50.00"));
        order.cancel("Out of stock");
        
        assertThat(order.getCurrentStateName()).isEqualTo("Cancelled");
        assertThat(order.getCancellationReason()).isEqualTo("Out of stock");
    }
    
    @Test
    @DisplayName("Should not allow cancellation after shipping")
    void testCannotCancelShipped() {
        order.pay(new BigDecimal("75.00"));
        order.ship("TRACK-XYZ");
        
        assertThatThrownBy(() -> order.cancel("Too late"))
            .isInstanceOf(IllegalStateException.class);
    }
    
    @Test
    @DisplayName("Should allow delivery after shipping")
    void testDeliverAfterShipping() {
        order.pay(new BigDecimal("99.99"));
        order.ship("TRACK-123");
        order.deliver();
        
        assertThat(order.getCurrentStateName()).isEqualTo("Delivered");
    }
    
    @Test
    @DisplayName("Should not allow delivery before shipping")
    void testCannotDeliverBeforeShipping() {
        order.pay(new BigDecimal("99.99"));
        
        assertThatThrownBy(() -> order.deliver())
            .isInstanceOf(IllegalStateException.class);
    }
    
    @Test
    @DisplayName("Should allow refund after delivery")
    void testRefundAfterDelivery() {
        order.pay(new BigDecimal("99.99"));
        order.ship("TRACK-123");
        order.deliver();
        order.refund();
        
        assertThat(order.getCurrentStateName()).isEqualTo("Refunded");
    }
    
    @Test
    @DisplayName("Should not allow refund before delivery")
    void testCannotRefundBeforeDelivery() {
        order.pay(new BigDecimal("99.99"));
        
        assertThatThrownBy(() -> order.refund())
            .isInstanceOf(IllegalStateException.class);
    }
    
    @Test
    @DisplayName("Should track state history")
    void testStateHistory() {
        order.pay(new BigDecimal("99.99"));
        order.ship("TRACK-123");
        order.deliver();
        
        List<StateTransition> history = order.getStateHistory();
        
        assertThat(history).hasSize(4);
        assertThat(history.get(0).stateName()).isEqualTo("Pending");
        assertThat(history.get(1).stateName()).isEqualTo("Paid");
        assertThat(history.get(2).stateName()).isEqualTo("Shipped");
        assertThat(history.get(3).stateName()).isEqualTo("Delivered");
    }
    
    @Test
    @DisplayName("Should record timestamps in history")
    void testHistoryTimestamps() {
        LocalDateTime before = LocalDateTime.now();
        
        order.pay(new BigDecimal("99.99"));
        
        LocalDateTime after = LocalDateTime.now();
        
        List<StateTransition> history = order.getStateHistory();
        LocalDateTime paidTimestamp = history.get(1).timestamp();
        
        assertThat(paidTimestamp).isAfterOrEqualTo(before);
        assertThat(paidTimestamp).isBeforeOrEqualTo(after);
    }
    
    @Test
    @DisplayName("Should have chronological timestamps")
    void testChronologicalTimestamps() throws InterruptedException {
        order.pay(new BigDecimal("99.99"));
        Thread.sleep(10);
        order.ship("TRACK-123");
        
        List<StateTransition> history = order.getStateHistory();
        LocalDateTime paidAt = history.get(1).timestamp();
        LocalDateTime shippedAt = history.get(2).timestamp();
        
        assertThat(shippedAt).isAfter(paidAt);
    }
    
    @Test
    @DisplayName("Should get current state entry timestamp")
    void testCurrentStateTimestamp() {
        LocalDateTime before = LocalDateTime.now();
        order.pay(new BigDecimal("99.99"));
        LocalDateTime after = LocalDateTime.now();
        
        LocalDateTime enteredAt = order.getEnteredCurrentStateAt();
        
        assertThat(enteredAt).isAfterOrEqualTo(before);
        assertThat(enteredAt).isBeforeOrEqualTo(after);
    }
    
    @Test
    @DisplayName("Should indicate cancel capability")
    void testCanCancelCapability() {
        assertThat(order.canCancel()).isTrue();
        
        order.pay(new BigDecimal("99.99"));
        assertThat(order.canCancel()).isTrue();
        
        order.ship("TRACK-123");
        assertThat(order.canCancel()).isFalse();
    }
    
    @Test
    @DisplayName("Should indicate ship capability")
    void testCanShipCapability() {
        assertThat(order.canShip()).isFalse();
        
        order.pay(new BigDecimal("99.99"));
        assertThat(order.canShip()).isTrue();
        
        order.ship("TRACK-123");
        assertThat(order.canShip()).isFalse();
    }
    
    @Test
    @DisplayName("Should not allow operations in Cancelled state")
    void testNoOperationsAfterCancellation() {
        order.cancel("Reason");
        
        assertThatThrownBy(() -> order.pay(new BigDecimal("10.00")))
            .isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> order.ship("TRACK"))
            .isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> order.deliver())
            .isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> order.refund())
            .isInstanceOf(IllegalStateException.class);
    }
    
    @Test
    @DisplayName("Should not allow operations in Refunded state")
    void testNoOperationsAfterRefund() {
        order.pay(new BigDecimal("99.99"));
        order.ship("TRACK-123");
        order.deliver();
        order.refund();
        
        assertThatThrownBy(() -> order.pay(new BigDecimal("10.00")))
            .isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> order.ship("TRACK"))
            .isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> order.deliver())
            .isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> order.cancel("Reason"))
            .isInstanceOf(IllegalStateException.class);
    }
    
    @Test
    @DisplayName("Should complete successful order workflow")
    void testSuccessfulWorkflow() {
        order.pay(new BigDecimal("99.99"));
        order.ship("TRACK-ABC");
        order.deliver();
        
        assertThat(order.getCurrentStateName()).isEqualTo("Delivered");
        assertThat(order.getPaymentAmount()).isEqualByComparingTo(new BigDecimal("99.99"));
        assertThat(order.getTrackingNumber()).isEqualTo("TRACK-ABC");
    }
    
    @Test
    @DisplayName("Should complete cancellation workflow")
    void testCancellationWorkflow() {
        order.pay(new BigDecimal("50.00"));
        order.cancel("Customer request");
        
        assertThat(order.getCurrentStateName()).isEqualTo("Cancelled");
        assertThat(order.getCancellationReason()).isEqualTo("Customer request");
        assertThat(order.getPaymentAmount()).isEqualByComparingTo(new BigDecimal("50.00"));
    }
    
    @Test
    @DisplayName("Should complete refund workflow")
    void testRefundWorkflow() {
        order.pay(new BigDecimal("149.99"));
        order.ship("TRACK-XYZ");
        order.deliver();
        order.refund();
        
        assertThat(order.getCurrentStateName()).isEqualTo("Refunded");
        assertThat(order.getPaymentAmount()).isEqualByComparingTo(new BigDecimal("149.99"));
    }
    
    @Test
    @DisplayName("Should verify order properties")
    void testOrderProperties() {
        assertThat(order.getOrderId()).isEqualTo("ORD-12345");
        assertThat(order.getShippingAddress()).isEqualTo("123 Main St, Springfield, 12345");
    }
    
    @Test
    @DisplayName("Should return defensive copy of state history")
    void testHistoryDefensiveCopy() {
        order.pay(new BigDecimal("99.99"));
        
        List<StateTransition> history1 = order.getStateHistory();
        int originalSize = history1.size();
        
        history1.clear();
        
        List<StateTransition> history2 = order.getStateHistory();
        assertThat(history2).hasSize(originalSize);
    }
}
