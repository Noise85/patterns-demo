package com.patterns.observer.simulation;

/**
 * Types of stock events that can occur.
 */
public enum StockEventType {
    /**
     * Stock price increased.
     */
    PRICE_INCREASE,
    
    /**
     * Stock price decreased.
     */
    PRICE_DECREASE,
    
    /**
     * Trading volume spiked significantly.
     */
    VOLUME_SPIKE,
    
    /**
     * Market opened for trading.
     */
    MARKET_OPEN,
    
    /**
     * Market closed for trading.
     */
    MARKET_CLOSE,
    
    /**
     * Trading halted for this stock.
     */
    TRADING_HALTED
}
