package com.patterns.templatemethod.simulation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data model for report generation.
 */
public class ReportData {
    
    private final String title;
    private final List<DataRow> rows;
    private final LocalDate reportDate;
    
    public ReportData(String title, List<DataRow> rows, LocalDate reportDate) {
        this.title = title;
        this.rows = new ArrayList<>(rows);
        this.reportDate = reportDate;
    }
    
    public String getTitle() {
        return title;
    }
    
    public List<DataRow> getRows() {
        return new ArrayList<>(rows);
    }
    
    public LocalDate getReportDate() {
        return reportDate;
    }
    
    /**
     * Represents a single data row in the report.
     */
    public static class DataRow {
        private final String product;
        private final BigDecimal amount;
        private final int quantity;
        
        public DataRow(String product, BigDecimal amount, int quantity) {
            this.product = product;
            this.amount = amount;
            this.quantity = quantity;
        }
        
        public String getProduct() {
            return product;
        }
        
        public BigDecimal getAmount() {
            return amount;
        }
        
        public int getQuantity() {
            return quantity;
        }
    }
}
