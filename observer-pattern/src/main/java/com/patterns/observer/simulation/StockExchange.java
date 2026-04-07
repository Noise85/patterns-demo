package com.patterns.observer.simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Central registry for all stocks in the exchange.
 * Provides stock lookup and management capabilities.
 */
public class StockExchange {
    
    private final Map<String, Stock> stocks;
    
    public StockExchange() {
        this.stocks = new HashMap<>();
    }
    
    /**
     * Adds a stock to the exchange.
     *
     * @param stock the stock to add
     */
    public void addStock(Stock stock) {
        // TODO: Add stock to stocks map (key: symbol, value: stock)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Retrieves a stock by symbol.
     *
     * @param symbol the stock symbol
     * @return the stock, or null if not found
     */
    public Stock getStock(String symbol) {
        // TODO: Return stock from stocks map, or null if not found
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Convenience method to observe a stock by symbol.
     *
     * @param symbol   the stock symbol
     * @param observer the observer to register
     * @return true if stock found and observer registered, false otherwise
     */
    public boolean observeStock(String symbol, StockObserver observer) {
        // TODO: Find stock by symbol and register observer
        // Return true if stock found, false otherwise
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Returns the number of stocks in the exchange.
     *
     * @return stock count
     */
    public int getStockCount() {
        // TODO: Return size of stocks map
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
