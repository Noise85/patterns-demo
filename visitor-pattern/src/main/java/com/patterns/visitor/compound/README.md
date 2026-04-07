# Compound Package - Visitor Composition

This package demonstrates **visitor composition patterns**, specifically the **FilteringVisitor** pattern that combines filtering logic with any target visitor operation.

## Pattern Approach

Unlike the `isolation` package which shows basic visitor implementation, this package demonstrates:

1. **Filtering Visitor**: Applies a visitor only to elements matching criteria
2. **Visitor Composition**: Combining multiple visitors for complex operations
3. **Decorator Pattern**: Wrapping visitors to add filtering behavior

## Key Components

### Base Components (Shared with Isolation)
- `FileSystemElement`, `File`, `Directory` - Same composite structure
- `FileSystemVisitor` - Same visitor interface
- `SizeCalculatorVisitor` - Reuse from isolation

### Compound Components (New)
- `FilteringVisitor` - Filters elements before applying target visitor
- `CompositeVisitor` - Applies multiple visitors simultaneously

## How It Works

### FilteringVisitor Architecture
```
┌─────────────────────────────────────────┐
│      FilteringVisitor                   │
│  (Wraps another visitor)                │
├─────────────────────────────────────────┤
│  - searchPattern: String                │
│  - extension: String                    │
│  - targetVisitor: FileSystemVisitor ◄───┼── Any visitor!
│  - matchedFiles: List<File>             │
├─────────────────────────────────────────┤
│  visit(File)                            │
│    ├─ Check if matches filter           │
│    ├─ If yes: record + delegate         │
│    └─ If no: skip                       │
│                                         │
│  visit(Directory)                       │
│    └─ Recurse through children          │
└─────────────────────────────────────────┘
```

## Usage Examples

### Example 1: Calculate Size of Filtered Files
```java
// Find all PDF reports and calculate their total size
SizeCalculatorVisitor sizeVisitor = new SizeCalculatorVisitor();
FilteringVisitor filter = new FilteringVisitor("report", "pdf", sizeVisitor);

root.accept(filter);

System.out.println("Found: " + filter.getMatchCount() + " files");
System.out.println("Total size: " + sizeVisitor.getTotalSize() + " bytes");
```

### Example 2: Multiple Operations on Same Filter
```java
// Process all .log files with multiple operations
SizeCalculatorVisitor sizeCalc = new SizeCalculatorVisitor();
FileCounterVisitor counter = new FileCounterVisitor();

CompositeVisitor multiOp = new CompositeVisitor(sizeCalc, counter);
FilteringVisitor filter = new FilteringVisitor(null, "log", multiOp);

root.accept(filter);
```

### Example 3: Different Filters, Different Operations
```java
// PDF size calculation
FilteringVisitor pdfSizer = new FilteringVisitor(null, "pdf", new SizeCalculatorVisitor());

// TXT file search
FilteringVisitor txtSearch = new FilteringVisitor("readme", "txt", new PrintVisitor());

root.accept(pdfSizer);
root.accept(txtSearch);
```

## Benefits Over Basic Visitor

1. **Single Pass Efficiency**: Filter and process in one tree traversal
2. **Reusability**: Any visitor can be wrapped with filtering
3. **Composition**: Stack filters or combine multiple visitors
4. **Separation of Concerns**: Filter logic separate from operation logic
5. **Flexibility**: Change filter or operation independently

## Pattern Combination

This demonstrates three patterns working together:

- **Visitor**: Separates operations from structure
- **Composite**: Hierarchical file system structure
- **Decorator**: FilteringVisitor wraps other visitors

## When to Use

✅ Use compound visitors when:
- You need to apply operations only to filtered subsets
- Single-pass traversal is important for performance
- Filter criteria are reusable across operations
- You want composable, flexible visitor pipelines

❌ Stick with basic visitors when:
- Simple, straightforward operations on all elements
- Filter is one-time use
- Code simplicity is more important than flexibility
