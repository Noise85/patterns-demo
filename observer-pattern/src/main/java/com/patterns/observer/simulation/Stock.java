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
        if(!this.observers.contains(observer))
            this.observers.add(observer);
    }
    
    /**
     * Removes an observer from receiving updates.
     *
     * @param observer the observer to remove
     */
    public void removeObserver(StockObserver observer) {
        this.observers.remove(observer);
    }
    
    /**
     * Updates the stock price and notifies observers.
     *
     * @param newPrice new stock price
     * @param volume   trading volume
     */
    public void updatePrice(BigDecimal newPrice, long volume) {
        this.previousPrice = this.currentPrice;
        this.currentPrice = newPrice;
        this.volume = volume;
        this.notifyObservers(new StockEvent(
            this.symbol,
            this.currentPrice.compareTo(this.previousPrice) > 0 ? StockEventType.PRICE_INCREASE : StockEventType.PRICE_DECREASE,
            LocalDateTime.now(),
            this.previousPrice,
            this.currentPrice,
            this.volume,
            this.calculatePriceChange(this.previousPrice, this.currentPrice),
            this.calculatePercentChange(this.previousPrice, this.currentPrice)
        ));
    }
    
    /**
     * Notifies all interested observers about a stock event.
     *
     * @param event the stock event
     */
    private void notifyObservers(StockEvent event) {
        getObservers().stream()
                .filter(o -> o.supportsEvent(event))
                .forEach(o -> o.onStockUpdate(event));
    }
    
    /**
     * Calculates percentage change between two prices.
     *
     * @param previousPrice the old price
     * @param currentPrice the new price
     * @return percentage change
     */
    private double calculatePercentChange(BigDecimal previousPrice, BigDecimal currentPrice) {
        double percent = Math.abs(1-currentPrice.divide(previousPrice, 2, RoundingMode.HALF_UP).doubleValue());
        if(previousPrice.compareTo(currentPrice) < 0) {
            return percent;
        }
        return -percent;
    }

    /**
     * Calculates price change between two prices.
     * @param previousPrice the old price
     * @param currentPrice the new price
     * @return double the difference between the two prices
     */
    private BigDecimal calculatePriceChange(BigDecimal previousPrice, BigDecimal currentPrice) {
        return currentPrice.subtract(previousPrice);
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

    public List<StockObserver> getObservers() {
        return new ArrayList<>(this.observers);
    }

    public boolean isRegistered(StockObserver observer) {
        return getObservers().contains(observer);
    }
    
    public int getObserverCount() {
        return this.observers.size();
    }
}
