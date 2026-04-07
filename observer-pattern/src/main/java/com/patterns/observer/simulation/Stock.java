package com.patterns.observer.simulation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete subject representing a stock with observable price changes.
 */
public class Stock {
    
    private final String symbol;
    private final String name;
    private final List<StockObserver> observers;
    
    private BigDecimal currentPrice;
    private BigDecimal previousPrice;
    private long volume;
    
    /**
     * Creates a new stock.
     *
     * @param symbol ticker symbol
     * @param name   company name
     * @param initialPrice starting price
     */
    public Stock(String symbol, String name, BigDecimal initialPrice) {
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = initialPrice;
        this.previousPrice = initialPrice;
        this.volume = 0;
        this.observers = new ArrayList<>();
    }
    
    /**
     * Registers an observer to receive stock updates.
     *
     * @param observer the observer to register
     */
    public void registerObserver(StockObserver observer) {
        // TODO: Add observer to list if not already registered
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Removes an observer from receiving updates.
     *
     * @param observer the observer to remove
     */
    public void removeObserver(StockObserver observer) {
        // TODO: Remove observer from list
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Updates the stock price and notifies observers.
     *
     * @param newPrice new stock price
     * @param volume   trading volume
     */
    public void updatePrice(BigDecimal newPrice, long volume) {
        // TODO: Update price and notify observers
        // 1. Store old price: previousPrice = currentPrice
        // 2. Update currentPrice and volume
        // 3. Calculate price change and percent change
        // 4. Determine event type (PRICE_INCREASE or PRICE_DECREASE)
        // 5. Create StockEvent with all data
        // 6. Call notifyObservers(event)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Notifies all interested observers about a stock event.
     *
     * @param event the stock event
     */
    private void notifyObservers(StockEvent event) {
        // TODO: Notify observers with filtering
        // 1. Create defensive copy of observers list
        // 2. For each observer:
        //    - Check if observer.isInterestedIn(event)
        //    - If yes, call observer.onStockUpdate(event)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Calculates percentage change between two prices.
     *
     * @param oldPrice the old price
     * @param newPrice the new price
     * @return percentage change
     */
    private double calculatePercentChange(BigDecimal oldPrice, BigDecimal newPrice) {
        // TODO: Calculate percent change
        // Formula: ((newPrice - oldPrice) / oldPrice) * 100
        // Use BigDecimal.divide with RoundingMode.HALF_UP
        // Return as double
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    // Getters
    
    public String getSymbol() {
        return symbol;
    }
    
    public String getName() {
        return name;
    }
    
    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }
    
    public BigDecimal getPreviousPrice() {
        return previousPrice;
    }
    
    public long getVolume() {
        return volume;
    }
    
    public int getObserverCount() {
        // TODO: Return size of observers list
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
