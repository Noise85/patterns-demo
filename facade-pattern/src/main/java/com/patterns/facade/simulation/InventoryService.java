package com.patterns.facade.simulation;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Inventory subsystem managing product stock levels.
 */
public class InventoryService {
    
    private final Map<String, Integer> stockLevels = new ConcurrentHashMap<>();
    private final Map<String, ReservationRecord> reservations = new ConcurrentHashMap<>();
    
    /**
     * Initializes inventory with stock levels.
     *
     * @param productId the product identifier
     * @param quantity the available quantity
     */
    public void initializeStock(String productId, int quantity) {
        stockLevels.put(productId, quantity);
    }
    
    /**
     * Checks if requested items are available.
     *
     * @param productId the product identifier
     * @param quantity the requested quantity
     * @return true if available, false otherwise
     */
    public boolean checkAvailability(String productId, int quantity) {
        // TODO: Implement availability check
        // - Get current stock level for product
        // - Return true if available >= requested quantity
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Reserves items for an order without deducting from stock.
     *
     * @param orderId the order identifier
     * @param items the items to reserve
     * @return reservation identifier
     * @throws InsufficientStockException if any item is out of stock
     */
    public String reserveItems(String orderId, List<OrderItem> items) {
        // TODO: Implement reservation logic
        // - Check availability for ALL items first
        // - If any item unavailable, throw InsufficientStockException
        // - Create reservation record (don't deduct stock yet)
        // - Store reservation with generated ID
        // - Return reservation ID
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Commits a reservation by actually deducting from stock.
     *
     * @param reservationId the reservation to commit
     */
    public void commitReservation(String reservationId) {
        // TODO: Implement commit logic
        // - Get reservation record
        // - Deduct reserved quantities from stock
        // - Remove reservation record
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Releases a reservation without deducting stock.
     *
     * @param reservationId the reservation to release
     */
    public void releaseReservation(String reservationId) {
        // TODO: Implement release logic
        // - Remove reservation record (no stock changes needed)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public int getAvailableStock(String productId) {
        return stockLevels.getOrDefault(productId, 0);
    }
    
    /**
     * Internal record for tracking reservations.
     */
    private record ReservationRecord(String orderId, List<OrderItem> items) {}
}
