package com.patterns.observer.simulation;

import java.util.HashMap;
import java.util.Map;

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
        this.stocks.put(stock.getSymbol(), stock);
    }
    
    /**
     * Retrieves a stock by symbol.
     *
     * @param symbol the stock symbol
     * @return the stock, or null if not found
     */
    public Stock getStock(String symbol) {
        return this.stocks.get(symbol);
    }
    
    /**
     * Convenience method to observe a stock by symbol.
     *
     * @param symbol   the stock symbol
     * @param observer the observer to register
     * @return true if stock found and observer registered, false otherwise
     */
    public boolean observeStock(String symbol, StockObserver observer) {
        if(stocks.containsKey(symbol)) {
            stocks.get(symbol).registerObserver(observer);
        }
        return stocks.get(symbol).isRegistered(observer);
    }
    
    /**
     * Returns the number of stocks in the exchange.
     *
     * @return stock count
     */
    public int getStockCount() {
        return this.stocks.size();
    }
}
