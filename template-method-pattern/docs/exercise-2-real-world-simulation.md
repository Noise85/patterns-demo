# Exercise 2: Real-World Simulation - Report Generation System

## Objective

Build a production-grade **report generation system** that creates different report formats (PDF, HTML, CSV) using the Template Method pattern. This simulates a real business reporting system with complex formatting requirements.

## Business Context

You're developing an analytics platform that generates reports for business users. Reports can be exported in multiple formats, but all follow the same generation process:

1. **Gather Data** - Query from database/API
2. **Apply Filters** - Filter based on criteria
3. **Calculate Metrics** - Compute aggregations
4. **Format Header** - Add title, date, metadata
5. **Format Data Rows** - Format each data row
6. **Format Footer** - Add summary, page numbers
7. **Export** - Save to file/send to output

The structure is identical, but formatting differs dramatically between PDF, HTML, and CSV.

## Requirements

### Report Types

#### 1. PDF Report
- Header: Title with centered formatting, date in MM/dd/yyyy
- Data rows: Column-aligned with separators
- Footer: Summary row with totals, page number
- Export format: Binary placeholder (simulated)

#### 2. HTML Report
- Header: `<h1>` title, `<p>` metadata with CSS classes
- Data rows: `<table>` with `<tr>` and `<td>` tags
- Footer: `<div class="summary">` with totals
- Export format: Valid HTML document

#### 3. CSV Report  
- Header: Title row, metadata row (optional)
- Data rows: Comma-separated values
- Footer: Totals row
- Export format: Plain text CSV

### Data Model

```java
public class ReportData {
    private final String title;
    private final List<DataRow> rows;
    private final LocalDate reportDate;
    
    public static class DataRow {
        private final String product;
        private final BigDecimal amount;
        private final int quantity;
        // getters...
    }
}
```

### Abstract Base Class

```java
public abstract class ReportGenerator {
    
    protected final List<String> output = new ArrayList<>();
    
    /**
     * Template method - orchestrates report generation.
     * Final to ensure consistent report structure.
     */
    public final String generateReport(ReportData data) {
        // 1. Apply filters (if needed)
        List<DataRow> filteredRows = applyFilters(data.getRows());
        
        // 2. Calculate metrics
        ReportMetrics metrics = calculateMetrics(filteredRows);
        
        // 3. Build report
        output.clear();
        formatHeader(data.getTitle(), data.getReportDate());
        
        for (DataRow row : filteredRows) {
            formatDataRow(row);
        }
        
        formatFooter(metrics);
        
        // 4. Post-processing hook
        afterFormatting();
        
        // 5. Export
        return export();
    }
    
    // Primitive operations - must implement
    protected abstract void formatHeader(String title, LocalDate date);
    protected abstract void formatDataRow(DataRow row);
    protected abstract void formatFooter(ReportMetrics metrics);
    protected abstract String export();
    
    // Hook methods - optional customization
    protected List<DataRow> applyFilters(List<DataRow> rows) {
        return rows;  // No filtering by default
    }
    
    protected void afterFormatting() {
        // Optional post-processing
    }
    
    // Common operation - shared logic
    protected ReportMetrics calculateMetrics(List<DataRow> rows) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        int totalQuantity = 0;
        
        for (DataRow row : rows) {
            totalAmount = totalAmount.add(row.getAmount());
            totalQuantity += row.getQuantity();
        }
        
        return new ReportMetrics(totalAmount, totalQuantity, rows.size());
    }
}
```

## Implementation Tasks

### Task 1: Implement PdfReportGenerator

```java
public class PdfReportGenerator extends ReportGenerator {
    
    @Override
    protected void formatHeader(String title, LocalDate date) {
        // TODO: Format PDF header
        // Add: "=== " + title + " ==="
        // Add: "Date: " + date (formatted as MM/dd/yyyy)
        // Add: "---"
    }
    
    @Override
    protected void formatDataRow(DataRow row) {
        // TODO: Format as column-aligned data
        // Format: "Product: <product> | Amount: $<amount> | Qty: <quantity>"
        // Amount formatted as currency with 2 decimals
    }
    
    @Override
    protected void formatFooter(ReportMetrics metrics) {
        // TODO: Format PDF footer
        // Add: "---"
        // Add: "TOTAL: $<totalAmount> | Items: <itemCount>"
        // Add: "Page 1 of 1"
    }
    
    @Override
    protected String export() {
        // TODO: Join output lines with newlines
        // In real system, this would generate binary PDF
    }
    
    @Override
    protected void afterFormatting() {
        // TODO: Add digital signature line
        // Add: "[DIGITALLY SIGNED]"
    }
}
```

### Task 2: Implement HtmlReportGenerator

```java
public class HtmlReportGenerator extends ReportGenerator {
    
    @Override
    protected void formatHeader(String title, LocalDate date) {
        // TODO: Format HTML header
        // Add: "<!DOCTYPE html>"
        // Add: "<html><body>"
        // Add: "<h1>" + title + "</h1>"
        // Add: "<p class='metadata'>Generated: " + date + "</p>"
        // Add: "<table border='1'>"
    }
    
    @Override
    protected void formatDataRow(DataRow row) {
        // TODO: Format as HTML table row
        // Add: "<tr>"
        // Add: "  <td>" + product + "</td>"
        // Add: "  <td>$" + amount + "</td>"
        // Add: "  <td>" + quantity + "</td>"
        // Add: "</tr>"
    }
    
    @Override
    protected void formatFooter(ReportMetrics metrics) {
        // TODO: Format HTML footer
        // Add: "</table>"
        // Add: "<div class='summary'>Total: $" + totalAmount + "</div>"
        // Add: "</body></html>"
    }
    
    @Override
    protected String export() {
        // TODO: Join output lines with newlines
    }
}
```

### Task 3: Implement CsvReportGenerator

```java
public class CsvReportGenerator extends ReportGenerator {
    
    @Override
    protected void formatHeader(String title, LocalDate date) {
        // TODO: Format CSV header
        // Add: title
        // Add: "Product,Amount,Quantity"
    }
    
    @Override
    protected void formatDataRow(DataRow row) {
        // TODO: Format as CSV row
        // Add: product + "," + amount + "," + quantity
    }
    
    @Override
    protected void formatFooter(ReportMetrics metrics) {
        // TODO: Format CSV footer
        // Add: "TOTAL," + totalAmount + "," + itemCount
    }
    
    @Override
    protected String export() {
        // TODO: Join output lines with newlines
    }
    
    @Override
    protected List<DataRow> applyFilters(List<DataRow> rows) {
        // TODO: Filter out rows with zero amount
        // Return only rows where amount > 0
    }
}
```

## Advanced Requirements

### 1. Report Metrics

Create a helper class to hold calculated metrics:

```java
public class ReportMetrics {
    private final BigDecimal totalAmount;
    private final int totalQuantity;
    private final int itemCount;
    
    // constructor, getters
}
```

### 2. Filtering (CSV only)

- CSV reports should filter out rows with zero or negative amounts
- Override `applyFilters()` hook in `CsvReportGenerator`
- Other formats include all rows

### 3. Post-Processing (PDF only)

- PDF reports should add a digital signature line
- Override `afterFormatting()` hook in `PdfReportGenerator`
- Other formats don't need post-processing

### 4. Currency Formatting

- All amounts should be formatted with 2 decimal places
- Use `BigDecimal.setScale(2, RoundingMode.HALF_UP)`
- Prefix with `$` sign

### 5. Date Formatting

- PDF: `MM/dd/yyyy` format
- HTML/CSV: ISO format (`toString()`) is acceptable
- Use `DateTimeFormatter` for custom formats

## Expected Output Examples

### PDF Report
```
=== Sales Report ===
Date: 04/08/2026
---
Product: Widget | Amount: $100.00 | Qty: 5
Product: Gadget | Amount: $250.50 | Qty: 3
---
TOTAL: $350.50 | Items: 2
Page 1 of 1
[DIGITALLY SIGNED]
```

### HTML Report
```html
<!DOCTYPE html>
<html><body>
<h1>Sales Report</h1>
<p class='metadata'>Generated: 2026-04-08</p>
<table border='1'>
<tr>
  <td>Widget</td>
  <td>$100.00</td>
  <td>5</td>
</tr>
<tr>
  <td>Gadget</td>
  <td>$250.50</td>
  <td>3</td>
</tr>
</table>
<div class='summary'>Total: $350.50</div>
</body></html>
```

### CSV Report
```
Sales Report
Product,Amount,Quantity
Widget,100.00,5
Gadget,250.50,3
TOTAL,350.50,2
```

## Testing Strategy

### Unit Tests

1. **Test each generator independently**: Verify output format
2. **Test template method flow**: Ensure steps execute in order
3. **Test hook methods**: Verify filtering, post-processing
4. **Test metrics calculation**: Verify totals are accurate
5. **Test edge cases**: Empty reports, single row, large datasets

### Integration Tests

1. **Compare outputs**: Verify data consistency across formats
2. **Test with real data**: Use realistic business data
3. **Performance tests**: Generate reports with 1000+ rows

## Success Criteria

- [ ] PDF report formats with proper alignment and structure
- [ ] HTML report generates valid HTML with table structure
- [ ] CSV report creates valid comma-separated values
- [ ] All formats calculate metrics correctly
- [ ] CSV filters out zero-amount rows
- [ ] PDF includes digital signature
- [ ] Currency amounts formatted with 2 decimals
- [ ] Template method enforces consistent flow
- [ ] All 25+ tests pass

## Design Insights

### Why Template Method Here?

1. **Common Structure**: All reports follow same generation flow
2. **Different Formats**: Output format varies drastically
3. **Extensibility**: Easy to add new report types (XML, Excel)
4. **Consistency**: Ensures all reports calculate metrics the same way
5. **Maintainability**: Change report flow in one place

### Alternative Approaches

- **Strategy Pattern**: Would require duplicating the generation flow
- **Visitor Pattern**: Overkill for simple formatting
- **Factory Pattern**: Only handles creation, not the algorithm structure

Template Method is the right choice when you have a **fixed algorithm** with **variable steps**.

## Bonus Challenges

1. **Add Excel Report**: Implement `.xlsx` format with formulas
2. **Pagination**: Split large reports into multiple pages
3. **Sorting**: Add hook for custom row sorting
4. **Grouping**: Group rows by product category
5. **Async Export**: Make `export()` asynchronous with callbacks

Good luck building a professional business reporting system! 📊
