package com.patterns.observer.simulation;

import java.math.BigDecimal;

/**
 * Monitor that triggers alerts when stock price crosses thresholds.
 */
public class PriceAlertMonitor implements StockObserver {
    
    private final String alertId;
    private final String targetSymbol;
    private final BigDecimal threshold;
    private final AlertType alertType;
    private boolean triggered;
    private String lastAlertMessage;
    
    /**
     * Creates a new price alert monitor.
     *
     * @param alertId      unique alert identifier
     * @param targetSymbol stock symbol to monitor
     * @param threshold    price threshold
     * @param alertType    type of alert (ABOVE or BELOW)
     */
    public PriceAlertMonitor(String alertId, String targetSymbol, BigDecimal threshold, AlertType alertType) {
        this.alertId = alertId;
        this.targetSymbol = targetSymbol;
        this.threshold = threshold;
        this.alertType = alertType;
        this.triggered = false;
    }
    
    @Override
    public void onStockUpdate(StockEvent event) {
        // TODO: Check if alert should trigger
        // 1. Verify this event is for our target symbol
        // 2. Check threshold condition based on alertType
        // 3. If triggered, set triggered = true and create alert message
        // 4. Store alert message in lastAlertMessage
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getObserverId() {
        return "PriceAlert-" + alertId;
    }
    
    @Override
    public boolean isInterestedIn(StockEvent event) {
        // TODO: Filter events
        // Only interested if:
        // 1. Event is for our target symbol
        // 2. Not already triggered (one-time alert)
        // 3. Price crosses threshold in the correct direction
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public boolean isTriggered() {
        return triggered;
    }
    
    public String getLastAlertMessage() {
        return lastAlertMessage;
    }
    
    public String getTargetSymbol() {
        return targetSymbol;
    }
    
    public BigDecimal getThreshold() {
        return threshold;
    }
    
    public AlertType getAlertType() {
        return alertType;
    }
}
