# Visitor Pattern

## Intent

The **Visitor** pattern lets you separate algorithms from the objects on which they operate. It allows you to add new operations to existing object structures without modifying those structures.

## Problem

Imagine you have a complex object structure (like a file system tree, document hierarchy, or shopping cart). You need to perform various operations on these objects:
- Export to different formats
- Calculate totals, statistics, or metrics
- Validate or transform data
- Generate reports

The naive approach is to add these methods directly to each class. However, this leads to:
- **Bloated classes**: Each class contains many unrelated operations
- **Maintenance nightmare**: Changes to one operation require modifying multiple classes
- **Violation of Single Responsibility**: Classes do both their core job and various auxiliary operations
- **Difficult extensibility**: Adding a new operation requires changing all classes

## Solution

The Visitor pattern suggests:
1. **Define a Visitor interface** with a `visit()` method for each type in the structure
2. **Implement the `accept(Visitor)` method** in each element class
3. **Create concrete visitors** for each operation (one visitor per operation)
4. **Elements delegate work to visitors** by calling the appropriate `visit()` method

This achieves **double dispatch**: the operation depends on both the visitor type and the element type being visited.

## Structure

```
┌─────────────────┐
│    Visitor      │
├─────────────────┤
│ visitFileA()    │
│ visitFileB()    │
└─────────────────┘
        △
        │ implements
        │
┌───────┴─────────┐
│ ConcreteVisitor │
├─────────────────┤
│ visitFileA()    │
│ visitFileB()    │
└─────────────────┘

┌─────────────────┐
│    Element      │
├─────────────────┤
│ accept(Visitor) │
└─────────────────┘
        △
        │ implements
        │
┌───────┴─────────┐
│ ConcreteElement │
├─────────────────┤
│ accept(Visitor) │ ──────> visitor.visitConcreteElement(this)
└─────────────────┘
```

## Key Characteristics

### When to Use
- ✅ You need to perform many distinct operations on objects in a complex structure
- ✅ The object structure rarely changes, but you frequently add new operations
- ✅ You want to keep related operations together in a single class (visitor)
- ✅ You need to gather information from a complex object hierarchy

### When NOT to Use
- ❌ The object structure changes frequently (adding new element types is hard)
- ❌ Element classes are simple and operations are few
- ❌ You can't modify element classes to add `accept()` method

## Visitor vs. Strategy

| Visitor | Strategy |
|---------|----------|
| Operates on **multiple types** in a structure | Operates on **single object/context** |
| Operation depends on **element type** | Algorithm is self-contained |
| Uses **double dispatch** | Uses single method call |
| Elements call visitor with `this` | Context delegates to strategy |
| Best for **tree/composite structures** | Best for **interchangeable algorithms** |

## Visitor vs. Command

| Visitor | Command |
|---------|---------|
| **Traverses** object structures | **Encapsulates** requests |
| Multiple `visit()` methods | Single `execute()` method |
| Focuses on **operation logic** | Focuses on **request lifecycle** |
| Type-safe (compile-time) | Can be parameterized (runtime) |

## Double Dispatch Explained

**Single Dispatch** (normal method call): Operation depends on object type (e.g., `obj.display()`)

**Double Dispatch** (Visitor): Operation depends on **both** visitor type and element type:
1. Client calls `element.accept(visitor)`
2. Element calls `visitor.visitElement(this)`
3. Visitor executes the correct operation for this specific element type

This allows compile-time type safety while keeping operations separate from data structures.

## Open/Closed Principle

Visitor pattern follows **Open/Closed Principle**:
- ✅ **Open for extension**: Add new visitors (new operations) without changing elements
- ✅ **Closed for modification**: Element classes remain unchanged when adding operations

However, adding new element types requires modifying all visitors (trade-off).

## Real-World Applications

1. **Compilers/Interpreters**: Traverse AST (Abstract Syntax Tree) for type checking, optimization, code generation
2. **Document Processing**: Export documents to JSON, XML, PDF, HTML
3. **E-commerce**: Calculate prices, taxes, discounts, generate invoices
4. **File Systems**: Calculate sizes, search files, generate reports
5. **Game Engines**: Render objects, detect collisions, apply physics
6. **Data Validation**: Validate complex object graphs with different rules

## Implementation Notes

### Encapsulation Consideration
Visitors often need access to element internals. Options:
1. **Public getters** (preferred): Keep fields private, expose via methods
2. **Friend classes** (Java: package-private): Allow visitor access
3. **Break encapsulation** (last resort): Make fields public

### Traversal Strategy
Who controls traversal:
1. **Visitor controls**: Visitor decides which elements to visit next
2. **Element controls**: Element visits children in `accept()` method
3. **Separate iterator**: External object manages traversal

### Return Values
Visitors can:
- Return void (side effects only)
- Return results (accumulate data)
- Throw exceptions (validation)

## Practical Considerations

### Thread Safety
- Visitors with mutable state are not thread-safe
- Use immutable visitors or synchronization
- Consider separate visitor instances per thread

### Performance
- Double dispatch has minimal overhead
- Traversal cost depends on structure size
- Cache visitor instances if stateless

### Testing
- Test each visitor independently
- Test each element type with each visitor
- Test composite structures end-to-end

---

## Exercises

This module contains two exercises:

1. **Pattern in Isolation** (`docs/exercise-1-pattern-in-isolation.md`)
   - File system traversal with size calculation and search
   - Focus: Basic visitor structure, recursive traversal

2. **Real-World Simulation** (`docs/exercise-2-real-world-simulation.md`)
   - E-commerce order processing with multiple visitors
   - Focus: Complex visitors, validation, report generation, analytics

See individual exercise files for detailed requirements and test cases.

---

## References

- **Refactoring Guru**: https://refactoring.guru/design-patterns/visitor
- **Gang of Four**: Design Patterns - Elements of Reusable Object-Oriented Software
