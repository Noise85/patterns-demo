package com.patterns.templatemethod.simulation;

import com.patterns.templatemethod.simulation.ReportData.DataRow;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for report generation using Template Method pattern.
 * Defines the algorithm skeleton for creating reports in different formats.
 */
public abstract class ReportGenerator {
    
    protected final List<String> output = new ArrayList<>();
    
    /**
     * Template method - orchestrates report generation.
     * Final to ensure consistent report structure across all formats.
     *
     * @param data report data
     * @return formatted report as string
     */
    public final String generateReport(ReportData data) {
        // Step 1: Apply filters (if needed)
        List<DataRow> filteredRows = applyFilters(data.getRows());
        
        // Step 2: Calculate metrics
        ReportMetrics metrics = calculateMetrics(filteredRows);
        
        // Step 3: Build report
        output.clear();
        formatHeader(data.getTitle(), data.getReportDate());
        
        for (DataRow row : filteredRows) {
            formatDataRow(row);
        }
        
        formatFooter(metrics);
        
        // Step 4: Post-processing hook
        afterFormatting();
        
        // Step 5: Export
        return export();
    }
    
    /**
     * Formats the report header.
     * Primitive operation - must be implemented by subclasses.
     *
     * @param title report title
     * @param date report date
     */
    protected abstract void formatHeader(String title, LocalDate date);
    
    /**
     * Formats a single data row.
     * Primitive operation - must be implemented by subclasses.
     *
     * @param row data row to format
     */
    protected abstract void formatDataRow(DataRow row);
    
    /**
     * Formats the report footer with metrics.
     * Primitive operation - must be implemented by subclasses.
     *
     * @param metrics calculated metrics
     */
    protected abstract void formatFooter(ReportMetrics metrics);
    
    /**
     * Exports the report to final format.
     * Primitive operation - must be implemented by subclasses.
     *
     * @return formatted report string
     */
    protected abstract String export();
    
    /**
     * Hook method - applies filters to data rows.
     * Default returns all rows. Override to implement filtering.
     *
     * @param rows original rows
     * @return filtered rows
     */
    protected List<DataRow> applyFilters(List<DataRow> rows) {
        return rows;
    }
    
    /**
     * Hook method - called after formatting completes.
     * Default does nothing. Override for post-processing.
     */
    protected void afterFormatting() {
        // Default: do nothing
    }
    
    /**
     * Common operation - calculates report metrics.
     * Shared by all report types.
     *
     * @param rows data rows to calculate metrics from
     * @return calculated metrics
     */
    protected final ReportMetrics calculateMetrics(List<DataRow> rows) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        int totalQuantity = 0;
        
        for (DataRow row : rows) {
            totalAmount = totalAmount.add(row.getAmount());
            totalQuantity += row.getQuantity();
        }
        
        return new ReportMetrics(totalAmount, totalQuantity, rows.size());
    }
}
