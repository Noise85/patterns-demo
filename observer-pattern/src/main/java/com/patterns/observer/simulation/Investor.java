package com.patterns.observer.simulation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Long-term investor who tracks portfolio value.
 * Only interested in significant price changes (>2%).
 */
public class Investor implements StockObserver {
    
    private final String name;
    private final Map<String, Integer> holdings;  // symbol -> number of shares
    private BigDecimal portfolioValue;
    
    /**
     * Creates a new investor.
     *
     * @param name investor name
     */
    public Investor(String name) {
        this.name = name;
        this.holdings = new HashMap<>();
        this.portfolioValue = BigDecimal.ZERO;
    }
    
    /**
     * Adds shares to the portfolio.
     *
     * @param symbol stock symbol
     * @param shares number of shares
     */
    public void addHolding(String symbol, int shares) {
        // TODO: Add or update holding in holdings map
        // If symbol exists, add to existing shares
        // If symbol doesn't exist, create new entry
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void onStockUpdate(StockEvent event) {
        // TODO: Update portfolio value when stock prices change
        // Calculate new value of holdings for this stock: shares * newPrice
        // Update portfolioValue accordingly
        // Log significant changes if needed
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getObserverId() {
        return "Investor-" + name;
    }
    
    @Override
    public boolean isInterestedIn(StockEvent event) {
        // TODO: Filter events
        // Only interested if:
        // 1. We hold this stock (check holdings map)
        // 2. Price change is significant (absolute percent change > 2.0)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public Map<String, Integer> getHoldings() {
        return new HashMap<>(holdings);
    }
    
    public BigDecimal getPortfolioValue() {
        return portfolioValue;
    }
    
    public String getName() {
        return name;
    }
}
