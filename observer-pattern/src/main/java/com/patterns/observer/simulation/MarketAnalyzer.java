package com.patterns.observer.simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Aggregates statistics and trends across multiple stocks.
 */
public class MarketAnalyzer implements StockObserver {

    public static final double THRESHOLD_PERCENTAGE = 0.01d;
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
        this.stockEvents.computeIfAbsent(event.symbol(), k -> new ArrayList<>()).add(event);
    }
    
    @Override
    public String getObserverId() {
        return "MarketAnalyzer-" + analyzerId;
    }
    
    @Override
    public boolean supportsEvent(StockEvent event) {
        return event.eventType().hasMatch(StockEventType.PRICE_DECREASE, StockEventType.PRICE_INCREASE);
    }
    
    /**
     * Calculates average percent change across all tracked stocks.
     *
     * @return average percent change
     */
    public double getAveragePercentChange() {
        return this.stockEvents
                .values()
                .stream()
                .flatMap(List::stream)
                .mapToDouble(StockEvent::percentChange)
                .average()
                .orElse(1.0d);
    }
    
    /**
     * Determines market trend based on average price changes.
     *
     * @return "BULLISH" if avg > 1%, "BEARISH" if avg < -1%, otherwise "NEUTRAL"
     */
    public String getTrend() {
        double avgPercentChange = getAveragePercentChange();
        if(avgPercentChange > THRESHOLD_PERCENTAGE) {
            return "BULLISH";
        }
        if(avgPercentChange < -THRESHOLD_PERCENTAGE) {
            return "BEARISH";
        }
        return "NEUTRAL";
    }
    
    /**
     * Returns the number of stocks being tracked.
     *
     * @return number of unique stocks
     */
    public long getTrackedStockCount() {
        return this.stockEvents.size();
    }
    
    /**
     * Returns total number of events recorded.
     *
     * @return total event count
     */
    public long getTotalEventCount() {
        return this.stockEvents.values().stream().mapToLong(List::size).sum();
    }
    
    public Map<String, List<StockEvent>> getStockEvents() {
        return new HashMap<>(stockEvents);
    }
}
