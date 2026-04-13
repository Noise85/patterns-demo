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
        if(this.supportsEvent(event)) {
            this.triggered = true;
            this.lastAlertMessage = "Alert! " + event.symbol() + " price crossed $" + threshold;
        }
    }
    
    @Override
    public String getObserverId() {
        return "PriceAlert-" + alertId;
    }
    
    @Override
    public boolean supportsEvent(StockEvent event) {
        if(!this.triggered && event.symbol().equals(targetSymbol)) {
            return (priceIncreased(event) && alertType.equals(AlertType.ABOVE))
                    || (priceDecreased(event) && alertType.equals(AlertType.BELOW));
        }
        return false;
    }

    public boolean priceIncreased(StockEvent event) {
        return threshold.compareTo(event.newPrice()) < 0 && event.oldPrice().compareTo(event.newPrice()) < 0;
    }

    public boolean priceDecreased(StockEvent event) {
        return threshold.compareTo(event.oldPrice()) < 0 && event.newPrice().compareTo(event.oldPrice()) < 0;
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
