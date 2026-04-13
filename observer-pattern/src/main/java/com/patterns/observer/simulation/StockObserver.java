package com.patterns.observer.simulation;

/**
 * Observer interface for stock market updates.
 * Observers can filter which events they're interested in.
 */
public interface StockObserver {
    
    /**
     * Called when a stock event occurs.
     *
     * @param event the stock event containing details of the change
     */
    void onStockUpdate(StockEvent event);
    
    /**
     * Returns the unique identifier for this observer.
     *
     * @return observer ID
     */
    String getObserverId();
    
    /**
     * Determines if this observer is bound to the given event.
     * Allows observers to filter events they don't care about.
     *
     * @param event the event to evaluate
     * @return true if observer should be notified, false otherwise
     */
    boolean supportsEvent(StockEvent event);
}
