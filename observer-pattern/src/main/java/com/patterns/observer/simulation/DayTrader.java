package com.patterns.observer.simulation;

/**
 * Short-term trader who tracks rapid price movements.
 * Interested in all price changes > 0.5%.
 */
public class DayTrader implements StockObserver {
    
    private final String name;
    private int opportunityCount;
    
    /**
     * Creates a new day trader.
     *
     * @param name trader name
     */
    public DayTrader(String name) {
        this.name = name;
        this.opportunityCount = 0;
    }
    
    @Override
    public void onStockUpdate(StockEvent event) {
        this.opportunityCount++;
    }
    
    @Override
    public String getObserverId() {
        return "DayTrader-" + name;
    }
    
    @Override
    public boolean supportsEvent(StockEvent event) {
        return Math.abs(event.percentChange()) > 0.05d;
    }
    
    public int getOpportunityCount() {
        return opportunityCount;
    }
    
    public String getName() {
        return name;
    }
}
