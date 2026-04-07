package com.patterns.templatemethod.simulation;

import com.patterns.templatemethod.simulation.ReportData.DataRow;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 * Generates reports in HTML format.
 * Demonstrates generating structured markup.
 */
public class HtmlReportGenerator extends ReportGenerator {
    
    @Override
    protected void formatHeader(String title, LocalDate date) {
        // TODO: Format HTML header
        // Add line: "<!DOCTYPE html>"
        // Add line: "<html><body>"
        // Add line: "<h1>" + title + "</h1>"
        // Add line: "<p class='metadata'>Generated: " + date + "</p>"
        // Add line: "<table border='1'>"
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    protected void formatDataRow(DataRow row) {
        // TODO: Format as HTML table row
        // Add line: "<tr>"
        // Add line: "  <td>" + product + "</td>"
        // Add line: "  <td>$" + amount (2 decimals) + "</td>"
        //   - Use: row.getAmount().setScale(2, RoundingMode.HALF_UP)
        // Add line: "  <td>" + quantity + "</td>"
        // Add line: "</tr>"
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    protected void formatFooter(ReportMetrics metrics) {
        // TODO: Format HTML footer
        // Add line: "</table>"
        // Add line: "<div class='summary'>Total: $" + totalAmount (2 decimals) + "</div>"
        //   - Use: metrics.getTotalAmount().setScale(2, RoundingMode.HALF_UP)
        // Add line: "</body></html>"
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    protected String export() {
        // TODO: Join all output lines with newlines
        // Use: String.join("\n", output)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    // Note: Uses default implementations for applyFilters() and afterFormatting()
}
