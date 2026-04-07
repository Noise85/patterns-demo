package com.patterns.memento.simulation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * Represents a trading order with snapshot/restore capabilities.
 * Demonstrates the Memento pattern for transaction management.
 */
public class Order {
    
    // Immutable fields
    private final String orderId;
    private final String symbol;
    private final int quantity;
    private final BigDecimal price; // null for market orders
    private final LocalDateTime submittedAt;
    
    // Mutable fields
    private OrderStatus orderStatus;
    private int filledQuantity;
    private BigDecimal averageFillPrice;
    private LocalDateTime lastUpdated;
    
    /**
     * Creates a new order.
     *
     * @param orderId unique order identifier
     * @param symbol trading symbol
     * @param quantity total order quantity
     * @param price limit price (null for market orders)
     */
    public Order(String orderId, String symbol, int quantity, BigDecimal price) {
        this.orderId = orderId;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.submittedAt = LocalDateTime.now();
        this.orderStatus = OrderStatus.SUBMITTED;
        this.filledQuantity = 0;
        this.averageFillPrice = BigDecimal.ZERO;
        this.lastUpdated = LocalDateTime.now();
    }
    
    /**
     * Marks order as submitted.
     */
    public void submit() {
        // TODO: Set status to SUBMITTED, update lastUpdated
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Marks order as risk checked.
     */
    public void markRiskChecked() {
        // TODO: Set status to RISK_CHECKED, update lastUpdated
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Marks order as pending execution.
     */
    public void markPendingExecution() {
        // TODO: Set status to PENDING_EXECUTION, update lastUpdated
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Records a partial fill.
     * Calculates weighted average fill price.
     *
     * @param fillQuantity quantity filled
     * @param fillPrice price of this fill
     */
    public void fillPartial(int fillQuantity, BigDecimal fillPrice) {
        // TODO: Implement partial fill logic
        // 1. Calculate new average fill price:
        //    - If filledQuantity == 0: averageFillPrice = fillPrice
        //    - Otherwise: weighted average formula
        //      newAvg = (oldAvg * oldQty + fillPrice * fillQty) / (oldQty + fillQty)
        // 2. Update filledQuantity
        // 3. Update status:
        //    - If filledQuantity == quantity: EXECUTED
        //    - Otherwise: PARTIALLY_FILLED
        // 4. Update lastUpdated
        //
        // IMPORTANT: Use BigDecimal methods:
        //   - multiply() not *
        //   - add() not +
        //   - divide(divisor, scale, roundingMode) with scale=2, RoundingMode.HALF_UP
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Marks order as fully executed.
     */
    public void completeExecution() {
        // TODO: Set status to EXECUTED, update lastUpdated
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Rejects the order.
     *
     * @param reason rejection reason (for logging, not stored)
     */
    public void reject(String reason) {
        // TODO: Set status to REJECTED, update lastUpdated
        // Note: reason parameter is for logging, don't need to store it
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Cancels the order.
     */
    public void cancel() {
        // TODO: Set status to CANCELLED, update lastUpdated
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Creates a snapshot of current order state.
     *
     * @return immutable snapshot
     */
    public Snapshot snapshot() {
        // TODO: Create and return new Snapshot
        // Pass 'this' (the Order) to the Snapshot constructor
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Restores order state from a snapshot.
     *
     * @param snapshot snapshot to restore from
     */
    public void restore(Snapshot snapshot) {
        // TODO: Restore mutable fields from snapshot
        // DO restore: orderStatus, filledQuantity, averageFillPrice, lastUpdated
        // DON'T restore: orderId, symbol, quantity, price, submittedAt (immutable!)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    // Getters for testing
    
    public String getOrderId() {
        return orderId;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
    
    public int getFilledQuantity() {
        return filledQuantity;
    }
    
    public BigDecimal getAverageFillPrice() {
        return averageFillPrice;
    }
    
    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }
    
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
    
    /**
     * Snapshot class - immutable capture of order state.
     * Nested class with restricted access.
     */
    public static final class Snapshot {
        
        private final String orderId;
        private final String symbol;
        private final int quantity;
        private final BigDecimal price;
        
        private final OrderStatus orderStatus;
        private final int filledQuantity;
        private final BigDecimal averageFillPrice;
        private final LocalDateTime submittedAt;
        private final LocalDateTime lastUpdated;
        private final LocalDateTime snapshotTime;
        
        /**
         * Private constructor - only Order can create snapshots.
         *
         * @param order order to snapshot
         */
        private Snapshot(Order order) {
            // TODO: Copy all fields from order
            // Note: BigDecimal and LocalDateTime are immutable, safe to assign directly
            // Set snapshotTime to LocalDateTime.now()
            throw new UnsupportedOperationException("Not implemented yet");
        }
        
        // Package-private getters - Order can access
        
        String getOrderId() {
            // TODO: Return orderId
            throw new UnsupportedOperationException("Not implemented yet");
        }
        
        String getSymbol() {
            // TODO: Return symbol
            throw new UnsupportedOperationException("Not implemented yet");
        }
        
        int getQuantity() {
            // TODO: Return quantity
            throw new UnsupportedOperationException("Not implemented yet");
        }
        
        BigDecimal getPrice() {
            // TODO: Return price
            throw new UnsupportedOperationException("Not implemented yet");
        }
        
        OrderStatus getOrderStatus() {
            // TODO: Return orderStatus
            throw new UnsupportedOperationException("Not implemented yet");
        }
        
        int getFilledQuantity() {
            // TODO: Return filledQuantity
            throw new UnsupportedOperationException("Not implemented yet");
        }
        
        BigDecimal getAverageFillPrice() {
            // TODO: Return averageFillPrice
            throw new UnsupportedOperationException("Not implemented yet");
        }
        
        LocalDateTime getSubmittedAt() {
            // TODO: Return submittedAt
            throw new UnsupportedOperationException("Not implemented yet");
        }
        
        LocalDateTime getLastUpdated() {
            // TODO: Return lastUpdated
            throw new UnsupportedOperationException("Not implemented yet");
        }
        
        LocalDateTime getSnapshotTime() {
            // TODO: Return snapshotTime
            throw new UnsupportedOperationException("Not implemented yet");
        }
    }
}
