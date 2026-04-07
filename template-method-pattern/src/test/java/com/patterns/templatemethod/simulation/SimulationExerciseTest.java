package com.patterns.templatemethod.simulation;

import com.patterns.templatemethod.simulation.ReportData.DataRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Template Method Pattern - Simulation Exercise (Report Generation).
 */
@DisplayName("Template Method Pattern - Report Generation System")
class SimulationExerciseTest {
    
    private ReportData reportData;
    private PdfReportGenerator pdfGenerator;
    private HtmlReportGenerator htmlGenerator;
    private CsvReportGenerator csvGenerator;
    
    @BeforeEach
    void setUp() {
        List<DataRow> rows = Arrays.asList(
            new DataRow("Widget", new BigDecimal("100.00"), 5),
            new DataRow("Gadget", new BigDecimal("250.50"), 3),
            new DataRow("Doohickey", new BigDecimal("75.75"), 10)
        );
        
        reportData = new ReportData("Sales Report", rows, LocalDate.of(2026, 4, 8));
        
        pdfGenerator = new PdfReportGenerator();
        htmlGenerator = new HtmlReportGenerator();
        csvGenerator = new CsvReportGenerator();
    }
    
    // PDF Report Tests
    
    @Test
    @DisplayName("PDF: Should format header with title and date")
    void testPdfHeader() {
        String report = pdfGenerator.generateReport(reportData);
        
        assertThat(report)
            .contains("=== Sales Report ===")
            .contains("Date: 04/08/2026")
            .contains("---");
    }
    
    @Test
    @DisplayName("PDF: Should format data rows with proper alignment")
    void testPdfDataRows() {
        String report = pdfGenerator.generateReport(reportData);
        
        assertThat(report)
            .contains("Product: Widget | Amount: $100.00 | Qty: 5")
            .contains("Product: Gadget | Amount: $250.50 | Qty: 3")
            .contains("Product: Doohickey | Amount: $75.75 | Qty: 10");
    }
    
    @Test
    @DisplayName("PDF: Should format footer with totals")
    void testPdfFooter() {
        String report = pdfGenerator.generateReport(reportData);
        
        assertThat(report)
            .contains("TOTAL: $426.25 | Items: 3")
            .contains("Page 1 of 1");
    }
    
    @Test
    @DisplayName("PDF: Should add digital signature")
    void testPdfDigitalSignature() {
        String report = pdfGenerator.generateReport(reportData);
        
        assertThat(report).contains("[DIGITALLY SIGNED]");
    }
    
    @Test
    @DisplayName("PDF: Should format currency with 2 decimal places")
    void testPdfCurrencyFormatting() {
        List<DataRow> rows = Arrays.asList(
            new DataRow("Item", new BigDecimal("10.5"), 1)
        );
        ReportData data = new ReportData("Test", rows, LocalDate.now());
        
        String report = pdfGenerator.generateReport(data);
        
        assertThat(report).contains("$10.50"); // Not $10.5
    }
    
    // HTML Report Tests
    
    @Test
    @DisplayName("HTML: Should generate valid HTML structure")
    void testHtmlStructure() {
        String report = htmlGenerator.generateReport(reportData);
        
        assertThat(report)
            .startsWith("<!DOCTYPE html>")
            .contains("<html><body>")
            .contains("</body></html>")
            .endsWith("</body></html>");
    }
    
    @Test
    @DisplayName("HTML: Should format header with title")
    void testHtmlHeader() {
        String report = htmlGenerator.generateReport(reportData);
        
        assertThat(report)
            .contains("<h1>Sales Report</h1>")
            .contains("<p class='metadata'>Generated: 2026-04-08</p>")
            .contains("<table border='1'>");
    }
    
    @Test
    @DisplayName("HTML: Should format data rows as table rows")
    void testHtmlDataRows() {
        String report = htmlGenerator.generateReport(reportData);
        
        assertThat(report)
            .contains("<tr>")
            .contains("<td>Widget</td>")
            .contains("<td>$100.00</td>")
            .contains("<td>5</td>")
            .contains("</tr>");
    }
    
    @Test
    @DisplayName("HTML: Should format footer with table close and summary")
    void testHtmlFooter() {
        String report = htmlGenerator.generateReport(reportData);
        
        assertThat(report)
            .contains("</table>")
            .contains("<div class='summary'>Total: $426.25</div>");
    }
    
    @Test
    @DisplayName("HTML: Should format all three rows")
    void testHtmlAllRows() {
        String report = htmlGenerator.generateReport(reportData);
        
        assertThat(report)
            .contains("<td>Widget</td>")
            .contains("<td>Gadget</td>")
            .contains("<td>Doohickey</td>");
    }
    
    // CSV Report Tests
    
    @Test
    @DisplayName("CSV: Should format header with title and column names")
    void testCsvHeader() {
        String report = csvGenerator.generateReport(reportData);
        
        assertThat(report)
            .contains("Sales Report")
            .contains("Product,Amount,Quantity");
    }
    
    @Test
    @DisplayName("CSV: Should format data rows as CSV")
    void testCsvDataRows() {
        String report = csvGenerator.generateReport(reportData);
        
        assertThat(report)
            .contains("Widget,100.00,5")
            .contains("Gadget,250.50,3")
            .contains("Doohickey,75.75,10");
    }
    
    @Test
    @DisplayName("CSV: Should format footer with totals")
    void testCsvFooter() {
        String report = csvGenerator.generateReport(reportData);
        
        assertThat(report).contains("TOTAL,426.25,3");
    }
    
    @Test
    @DisplayName("CSV: Should filter out zero-amount rows")
    void testCsvFiltersZeroAmounts() {
        List<DataRow> rows = Arrays.asList(
            new DataRow("Item1", new BigDecimal("100.00"), 1),
            new DataRow("Item2", BigDecimal.ZERO, 0),
            new DataRow("Item3", new BigDecimal("50.00"), 2)
        );
        ReportData data = new ReportData("Test", rows, LocalDate.now());
        
        String report = csvGenerator.generateReport(data);
        
        assertThat(report)
            .contains("Item1,100.00,1")
            .contains("Item3,50.00,2")
            .doesNotContain("Item2");
    }
    
    @Test
    @DisplayName("CSV: Should filter out negative-amount rows")
    void testCsvFiltersNegativeAmounts() {
        List<DataRow> rows = Arrays.asList(
            new DataRow("Valid", new BigDecimal("100.00"), 1),
            new DataRow("Invalid", new BigDecimal("-50.00"), 1)
        );
        ReportData data = new ReportData("Test", rows, LocalDate.now());
        
        String report = csvGenerator.generateReport(data);
        
        assertThat(report)
            .contains("Valid,100.00,1")
            .doesNotContain("Invalid");
    }
    
    // Metrics Calculation Tests
    
    @Test
    @DisplayName("Should calculate total amount correctly")
    void testCalculateTotalAmount() {
        String report = pdfGenerator.generateReport(reportData);
        
        // 100.00 + 250.50 + 75.75 = 426.25
        assertThat(report).contains("426.25");
    }
    
    @Test
    @DisplayName("Should calculate total quantity correctly")
    void testCalculateTotalQuantity() {
        // 5 + 3 + 10 = 18 (but only item count (3) shown in reports)
        String report = pdfGenerator.generateReport(reportData);
        
        assertThat(report).contains("Items: 3");
    }
    
    @Test
    @DisplayName("Should handle single row report")
    void testSingleRowReport() {
        List<DataRow> rows = Arrays.asList(
            new DataRow("OnlyItem", new BigDecimal("99.99"), 1)
        );
        ReportData data = new ReportData("Single Item", rows, LocalDate.now());
        
        String report = pdfGenerator.generateReport(data);
        
        assertThat(report)
            .contains("OnlyItem")
            .contains("$99.99")
            .contains("Items: 1");
    }
    
    @Test
    @DisplayName("Should handle large amounts")
    void testLargeAmounts() {
        List<DataRow> rows = Arrays.asList(
            new DataRow("Expensive", new BigDecimal("9999.99"), 1)
        );
        ReportData data = new ReportData("Expensive Item", rows, LocalDate.now());
        
        String report = pdfGenerator.generateReport(data);
        
        assertThat(report).contains("9999.99");
    }
    
    // Template Method Flow Tests
    
    @Test
    @DisplayName("PDF: Should execute template method in correct order")
    void testPdfTemplateMethodFlow() {
        String report = pdfGenerator.generateReport(reportData);
        
        // Verify structure: header → rows → footer → signature
        int headerIndex = report.indexOf("=== Sales Report ===");
        int rowIndex = report.indexOf("Product: Widget");
        int footerIndex = report.indexOf("TOTAL:");
        int signatureIndex = report.indexOf("[DIGITALLY SIGNED]");
        
        assertThat(headerIndex).isLessThan(rowIndex);
        assertThat(rowIndex).isLessThan(footerIndex);
        assertThat(footerIndex).isLessThan(signatureIndex);
    }
    
    @Test
    @DisplayName("HTML: Should execute template method in correct order")
    void testHtmlTemplateMethodFlow() {
        String report = htmlGenerator.generateReport(reportData);
        
        // Verify structure: DOCTYPE → html → h1 → table → rows → close table → close html
        int doctypeIndex = report.indexOf("<!DOCTYPE html>");
        int h1Index = report.indexOf("<h1>");
        int tableOpenIndex = report.indexOf("<table");
        int firstRowIndex = report.indexOf("<tr>");
        int tableCloseIndex = report.indexOf("</table>");
        int htmlCloseIndex = report.indexOf("</html>");
        
        assertThat(doctypeIndex).isLessThan(h1Index);
        assertThat(h1Index).isLessThan(tableOpenIndex);
        assertThat(tableOpenIndex).isLessThan(firstRowIndex);
        assertThat(firstRowIndex).isLessThan(tableCloseIndex);
        assertThat(tableCloseIndex).isLessThan(htmlCloseIndex);
    }
    
    @Test
    @DisplayName("CSV: Should apply filters before formatting")
    void testCsvFilteringBeforeFormatting() {
        List<DataRow> rows = Arrays.asList(
            new DataRow("Valid1", new BigDecimal("100.00"), 1),
            new DataRow("Zero", BigDecimal.ZERO, 0),
            new DataRow("Valid2", new BigDecimal("50.00"), 1)
        );
        ReportData data = new ReportData("Test", rows, LocalDate.now());
        
        String report = csvGenerator.generateReport(data);
        
        // Only 2 valid items should appear
        assertThat(report).contains("TOTAL,150.00,2");
    }
    
    // Date Formatting Tests
    
    @Test
    @DisplayName("PDF: Should format date as MM/dd/yyyy")
    void testPdfDateFormat() {
        String report = pdfGenerator.generateReport(reportData);
        
        assertThat(report).contains("04/08/2026");
    }
    
    @Test
    @DisplayName("HTML: Should include date in metadata")
    void testHtmlDateInMetadata() {
        String report = htmlGenerator.generateReport(reportData);
        
        assertThat(report).contains("Generated: 2026-04-08");
    }
    
    // Hook Method Tests
    
    @Test
    @DisplayName("PDF: Should use afterFormatting hook")
    void testPdfAfterFormattingHook() {
        String report = pdfGenerator.generateReport(reportData);
        
        assertThat(report).contains("[DIGITALLY SIGNED]");
    }
    
    @Test
    @DisplayName("HTML: Should not use afterFormatting hook (default)")
    void testHtmlNoAfterFormattingHook() {
        String report = htmlGenerator.generateReport(reportData);
        
        assertThat(report).doesNotContain("[DIGITALLY SIGNED]");
    }
    
    @Test
    @DisplayName("CSV: Should use applyFilters hook")
    void testCsvApplyFiltersHook() {
        List<DataRow> rows = Arrays.asList(
            new DataRow("Valid", new BigDecimal("100.00"), 1),
            new DataRow("Invalid", BigDecimal.ZERO, 0)
        );
        ReportData data = new ReportData("Test", rows, LocalDate.now());
        
        String report = csvGenerator.generateReport(data);
        
        assertThat(report)
            .contains("Valid")
            .doesNotContain("Invalid");
    }
    
    @Test
    @DisplayName("PDF: Should not filter rows (default applyFilters)")
    void testPdfNoFiltering() {
        List<DataRow> rows = Arrays.asList(
            new DataRow("Item", BigDecimal.ZERO, 0)
        );
        ReportData data = new ReportData("Test", rows, LocalDate.now());
        
        String report = pdfGenerator.generateReport(data);
        
        assertThat(report).contains("Item"); // Not filtered
    }
    
    // Decimal Precision Tests
    
    @Test
    @DisplayName("Should format amounts with exactly 2 decimal places")
    void testDecimalPrecision() {
        List<DataRow> rows = Arrays.asList(
            new DataRow("Item1", new BigDecimal("10"), 1),
            new DataRow("Item2", new BigDecimal("20.5"), 1),
            new DataRow("Item3", new BigDecimal("30.123"), 1)
        );
        ReportData data = new ReportData("Test", rows, LocalDate.now());
        
        String pdfReport = pdfGenerator.generateReport(data);
        
        assertThat(pdfReport)
            .contains("$10.00")
            .contains("$20.50")
            .contains("$30.12"); // Rounded
    }
}
