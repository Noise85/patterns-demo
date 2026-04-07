package com.patterns.templatemethod.simulation;

import java.math.BigDecimal;

/**
 * Holds calculated metrics for a report.
 */
public class ReportMetrics {
    
    private final BigDecimal totalAmount;
    private final int totalQuantity;
    private final int itemCount;
    
    public ReportMetrics(BigDecimal totalAmount, int totalQuantity, int itemCount) {
        this.totalAmount = totalAmount;
        this.totalQuantity = totalQuantity;
        this.itemCount = itemCount;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public int getTotalQuantity() {
        return totalQuantity;
    }
    
    public int getItemCount() {
        return itemCount;
    }
}
