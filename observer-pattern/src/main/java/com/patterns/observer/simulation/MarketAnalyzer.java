package com.patterns.observer.simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Aggregates statistics and trends across multiple stocks.
 */
public class MarketAnalyzer implements StockObserver {
    
    private final String analyzerId;
    private final Map<String, List<StockEvent>> stockEvents;
    
    /**
     * Creates a new market analyzer.
     *
     * @param analyzerId unique analyzer identifier
     */
    public MarketAnalyzer(String analyzerId) {
        this.analyzerId = analyzerId;
        this.stockEvents = new HashMap<>();
    }
    
    @Override
    public void onStockUpdate(StockEvent event) {
        // TODO: Record event for analysis
        // Add event to stockEvents map (create list if doesn't exist)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getObserverId() {
        return "MarketAnalyzer-" + analyzerId;
    }
    
    @Override
    public boolean isInterestedIn(StockEvent event) {
        // TODO: Interested in all price change events
        // Return true for PRICE_INCREASE and PRICE_DECREASE events
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Calculates average percent change across all tracked stocks.
     *
     * @return average percent change
     */
    public double getAveragePercentChange() {
        // TODO: Calculate average percent change
        // Get all events from stockEvents, extract percentChange, calculate average
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Determines market trend based on average price changes.
     *
     * @return "BULLISH" if avg > 1%, "BEARISH" if avg < -1%, otherwise "NEUTRAL"
     */
    public String getTrend() {
        // TODO: Determine trend based on average percent change
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Returns the number of stocks being tracked.
     *
     * @return number of unique stocks
     */
    public int getTrackedStockCount() {
        // TODO: Return number of unique stocks in stockEvents map
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Returns total number of events recorded.
     *
     * @return total event count
     */
    public int getTotalEventCount() {
        // TODO: Count total events across all stocks
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public Map<String, List<StockEvent>> getStockEvents() {
        return new HashMap<>(stockEvents);
    }
}
