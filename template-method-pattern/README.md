# Template Method Pattern

## Intent

The **Template Method** pattern defines the skeleton of an algorithm in a base class, letting subclasses override specific steps without changing the algorithm's structure.

## Problem

Many algorithms follow the same overall structure but differ in specific implementation details:

- **Data processing**: Parse → Validate → Transform → Save (same steps, different formats)
- **Report generation**: Gather data → Format → Add headers/footers → Export (same flow, different outputs)
- **Testing frameworks**: Setup → Execute → Teardown (same structure, different tests)
- **Web page rendering**: Load template → Fill content → Apply styles → Send (same sequence, different pages)

Without the Template Method pattern, you end up with:
- **Code duplication**: The same algorithm structure copied in multiple classes
- **Maintenance burden**: Changes to algorithm flow require updating every copy
- **Inconsistency**: Easy to miss steps or implement them in different orders
- **No enforcement**: Nothing prevents subclasses from breaking the intended flow

```java
// Without Template Method - code duplication
public class CSVProcessor {
    public void process() {
        String data = parseCSV();
        validate(data);
        String result = transformCSV(data);
        saveToDatabase(result);
    }
}

public class JSONProcessor {
    public void process() {
        String data = parseJSON();  // Same structure
        validate(data);              // duplicated everywhere
        String result = transformJSON(data);
        saveToDatabase(result);
    }
}
```

## Solution

The Template Method pattern suggests:

1. **Define algorithm skeleton** in an abstract base class
2. **Make template method final** so subclasses can't override the structure
3. **Declare primitive operations** as abstract methods for subclasses to implement
4. **Provide hook methods** with default (often empty) implementations for optional steps
5. **Subclasses override** only the specific steps they need to customize

```java
public abstract class DataProcessor {
    
    // Template method - defines algorithm skeleton (final)
    public final void process() {
        String data = parse();
        validate(data);
        if (shouldTransform()) {  // Hook method
            data = transform(data);
        }
        save(data);
        afterProcessing();  // Hook method
    }
    
    // Primitive operations - must be implemented
    protected abstract String parse();
    protected abstract String transform(String data);
    protected abstract void save(String data);
    
    // Hook methods - optional customization points
    protected boolean shouldTransform() { return true; }
    protected void afterProcessing() { }
    
    // Common implementation
    protected void validate(String data) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Invalid data");
        }
    }
}
```

## Structure

```
┌─────────────────────────────────────┐
│      AbstractClass                  │
├─────────────────────────────────────┤
│ + templateMethod() [final]          │ ◄── Defines algorithm
│ # primitiveOperation1() [abstract]  │ ◄── Steps to override
│ # primitiveOperation2() [abstract]  │
│ # hook() [default impl]             │ ◄── Optional steps
└─────────────────────────────────────┘
                 △
                 │ extends
      ┌──────────┴──────────┐
      │                     │
┌─────┴──────────┐  ┌──────┴─────────┐
│ ConcreteClass1 │  │ ConcreteClass2 │
├────────────────┤  ├────────────────┤
│ + primitive1() │  │ + primitive1() │
│ + primitive2() │  │ + primitive2() │
│ + hook()       │  └────────────────┘
└────────────────┘
```

## Key Characteristics

### When to Use
- ✅ Multiple classes share the same algorithm structure
- ✅ Algorithm has well-defined invariant steps
- ✅ Need to control which parts subclasses can customize
- ✅ Want to avoid code duplication across similar algorithms
- ✅ Need to enforce a specific execution order

### When NOT to Use
- ❌ Algorithm steps vary wildly between implementations
- ❌ No common structure across subclasses
- ❌ Subclasses need to change the algorithm flow itself
- ❌ Only using inheritance for code reuse (prefer composition)

## Method Types

### 1. Template Method (final)
```java
public final void processOrder() {
    validateOrder();
    calculateTotal();
    applyDiscounts();
    processPayment();
    sendConfirmation();
}
```
- Defines algorithm skeleton
- **Final** - prevents subclasses from changing structure
- Calls primitive operations and hooks

### 2. Primitive Operations (abstract)
```java
protected abstract BigDecimal calculateTotal();
protected abstract void processPayment();
```
- **Must** be implemented by subclasses
- Core steps that vary between implementations

### 3. Hook Methods (optional)
```java
protected void beforePayment() { }  // Empty default
protected boolean shouldSendEmail() { return true; }  // Logical default
```
- **Optional** customization points
- Default implementation provided
- Subclasses override only if needed

### 4. Common Operations (concrete)
```java
protected void validateOrder() {
    if (order.getItems().isEmpty()) {
        throw new IllegalStateException("Empty order");
    }
}
```
- Shared implementation for all subclasses
- Cannot be overridden (or marked final if can't)

## Template Method vs Strategy

| Template Method | Strategy |
|----------------|----------|
| **Inheritance-based** | **Composition-based** |
| Subclasses override steps | Client passes strategy object |
| Fixed algorithm structure | Entire algorithm replaceable |
| Abstract base class | Interface for strategies |
| Multiple methods (steps) | Single method (algorithm) |
| Compile-time binding | Runtime binding |

Example:
- **Template Method**: Different report types (PDF/HTML) share formatting flow
- **Strategy**: Different sorting algorithms (QuickSort/MergeSort) - entirely different

## Template Method vs Factory Method

| Template Method | Factory Method |
|----------------|----------------|
| Orchestrates **algorithm flow** | Creates **objects** |
| Multiple steps, some customizable | Single creation step |
| Focuses on **behavior** | Focuses on **instantiation** |

They often work together:
```java
public abstract class ReportGenerator {
    public final Report generate() {
        Report report = createReport();  // Factory Method
        addContent(report);              // Template Method continues
        format(report);
        return report;
    }
    
    protected abstract Report createReport();  // Factory Method
    protected abstract void addContent(Report report);
}
```

## Hollywood Principle

Template Method exemplifies **"Don't call us, we'll call you"**:

- Base class controls the flow
- Base class calls subclass methods (not vice versa)
- Inversion of control - framework calls application code

```java
// Base class is in control
public abstract class Framework {
    public final void execute() {
        initialize();        // Framework calls...
        doWork();           // ...your code
        cleanup();          // ...when it wants
    }
    
    protected abstract void doWork();  // You implement this
}
```

## Common Use Cases

### Testing Frameworks
```java
public abstract class TestCase {
    public final void run() {
        setUp();
        try {
            runTest();
        } finally {
            tearDown();
        }
    }
    
    protected void setUp() { }
    protected abstract void runTest();
    protected void tearDown() { }
}
```

### Data Import/Export
```java
public abstract class DataExporter {
    public final void export(List<Data> data) {
        openConnection();
        writeHeader();
        for (Data item : data) {
            writeData(item);
        }
        writeFooter();
        closeConnection();
    }
    
    protected abstract void writeHeader();
    protected abstract void writeData(Data item);
    protected void writeFooter() { }  // Optional
}
```

### UI Component Rendering
```java
public abstract class Widget {
    public final void render() {
        beforeRender();
        renderBorder();
        renderContent();
        renderChildren();
        afterRender();
    }
    
    protected void beforeRender() { }
    protected abstract void renderContent();
    protected void afterRender() { }
}
```

## Implementation Best Practices

### 1. Minimize Primitive Operations
```java
// Bad - too many abstract methods
protected abstract void step1();
protected abstract void step2();
protected abstract void step3();
protected abstract void step4();
protected abstract void step5();

// Better - group related operations
protected abstract void initialize();
protected abstract void process();
protected abstract void cleanup();
```

### 2. Use Access Modifiers Appropriately
```java
public final void templateMethod() { }      // Public - clients call this
protected abstract void primitiveOp();      // Protected - only for subclasses
private void helperMethod() { }             // Private - internal only
```

### 3. Provide Meaningful Hooks
```java
// Good hooks
protected boolean shouldValidate() { return true; }
protected void afterSuccessfulSave(Data data) { }
protected int getMaxRetries() { return 3; }

// Bad hooks (too generic)
protected void hook1() { }
protected void hook2() { }
```

### 4. Document the Contract
```java
/**
 * Template method for processing data.
 * <p>
 * Execution order:
 * 1. load() - must return non-null data
 * 2. validate() - may throw ValidationException
 * 3. transform() - if shouldTransform() returns true
 * 4. save() - must be idempotent
 * 
 * @throws ValidationException if data is invalid
 */
public final void process() { ... }
```

## Testing Strategy

### 1. Test Template Method Flow
```java
@Test
void testTemplateMethodExecutesInCorrectOrder() {
    OrderRecorder recorder = new OrderRecorder();
    ConcreteClass instance = new ConcreteClass(recorder);
    
    instance.templateMethod();
    
    assertThat(recorder.getOrder()).containsExactly(
        "step1", "step2", "step3"
    );
}
```

### 2. Test Each Primitive Operation
```java
@Test
void testPrimitiveOperationBehavior() {
    ConcreteClass instance = new ConcreteClass();
    String result = instance.primitiveOperation("input");
    assertThat(result).isEqualTo("expected");
}
```

### 3. Test Hook Methods
```java
@Test
void testHookMethodCustomization() {
    ConcreteClass withHook = new ConcreteClass() {
        @Override
        protected void afterProcessing() {
            // Custom behavior
        }
    };
    
    withHook.process();
    // Verify hook was called
}
```

## Common Mistakes

### ❌ Making Template Method Non-Final
```java
// Bad - subclasses can break the flow
public void templateMethod() { }

// Good - structure is protected
public final void templateMethod() { }
```

### ❌ Too Many Primitive Operations
```java
// Bad - 10 abstract methods is too many
protected abstract void step1();
protected abstract void step2();
// ... step3 through step10

// Better - group into fewer operations
protected abstract void initialize();
protected abstract void execute();
protected abstract void cleanup();
```

### ❌ Using Public Access for Hooks
```java
// Bad - hooks should not be public
public void afterProcessing() { }

// Good - hooks are protected
protected void afterProcessing() { }
```

### ❌ Not Providing Hook Methods
```java
// Bad - no customization points
public final void process() {
    step1();
    step2();
    step3();
}

// Better - allow optional customization
public final void process() {
    beforeProcessing();  // Hook
    step1();
    step2();
    step3();
    afterProcessing();   // Hook
}
```

## Exercises

This module includes two hands-on exercises:

1. **Pattern in Isolation** (`docs/exercise-1-pattern-in-isolation.md`)
   - Simple data processing pipeline
   - Focus on template method structure
   - Practice implementing primitive operations and hooks

2. **Real-World Simulation** (`docs/exercise-2-real-world-simulation.md`)
   - Report generation system with multiple formats
   - Production-grade complexity
   - Advanced customization and testing

## Further Reading

- *Design Patterns* (Gang of Four) - Classic treatment
- *Head First Design Patterns* - Beginner-friendly examples
- *Refactoring Guru* - https://refactoring.guru/design-patterns/template-method
- JUnit framework source - Real-world Template Method usage

---

**Note**: The Template Method pattern is one of the most commonly used patterns in frameworks and libraries. Understanding it well will help you both use and create extensible systems.
