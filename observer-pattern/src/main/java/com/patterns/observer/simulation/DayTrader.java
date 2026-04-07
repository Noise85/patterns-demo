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
        // TODO: Track trading opportunities
        // Increment opportunityCount when meaningful price change occurs
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getObserverId() {
        return "DayTrader-" + name;
    }
    
    @Override
    public boolean isInterestedIn(StockEvent event) {
        // TODO: Filter events
        // Interested in all price changes where absolute percent change > 0.5
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public int getOpportunityCount() {
        return opportunityCount;
    }
    
    public String getName() {
        return name;
    }
}
