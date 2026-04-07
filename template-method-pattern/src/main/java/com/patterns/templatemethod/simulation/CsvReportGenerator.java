package com.patterns.templatemethod.simulation;

import com.patterns.templatemethod.simulation.ReportData.DataRow;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates reports in CSV format.
 * Demonstrates filtering hook to exclude zero-amount rows.
 */
public class CsvReportGenerator extends ReportGenerator {
    
    @Override
    protected void formatHeader(String title, LocalDate date) {
        // TODO: Format CSV header
        // Add line: title
        // Add line: "Product,Amount,Quantity"
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    protected void formatDataRow(DataRow row) {
        // TODO: Format as CSV row
        // Format: product + "," + amount (2 decimals) + "," + quantity
        // Use: row.getAmount().setScale(2, RoundingMode.HALF_UP)
        // Add to output
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    protected void formatFooter(ReportMetrics metrics) {
        // TODO: Format CSV footer
        // Add line: "TOTAL," + totalAmount (2 decimals) + "," + itemCount
        // Use: metrics.getTotalAmount().setScale(2, RoundingMode.HALF_UP)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    protected String export() {
        // TODO: Join all output lines with newlines
        // Use: String.join("\n", output)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    protected List<DataRow> applyFilters(List<DataRow> rows) {
        // TODO: Filter out rows with zero or negative amounts
        // Create new list containing only rows where amount > 0
        // Use: row.getAmount().compareTo(BigDecimal.ZERO) > 0
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    // Note: Uses default implementation for afterFormatting()
}
