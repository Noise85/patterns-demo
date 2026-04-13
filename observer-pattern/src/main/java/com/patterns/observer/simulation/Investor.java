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
        this.holdings.put(symbol, shares);
    }
    
    @Override
    public void onStockUpdate(StockEvent event) {
        this.portfolioValue = this.portfolioValue.add(
            event.priceChange().multiply(
                new BigDecimal(
                        holdings.get(event.symbol())
                )
            )
        );
    }
    
    @Override
    public String getObserverId() {
        return "Investor-" + name;
    }
    
    @Override
    public boolean supportsEvent(StockEvent event) {
        return holdings.containsKey(event.symbol()) &&
                event.percentChange()>0.02d;
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
