package com.patterns.templatemethod.simulation;

import com.patterns.templatemethod.simulation.ReportData.DataRow;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Generates reports in PDF format.
 * Demonstrates custom formatting and post-processing hook.
 */
public class PdfReportGenerator extends ReportGenerator {
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    
    @Override
    protected void formatHeader(String title, LocalDate date) {
        // TODO: Format PDF header
        // Add line: "=== " + title + " ==="
        // Add line: "Date: " + formatted date (use DATE_FORMAT)
        // Add line: "---"
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    protected void formatDataRow(DataRow row) {
        // TODO: Format as column-aligned data
        // Format: "Product: <product> | Amount: $<amount> | Qty: <quantity>"
        // Amount should be formatted with 2 decimal places
        // Use: row.getAmount().setScale(2, RoundingMode.HALF_UP)
        // Format string: String.format("Product: %s | Amount: $%.2f | Qty: %d", ...)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    protected void formatFooter(ReportMetrics metrics) {
        // TODO: Format PDF footer
        // Add line: "---"
        // Add line: "TOTAL: $<totalAmount> | Items: <itemCount>"
        //   - Use metrics.getTotalAmount().setScale(2, RoundingMode.HALF_UP)
        //   - Format: String.format("TOTAL: $%.2f | Items: %d", ...)
        // Add line: "Page 1 of 1"
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    protected String export() {
        // TODO: Join all output lines with newlines
        // Use: String.join("\n", output)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    protected void afterFormatting() {
        // TODO: Add digital signature line
        // Add to output: "[DIGITALLY SIGNED]"
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
