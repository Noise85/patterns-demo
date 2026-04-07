# Decorator Pattern

**Category**: Structural Design Pattern

## What is the Decorator Pattern?

The Decorator pattern lets you attach new behaviors to objects by placing these objects inside special wrapper objects that contain the behaviors. It provides a flexible alternative to subclassing for extending functionality.

### Key Characteristics

- **Wrappers**: Decorators wrap objects, maintaining the same interface
- **Composability**: Multiple decorators can be stacked/layered
- **Runtime flexibility**: Behaviors can be added or removed dynamically
- **Single Responsibility**: Each decorator adds one specific behavior
- **Open/Closed Principle**: Extend behavior without modifying existing code

## When to Use the Decorator Pattern

### Ideal Scenarios

- **Dynamic behavior composition**: Need to add responsibilities to objects at runtime
- **Combinatorial explosion**: Subclassing would create too many classes
- **Optional features**: Some instances need extra features, others don't
- **Layered functionality**: Multiple independent concerns (logging, caching, validation, etc.)
- **Reversible enhancements**: Behaviors need to be added/removed dynamically

### Real-World Examples

- **I/O Streams**: `BufferedInputStream` wraps `FileInputStream`
- **Middleware pipelines**: HTTP request/response processing
- **UI components**: Scrollbars, borders, shadows on windows
- **Pricing calculations**: Base price + tax + discounts + fees
- **Data processing**: Compression → Encryption → Transmission

## Why Use the Decorator Pattern?

### Benefits

✅ **More flexible than inheritance**: No class explosion  
✅ **Runtime composition**: Add/remove behaviors dynamically  
✅ **Single Responsibility**: Each decorator has one job  
✅ **Open/Closed Principle**: Extend without modifying  
✅ **Composable**: Mix and match decorators freely

### Trade-offs

⚠️ **Complexity**: Many small objects in the system  
⚠️ **Order dependency**: Decorator stacking order can matter  
⚠️ **Identity issues**: Wrapped object !== original object  
⚠️ **Configuration overhead**: Setting up decorator chains

## Structure

```
Component (interface)
├── ConcreteComponent (base functionality)
└── Decorator (abstract, holds Component reference)
    ├── ConcreteDecoratorA (adds behavior A)
    └── ConcreteDecoratorB (adds behavior B)
```

## Exercises

This module contains two hands-on TDD exercises:

1. **Pattern in Isolation** (`exercise-1-pattern-in-isolation.md`)  
   Focus: Notification system with encryption, compression, and formatting decorators

2. **Real-World Simulation** (`exercise-2-real-world-simulation.md`)  
   Focus: Payment processing pipeline with validation, fraud detection, and audit logging

## Learning Objectives

After completing these exercises, you will:

- Understand when decorators are superior to inheritance
- Implement composable wrapper chains
- Handle decorator stacking order correctly
- Apply the pattern to realistic domain problems
- Test decorated objects in isolation and combination

## References

- [Refactoring Guru - Decorator Pattern](https://refactoring.guru/design-patterns/decorator)
- Gang of Four Design Patterns
- Effective Java (Item 18: Favor composition over inheritance)
