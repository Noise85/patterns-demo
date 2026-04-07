# Exercise 1: Template Method Pattern in Isolation

## Objective

Learn the Template Method pattern by implementing a simple **data processing pipeline** where the overall structure is fixed but specific steps vary by data format.

## Scenario

You're building a data import system that processes files from different sources. All data goes through the same pipeline:

1. **Parse** - Read and parse the raw data
2. **Validate** - Check data meets requirements
3. **Transform** - Convert to internal format
4. **Save** - Persist to storage

The steps are always the same, but implementation differs for CSV vs JSON files.

## Class Structure

### Abstract Base Class: `DataProcessor`

Defines the template method and common structure:

```java
public abstract class DataProcessor {
    
    /**
     * Template method - defines the processing algorithm.
     * Final to prevent subclasses from changing the flow.
     */
    public final List<String> process(String input) {
        // 1. Parse
        List<String> records = parse(input);
        
        // 2. Validate
        validate(records);
        
        // 3. Transform (optional)
        if (shouldTransform()) {
            records = transform(records);
        }
        
        // 4. Save
        save(records);
        
        // 5. Hook for post-processing
        afterProcessing(records);
        
        return records;
    }
    
    // Primitive operations - must implement
    protected abstract List<String> parse(String input);
    protected abstract List<String> transform(List<String> records);
    protected abstract void save(List<String> records);
    
    // Hook methods - can override
    protected boolean shouldTransform() { return true; }
    protected void afterProcessing(List<String> records) { }
    
    // Common operation - shared by all subclasses
    protected void validate(List<String> records) {
        if (records == null || records.isEmpty()) {
            throw new IllegalArgumentException("No records to process");
        }
        for (String record : records) {
            if (record == null || record.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid record");
            }
        }
    }
}
```

### Concrete Subclass: `CsvDataProcessor`

Processes CSV format data:

```java
public class CsvDataProcessor extends DataProcessor {
    
    @Override
    protected List<String> parse(String input) {
        // TODO: Split CSV input by newlines
        // Return list of trimmed records
    }
    
    @Override
    protected List<String> transform(List<String> records) {
        // TODO: Convert CSV records to uppercase
        // Return transformed list
    }
    
    @Override
    protected void save(List<String> records) {
        // TODO: Store each record in the database list
        // Use: database.add(record)
    }
    
    @Override
    protected void afterProcessing(List<String> records) {
        // TODO: Log processing completion
        // Use: log.add("Processed " + records.size() + " CSV records")
    }
}
```

### Concrete Subclass: `JsonDataProcessor`

Processes JSON format data:

```java
public class JsonDataProcessor extends DataProcessor {
    
    @Override
    protected List<String> parse(String input) {
        // TODO: Extract values from JSON array format: ["value1","value2"]
        // Remove brackets, split by comma, trim quotes and spaces
    }
    
    @Override
    protected List<String> transform(List<String> records) {
        // TODO: Add "JSON:" prefix to each record
        // Return transformed list
    }
    
    @Override
    protected void save(List<String> records) {
        // TODO: Store each record in the database list
        // Use: database.add(record)
    }
    
    @Override
    protected boolean shouldTransform() {
        // TODO: Return false - JSON doesn't need transformation
    }
}
```

## Requirements

### Part 1: Implement CsvDataProcessor

1. **parse()**: Split input by newlines (`\n`), trim each line
2. **transform()**: Convert each record to UPPERCASE
3. **save()**: Add each record to `database` list
4. **afterProcessing()**: Add log message: `"Processed N CSV records"`

### Part 2: Implement JsonDataProcessor

1. **parse()**: Extract values from JSON array format
   - Input: `["value1","value2"]`
   - Remove `[` and `]`
   - Split by `,`
   - Remove quotes and trim
2. **transform()**: Prepend `"JSON:"` to each record
3. **save()**: Add each record to `database` list
4. **shouldTransform()**: Override to return `false` (skip transformation)

### Part 3: Understanding Template Method

Answer these questions (in comments or separately):

1. Why is `process()` marked as `final`?
2. What would happen if a subclass tried to override `process()`?
3. What's the difference between `parse()` (abstract) and `afterProcessing()` (hook)?
4. When would you override `shouldTransform()`?
5. Why is `validate()` implemented in the base class instead of being abstract?

## Key Concepts

### Template Method
- **`process()`** is the template method
- Defines the algorithm skeleton
- **Final** to prevent changing the structure

### Primitive Operations
- **`parse()`**, **`transform()`**, **`save()`** are primitive operations
- **Abstract** - must be implemented by subclasses
- Each subclass provides its own implementation

### Hook Methods
- **`shouldTransform()`**, **`afterProcessing()`** are hooks
- **Optional** - have default implementations
- Subclasses override only if needed

### Common Operations
- **`validate()`** is a common operation
- **Concrete** implementation in base class
- Shared by all subclasses

## Expected Flow

### For CSV Input: `"record1\nrecord2\nrecord3"`

1. `parse()` → `["record1", "record2", "record3"]`
2. `validate()` → checks not empty, no null records
3. `shouldTransform()` → returns `true` (default)
4. `transform()` → `["RECORD1", "RECORD2", "RECORD3"]`
5. `save()` → adds to database: `["RECORD1", "RECORD2", "RECORD3"]`
6. `afterProcessing()` → logs: `"Processed 3 CSV records"`

### For JSON Input: `["item1","item2"]`

1. `parse()` → `["item1", "item2"]`
2. `validate()` → checks not empty, no null records
3. `shouldTransform()` → returns `false` (overridden)
4. `transform()` → **SKIPPED**
5. `save()` → adds to database: `["item1", "item2"]`
6. `afterProcessing()` → **NOT OVERRIDDEN** (does nothing)

## Testing Approach

Tests verify:
- ✅ Template method executes all steps in correct order
- ✅ Each step produces expected output
- ✅ Hook methods are optional
- ✅ Validation catches invalid data
- ✅ Different processors handle their formats correctly

## Success Criteria

- [ ] `CsvDataProcessor` correctly parses CSV format
- [ ] CSV records are transformed to uppercase
- [ ] CSV processing logs completion message
- [ ] `JsonDataProcessor` correctly parses JSON array format
- [ ] JSON records are **not** transformed (hook returns false)
- [ ] Both processors save to database correctly
- [ ] Validation throws exception for empty/null records
- [ ] All tests pass

## Tips

1. **Focus on the template method** - understand how it orchestrates the steps
2. **Primitive operations** are where customization happens
3. **Hook methods** provide flexibility without forcing implementation
4. **Common operations** eliminate code duplication
5. The **algorithm structure** is fixed, but **implementations** vary

Good luck! This exercise demonstrates the core Template Method pattern in its simplest form. 🎯
