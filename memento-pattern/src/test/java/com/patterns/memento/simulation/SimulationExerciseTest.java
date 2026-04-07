package com.patterns.memento.simulation;

import com.patterns.memento.simulation.Order.Snapshot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Memento Pattern - Simulation Exercise (Order Management).
 */
@DisplayName("Memento Pattern - Order Management with Snapshots")
class SimulationExerciseTest {
    
    private Order order;
    private TransactionManager txManager;
    
    @BeforeEach
    void setUp() {
        order = new Order("ORD-001", "AAPL", 100, new BigDecimal("150.00"));
        txManager = new TransactionManager();
    }
    
    // Order State Management Tests
    
    @Test
    @DisplayName("Should create order with initial state")
    void testOrderCreation() {
        assertThat(order.getOrderId()).isEqualTo("ORD-001");
        assertThat(order.getSymbol()).isEqualTo("AAPL");
        assertThat(order.getQuantity()).isEqualTo(100);
        assertThat(order.getPrice()).isEqualByComparingTo(new BigDecimal("150.00"));
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.SUBMITTED);
        assertThat(order.getFilledQuantity()).isZero();
        assertThat(order.getAverageFillPrice()).isEqualByComparingTo(BigDecimal.ZERO);
    }
    
    @Test
    @DisplayName("Should submit order")
    void testSubmit() {
        order.submit();
        
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.SUBMITTED);
    }
    
    @Test
    @DisplayName("Should mark order as risk checked")
    void testMarkRiskChecked() {
        order.markRiskChecked();
        
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.RISK_CHECKED);
    }
    
    @Test
    @DisplayName("Should mark order as pending execution")
    void testMarkPendingExecution() {
        order.markPendingExecution();
        
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PENDING_EXECUTION);
    }
    
    @Test
    @DisplayName("Should complete execution")
    void testCompleteExecution() {
        order.completeExecution();
        
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.EXECUTED);
    }
    
    @Test
    @DisplayName("Should reject order")
    void testReject() {
        order.reject("Risk limit exceeded");
        
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.REJECTED);
    }
    
    @Test
    @DisplayName("Should cancel order")
    void testCancel() {
        order.cancel();
        
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCELLED);
    }
    
    // Partial Fill Tests
    
    @Test
    @DisplayName("Should record single partial fill")
    void testSinglePartialFill() {
        order.fillPartial(50, new BigDecimal("150.25"));
        
        assertThat(order.getFilledQuantity()).isEqualTo(50);
        assertThat(order.getAverageFillPrice()).isEqualByComparingTo(new BigDecimal("150.25"));
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PARTIALLY_FILLED);
    }
    
    @Test
    @DisplayName("Should calculate average fill price for multiple fills")
    void testMultiplePartialFills() {
        order.fillPartial(50, new BigDecimal("150.00")); // 50 @ $150.00
        order.fillPartial(30, new BigDecimal("151.00")); // 30 @ $151.00
        
        // Average: (50*150 + 30*151) / 80 = 150.375
        assertThat(order.getFilledQuantity()).isEqualTo(80);
        assertThat(order.getAverageFillPrice())
            .isEqualByComparingTo(new BigDecimal("150.38")); // Rounded to 2 decimals
    }
    
    @Test
    @DisplayName("Should mark as executed when fully filled")
    void testFullFill() {
        order.fillPartial(100, new BigDecimal("150.00"));
        
        assertThat(order.getFilledQuantity()).isEqualTo(100);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.EXECUTED);
    }
    
    @Test
    @DisplayName("Should calculate average fill price with three fills")
    void testThreePartialFills() {
        order.fillPartial(50, new BigDecimal("150.00")); // 50 @ $150.00
        order.fillPartial(30, new BigDecimal("151.00")); // 30 @ $151.00
        order.fillPartial(20, new BigDecimal("149.00")); // 20 @ $149.00
        
        // Average: (50*150 + 30*151 + 20*149) / 100 = 150.26
        assertThat(order.getFilledQuantity()).isEqualTo(100);
        assertThat(order.getAverageFillPrice())
            .isEqualByComparingTo(new BigDecimal("150.26"));
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.EXECUTED);
    }
    
    // Snapshot Tests
    
    @Test
    @DisplayName("Should create snapshot of order state")
    void testCreateSnapshot() {
        order.markRiskChecked();
        Snapshot snapshot = order.snapshot();
        
        assertThat(snapshot).isNotNull();
    }
    
    @Test
    @DisplayName("Should restore order status from snapshot")
    void testRestoreStatus() {
        order.markRiskChecked();
        Snapshot snapshot = order.snapshot();
        
        order.markPendingExecution();
        order.restore(snapshot);
        
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.RISK_CHECKED);
    }
    
    @Test
    @DisplayName("Should restore filled quantity from snapshot")
    void testRestoreFilledQuantity() {
        order.fillPartial(50, new BigDecimal("150.00"));
        Snapshot snapshot = order.snapshot();
        
        order.fillPartial(30, new BigDecimal("151.00"));
        order.restore(snapshot);
        
        assertThat(order.getFilledQuantity()).isEqualTo(50);
    }
    
    @Test
    @DisplayName("Should restore average fill price from snapshot")
    void testRestoreAverageFillPrice() {
        order.fillPartial(50, new BigDecimal("150.00"));
        Snapshot snapshot = order.snapshot();
        
        order.fillPartial(50, new BigDecimal("152.00"));
        order.restore(snapshot);
        
        assertThat(order.getAverageFillPrice())
            .isEqualByComparingTo(new BigDecimal("150.00"));
    }
    
    @Test
    @DisplayName("Should not restore immutable fields")
    void testSnapshotDoesNotChangeImmutableFields() {
        Snapshot snapshot = order.snapshot();
        
        // These should never change when restoring
        String originalId = order.getOrderId();
        String originalSymbol = order.getSymbol();
        int originalQuantity = order.getQuantity();
        LocalDateTime originalSubmittedAt = order.getSubmittedAt();
        
        order.markRiskChecked();
        order.restore(snapshot);
        
        assertThat(order.getOrderId()).isEqualTo(originalId);
        assertThat(order.getSymbol()).isEqualTo(originalSymbol);
        assertThat(order.getQuantity()).isEqualTo(originalQuantity);
        assertThat(order.getSubmittedAt()).isEqualTo(originalSubmittedAt);
    }
    
    @Test
    @DisplayName("Should handle market orders with null price")
    void testMarketOrderSnapshot() {
        Order marketOrder = new Order("ORD-002", "GOOGL", 50, null);
        Snapshot snapshot = marketOrder.snapshot();
        
        marketOrder.markRiskChecked();
        marketOrder.restore(snapshot);
        
        assertThat(marketOrder.getPrice()).isNull();
        assertThat(marketOrder.getOrderStatus()).isEqualTo(OrderStatus.SUBMITTED);
    }
    
    // Multiple Snapshots Tests
    
    @Test
    @DisplayName("Should handle multiple independent snapshots")
    void testMultipleSnapshots() {
        order.submit();
        Snapshot s1 = order.snapshot();
        
        order.markRiskChecked();
        Snapshot s2 = order.snapshot();
        
        order.markPendingExecution();
        Snapshot s3 = order.snapshot();
        
        order.restore(s2);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.RISK_CHECKED);
        
        order.restore(s1);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.SUBMITTED);
        
        order.restore(s3);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PENDING_EXECUTION);
    }
    
    @Test
    @DisplayName("Should not affect snapshot when order changes")
    void testSnapshotImmutability() {
        order.fillPartial(50, new BigDecimal("150.00"));
        Snapshot snapshot = order.snapshot();
        
        order.fillPartial(30, new BigDecimal("151.00"));
        order.fillPartial(20, new BigDecimal("149.00"));
        
        // Snapshot should restore to 50 filled
        order.restore(snapshot);
        assertThat(order.getFilledQuantity()).isEqualTo(50);
    }
    
    // Transaction Manager Tests
    
    @Test
    @DisplayName("Should create checkpoint")
    void testCheckpoint() {
        txManager.checkpoint(order);
        
        assertThat(txManager.hasCheckpoints(order.getOrderId())).isTrue();
        assertThat(txManager.getCheckpointCount(order.getOrderId())).isEqualTo(1);
    }
    
    @Test
    @DisplayName("Should rollback to checkpoint")
    void testRollback() {
        order.markRiskChecked();
        txManager.checkpoint(order);
        
        order.markPendingExecution();
        boolean success = txManager.rollback(order);
        
        assertThat(success).isTrue();
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.RISK_CHECKED);
    }
    
    @Test
    @DisplayName("Should return false when rolling back with no checkpoints")
    void testRollbackNoCheckpoints() {
        boolean success = txManager.rollback(order);
        
        assertThat(success).isFalse();
    }
    
    @Test
    @DisplayName("Should handle multiple checkpoints")
    void testMultipleCheckpoints() {
        order.submit();
        txManager.checkpoint(order);
        
        order.markRiskChecked();
        txManager.checkpoint(order);
        
        order.markPendingExecution();
        txManager.checkpoint(order);
        
        assertThat(txManager.getCheckpointCount(order.getOrderId())).isEqualTo(3);
    }
    
    @Test
    @DisplayName("Should rollback multiple steps")
    void testMultiStepRollback() {
        order.submit();
        txManager.checkpoint(order);
        
        order.markRiskChecked();
        txManager.checkpoint(order);
        
        order.markPendingExecution();
        txManager.checkpoint(order);
        
        // Rollback 2 steps
        boolean success = txManager.rollbackTo(order, 2);
        
        assertThat(success).isTrue();
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.RISK_CHECKED);
    }
    
    @Test
    @DisplayName("Should enforce checkpoint limit")
    void testCheckpointLimit() {
        TransactionManager limitedTxManager = new TransactionManager(3);
        
        for (int i = 0; i < 5; i++) {
            order.markRiskChecked(); // Change state
            limitedTxManager.checkpoint(order);
        }
        
        assertThat(limitedTxManager.getCheckpointCount(order.getOrderId())).isEqualTo(3);
    }
    
    @Test
    @DisplayName("Should clear checkpoints")
    void testClearCheckpoints() {
        txManager.checkpoint(order);
        txManager.checkpoint(order);
        txManager.checkpoint(order);
        
        txManager.clearCheckpoints(order.getOrderId());
        
        assertThat(txManager.hasCheckpoints(order.getOrderId())).isFalse();
        assertThat(txManager.getCheckpointCount(order.getOrderId())).isZero();
    }
    
    @Test
    @DisplayName("Should handle multiple orders independently")
    void testMultipleOrders() {
        Order order2 = new Order("ORD-002", "GOOGL", 50, new BigDecimal("2800.00"));
        
        txManager.checkpoint(order);
        txManager.checkpoint(order2);
        
        assertThat(txManager.getCheckpointCount("ORD-001")).isEqualTo(1);
        assertThat(txManager.getCheckpointCount("ORD-002")).isEqualTo(1);
    }
    
    // Complete Workflow Tests
    
    @Test
    @DisplayName("Should support risk check failure rollback")
    void testRiskCheckFailureScenario() {
        order.submit();
        txManager.checkpoint(order);
        
        order.markRiskChecked();
        // Risk check fails - rollback
        txManager.rollback(order);
        
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.SUBMITTED);
        
        order.reject("Risk limit exceeded");
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.REJECTED);
    }
    
    @Test
    @DisplayName("Should support partial fill recovery")
    void testPartialFillRecoveryScenario() {
        order.markPendingExecution();
        txManager.checkpoint(order);
        
        order.fillPartial(50, new BigDecimal("150.00"));
        order.fillPartial(30, new BigDecimal("150.50"));
        txManager.checkpoint(order);
        
        // Connection lost - rollback
        txManager.rollback(order);
        
        assertThat(order.getFilledQuantity()).isEqualTo(80);
        
        // Rollback further
        txManager.rollback(order);
        assertThat(order.getFilledQuantity()).isZero();
    }
    
    @Test
    @DisplayName("Should support complex order lifecycle")
    void testComplexOrderLifecycle() {
        // Submit
        order.submit();
        txManager.checkpoint(order);
        
        // Risk check
        order.markRiskChecked();
        txManager.checkpoint(order);
        
        // Pending execution
        order.markPendingExecution();
        txManager.checkpoint(order);
        
        // Partial fills
        order.fillPartial(30, new BigDecimal("150.00"));
        txManager.checkpoint(order);
        
        order.fillPartial(40, new BigDecimal("150.50"));
        txManager.checkpoint(order);
        
        order.fillPartial(30, new BigDecimal("151.00"));
        txManager.checkpoint(order);
        
        assertThat(order.getFilledQuantity()).isEqualTo(100);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.EXECUTED);
        
        // Rollback 2 steps
        txManager.rollbackTo(order, 2);
        assertThat(order.getFilledQuantity()).isEqualTo(70);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PARTIALLY_FILLED);
    }
    
    @Test
    @DisplayName("Should handle eviction with FIFO")
    void testCheckpointEvictionFIFO() {
        TransactionManager limitedTxManager = new TransactionManager(2);
        
        order.submit();
        limitedTxManager.checkpoint(order); // Checkpoint 1
        
        order.markRiskChecked();
        limitedTxManager.checkpoint(order); // Checkpoint 2
        
        order.markPendingExecution();
        limitedTxManager.checkpoint(order); // Checkpoint 3 - evicts Checkpoint 1
        
        // Can only rollback twice now
        limitedTxManager.rollback(order); // Back to RISK_CHECKED
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.RISK_CHECKED);
        
        limitedTxManager.rollback(order); // Back to SUBMITTED (Checkpoint 1 was evicted, but Checkpoint 2 exists)
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.SUBMITTED);
        
        // Third rollback should fail (no more checkpoints)
        boolean success = limitedTxManager.rollback(order);
        assertThat(success).isFalse();
    }
    
    @Test
    @DisplayName("Should handle timestamp updates")
    void testTimestampUpdates() {
        LocalDateTime before = order.getSubmittedAt();
        
        order.markRiskChecked();
        LocalDateTime after = order.getLastUpdated();
        
        assertThat(after).isAfterOrEqualTo(before);
    }
}
