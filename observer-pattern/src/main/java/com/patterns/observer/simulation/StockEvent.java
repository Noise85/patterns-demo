package com.patterns.observer.simulation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Record representing a stock event with comprehensive change details.
 *
 * @param symbol         stock ticker symbol
 * @param eventType      type of event that occurred
 * @param timestamp      when the event occurred
 * @param oldPrice       price before the change
 * @param newPrice       price after the change
 * @param volume         trading volume
 * @param priceChange    absolute price change (newPrice - oldPrice)
 * @param percentChange  percentage price change
 */
public record StockEvent(
    String symbol,
    StockEventType eventType,
    LocalDateTime timestamp,
    BigDecimal oldPrice,
    BigDecimal newPrice,
    long volume,
    BigDecimal priceChange,
    double percentChange
) {
    
    @Override
    public String toString() {
        return String.format("[%s] %s: %s -> %s (%.2f%%)",
            timestamp, symbol, oldPrice, newPrice, percentChange);
    }
}
