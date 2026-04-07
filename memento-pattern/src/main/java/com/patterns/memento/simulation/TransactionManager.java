package com.patterns.memento.simulation;

import com.patterns.memento.simulation.Order.Snapshot;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages transaction checkpoints and rollback for orders.
 * Acts as the Caretaker in the Memento pattern.
 */
public class TransactionManager {
    
    private final Map<String, Deque<Snapshot>> checkpoints;
    private final int maxCheckpointsPerOrder;
    
    /**
     * Creates a transaction manager with default history limit (10).
     */
    public TransactionManager() {
        this(10);
    }
    
    /**
     * Creates a transaction manager with specified history limit.
     *
     * @param maxCheckpointsPerOrder maximum checkpoints per order
     */
    public TransactionManager(int maxCheckpointsPerOrder) {
        this.checkpoints = new HashMap<>();
        this.maxCheckpointsPerOrder = maxCheckpointsPerOrder;
    }
    
    /**
     * Creates a checkpoint for the given order.
     *
     * @param order order to checkpoint
     */
    public void checkpoint(Order order) {
        // TODO: Implement checkpointing
        // 1. Get order ID
        // 2. Get or create Deque for this order ID
        //    Use: checkpoints.computeIfAbsent(orderId, k -> new ArrayDeque<>())
        // 3. Check if history size >= maxCheckpointsPerOrder
        //    If so, remove oldest: history.removeFirst()
        // 4. Create snapshot and push to history: history.push(order.snapshot())
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Rolls back order to its last checkpoint.
     *
     * @param order order to rollback
     * @return true if rollback successful, false if no checkpoints
     */
    public boolean rollback(Order order) {
        // TODO: Implement single-step rollback
        // 1. Get checkpoints for this order ID
        // 2. Check if null or empty - return false
        // 3. Pop most recent snapshot
        // 4. Restore order from snapshot
        // 5. Return true
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Rolls back order multiple steps.
     *
     * @param order order to rollback
     * @param stepsBack number of steps to rollback
     * @return true if rollback successful, false if not enough checkpoints
     */
    public boolean rollbackTo(Order order, int stepsBack) {
        // TODO: Implement multi-step rollback
        // 1. Validate stepsBack > 0
        // 2. Get checkpoints for order
        // 3. Pop 'stepsBack' times (or until empty)
        // 4. Restore from last popped snapshot
        // 5. Return true if successful, false otherwise
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Clears all checkpoints for an order.
     *
     * @param orderId order ID
     */
    public void clearCheckpoints(String orderId) {
        // TODO: Remove all checkpoints for order
        // Use: checkpoints.remove(orderId)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Gets the number of checkpoints for an order.
     *
     * @param orderId order ID
     * @return checkpoint count
     */
    public int getCheckpointCount(String orderId) {
        // TODO: Return size of checkpoint deque for order
        // Return 0 if no checkpoints exist
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Checks if order has any checkpoints.
     *
     * @param orderId order ID
     * @return true if checkpoints exist
     */
    public boolean hasCheckpoints(String orderId) {
        // TODO: Check if checkpoints exist and are not empty
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
