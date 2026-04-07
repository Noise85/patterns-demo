# Solution Notes: Template Method Pattern

## Overview

These notes provide guidance on implementing the Template Method pattern exercises **without revealing complete solutions**. Use them to understand the approach and verify your implementation.

---

## Exercise 1: Data Processing Pipeline

### Key Implementation Points

#### Template Method Structure
```java
public final List<String> process(String input) {
    List<String> records = parse(input);          // Step 1: Primitive operation
    validate(records);                             // Step 2: Common operation
    if (shouldTransform()) {                       // Step 3: Hook (conditional)
        records = transform(records);
    }
    save(records);                                 // Step 4: Primitive operation
    afterProcessing(records);                      // Step 5: Hook (optional)
    return records;
}
```

**Why final?** Prevents subclasses from changing the algorithm flow. The structure must remain consistent.

### CsvDataProcessor Implementation Strategy

1. **parse()**: Use `String.split("\n")` and trim each element
2. **transform()**: Use `String.toUpperCase()` on each record
3. **save()**: Simple `database.add()` for each record
4. **afterProcessing()**: Format log message with record count

### JsonDataProcessor Implementation Strategy

1. **parse()**: 
   - Remove brackets with `substring()` or `replace()`
   - Split by comma
   - Remove quotes with `replace("\"", "")`
   - Trim whitespace
2. **transform()**: Prepend "JSON:" to each record
3. **save()**: Same as CSV - add to database
4. **shouldTransform()**: Return `false` to skip transformation step

### Common Mistakes to Avoid

❌ **Making template method non-final**
```java
public List<String> process(String input) { }  // BAD
```

✅ **Correct approach**
```java
public final List<String> process(String input) { }  // GOOD
```

❌ **Implementing validate() in subclasses**
```java
@Override
protected void validate(List<String> records) { }  // BAD - it's common logic
```

✅ **Leave validate() in base class**
- It's shared by all processors
- Implemented once in `DataProcessor`

❌ **Making hooks abstract**
```java
protected abstract void afterProcessing(List<String> records);  // BAD
```

✅ **Hooks should have default implementations**
```java
protected void afterProcessing(List<String> records) { }  // GOOD
```

### Testing Approach

Test individual steps:
```java
CsvDataProcessor processor = new CsvDataProcessor(...);

// Test parsing
List<String> parsed = processor.parse("line1\nline2");
assertThat(parsed).containsExactly("line1", "line2");

// Test transformation
List<String> transformed = processor.transform(Arrays.asList("a", "b"));
assertThat(transformed).containsExactly("A", "B");
```

Test template method flow:
```java
List<String> result = processor.process("data1\ndata2");

// Verify final state
assertThat(database).contains("DATA1", "DATA2");
assertThat(log).contains("Processed 2 CSV records");
```

---

## Exercise 2: Report Generation System

### Architecture Overview

```
ReportGenerator (abstract)
    ├── calculateMetrics() [common operation]
    ├── applyFilters() [hook - default returns all rows]
    ├── formatHeader() [primitive operation]
    ├── formatDataRow() [primitive operation]
    ├── formatFooter() [primitive operation]
    ├── afterFormatting() [hook - default does nothing]
    └── export() [primitive operation]
```

### Implementation Strategy by Report Type

#### PDF Report
- **Header**: Use decorative separators (`===`, `---`)
- **Data Rows**: Column-aligned format with `|` separators
- **Footer**: Summary line + page number
- **Hook Usage**: Override `afterFormatting()` for digital signature
- **Key Challenge**: Formatting currency with proper alignment

#### HTML Report
- **Header**: Standard HTML5 structure with semantic tags
- **Data Rows**: Table cells (`<td>`) with proper escaping
- **Footer**: Close table, add summary `<div>`
- **Hook Usage**: Use defaults (no filtering, no post-processing)
- **Key Challenge**: Valid HTML structure

#### CSV Report
- **Header**: Simple title + column headers
- **Data Rows**: Comma-separated values
- **Footer**: Totals row
- **Hook Usage**: Override `applyFilters()` to remove zero-amount rows
- **Key Challenge**: Proper CSV formatting (no extra spaces)

### ReportMetrics Helper Class

```java
public class ReportMetrics {
    private final BigDecimal totalAmount;
    private final int totalQuantity;
    private final int itemCount;
    
    public ReportMetrics(BigDecimal totalAmount, int totalQuantity, int itemCount) {
        this.totalAmount = totalAmount;
        this.totalQuantity = totalQuantity;
        this.itemCount = itemCount;
    }
    
    // Getters for all fields
}
```

### Currency Formatting

Use BigDecimal properly:
```java
BigDecimal amount = row.getAmount().setScale(2, RoundingMode.HALF_UP);
String formatted = String.format("$%.2f", amount);
```

### Date Formatting

For PDF (MM/dd/yyyy):
```java
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
String formatted = date.format(formatter);
```

### Filtering Logic (CSV only)

```java
@Override
protected List<DataRow> applyFilters(List<DataRow> rows) {
    // Filter rows where amount > 0
    // Use Stream API or traditional loop
    // Return new list with filtered rows
}
```

### Common Pitfalls

❌ **Modifying the template method flow**
- Don't change the order of operations
- Don't add new steps in subclasses

❌ **Duplicating calculateMetrics() logic**
- This is common operation - leave in base class
- All formats calculate metrics the same way

❌ **Making hooks mandatory**
- `afterFormatting()` and `applyFilters()` are optional
- Provide sensible defaults

❌ **Forgetting to clear output list**
- Template method clears `output` at start
- Each generation starts fresh

### Testing Strategy

#### Test Template Method Flow
```java
@Test
void testReportGenerationFlow() {
    // Create test data
    ReportData data = ...;
    
    // Generate report
    String result = generator.generateReport(data);
    
    // Verify structure (not exact content)
    assertThat(result).contains("Sales Report");
    assertThat(result).contains("Widget");
    assertThat(result).contains("250.50");
}
```

#### Test Metrics Calculation
```java
@Test
void testMetricsCalculation() {
    ReportGenerator generator = new TestableReportGenerator();
    
    ReportMetrics metrics = generator.calculateMetrics(testRows);
    
    assertThat(metrics.getTotalAmount()).isEqualByComparingTo("350.50");
    assertThat(metrics.getItemCount()).isEqualTo(2);
}
```

#### Test Filtering (CSV)
```java
@Test
void testCsvFiltersZeroAmounts() {
    CsvReportGenerator csv = new CsvReportGenerator();
    
    List<DataRow> rows = Arrays.asList(
        new DataRow("A", new BigDecimal("100.00"), 1),
        new DataRow("B", BigDecimal.ZERO, 0),  // Should be filtered
        new DataRow("C", new BigDecimal("50.00"), 2)
    );
    
    List<DataRow> filtered = csv.applyFilters(rows);
    
    assertThat(filtered).hasSize(2);
}
```

---

## Design Patterns Insights

### Template Method vs Strategy

**Template Method** (this exercise):
```java
public abstract class ReportGenerator {
    public final String generate() {  // Fixed algorithm
        formatHeader();
        formatData();
        formatFooter();
    }
}
```

**Strategy** (alternative):
```java
public class ReportGenerator {
    private FormattingStrategy strategy;
    
    public String generate() {
        return strategy.format(data);  // Entire algorithm replaceable
    }
}
```

Use Template Method when:
- Algorithm structure is invariant
- Multiple steps need customization
- Subclasses specialize specific steps

Use Strategy when:
- Entire algorithm varies
- Need runtime selection
- Favor composition over inheritance

### Hollywood Principle

"Don't call us, we'll call you"

```java
// Base class controls when to call subclass methods
public final String generateReport(ReportData data) {
    // Base class: "I'll call your formatHeader when I'm ready"
    formatHeader(data.getTitle(), data.getReportDate());
    
    // Base class: "I'll call your formatDataRow for each row"
    for (DataRow row : rows) {
        formatDataRow(row);
    }
}
```

Subclasses don't control the flow - they're called by the framework.

### When to Use Abstract vs Hook Methods

**Abstract methods** (required):
```java
protected abstract void formatHeader(String title, LocalDate date);
```
- Core operations that **must** be implemented
- No sensible default possible
- Different for every subclass

**Hook methods** (optional):
```java
protected void afterFormatting() { }
```
- Optional customization points
- Sensible default exists (often empty)
- Most subclasses use default

---

## Extension Ideas

### Adding New Report Types

To add XML report:

1. Extend `ReportGenerator`
2. Implement primitive operations (`formatHeader`, `formatDataRow`, `formatFooter`, `export`)
3. Optionally override hooks if needed
4. **No changes to base class required**

### Adding New Template Steps

If you need to add a new step to **all** reports:

1. Add step to template method in base class
2. Either provide concrete implementation or make it abstract
3. All subclasses automatically get the new step

### Customizing Metric Calculation

If one report type needs different metrics:

```java
@Override
protected ReportMetrics calculateMetrics(List<DataRow> rows) {
    // Call super for base calculation
    ReportMetrics base = super.calculateMetrics(rows);
    
    // Add custom metrics
    // ...
    
    return customMetrics;
}
```

---

## Key Takeaways

1. **Template method is final** - prevents changing algorithm structure
2. **Primitive operations** define customization points
3. **Hooks provide flexibility** without forcing implementation
4. **Common operations** eliminate duplication
5. **Inheritance-based** - subclasses specialize behavior
6. **Hollywood Principle** - framework controls the flow
7. **Best for fixed algorithms** with variable implementations

---

## Debugging Tips

### If tests fail:

1. **Check method signatures** - abstract methods must match exactly
2. **Verify template method calls** - are all steps being executed?
3. **Test individual operations** - test each primitive operation in isolation
4. **Check hook behavior** - are hooks being called when expected?
5. **Verify output accumulation** - is the output list being built correctly?

### Common Issues

**Problem**: Validation always throws exception
- **Solution**: Check that `parse()` returns non-null, non-empty list

**Problem**: Transformation not applied
- **Solution**: Verify `shouldTransform()` returns true

**Problem**: Database/output empty
- **Solution**: Ensure `save()`/`formatX()` adds to lists

**Problem**: Wrong order of operations
- **Solution**: Review template method - flow is defined there

---

Remember: The beauty of Template Method is that the **algorithm is defined once** in the base class, and **subclasses only customize the pieces that vary**. Focus on identifying what changes vs what stays the same.

Good luck! 🎯
