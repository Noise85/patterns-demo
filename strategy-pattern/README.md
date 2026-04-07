# Strategy Pattern

## Overview

The **Strategy** pattern is a behavioral design pattern that lets you define a family of algorithms, put each of them into a separate class, and make their objects interchangeable.

## Problem It Solves

When you have multiple ways to perform an operation (algorithms, behaviors, or strategies), and you want to:

- Switch between them at runtime
- Avoid complex conditional logic (if/else or switch statements)
- Make it easy to add new strategies without modifying existing code
- Keep related algorithms together but independent

**Without Strategy**: You might have a large class with multiple conditional statements that select different behaviors based on context.

**With Strategy**: Each algorithm becomes a separate class implementing a common interface. The context delegates the work to the strategy object.

## When to Use It

✅ **Use Strategy when:**

- You have multiple related algorithms or behaviors that differ only in their implementation
- You need to switch between algorithms at runtime
- You want to isolate the algorithm implementation from the code that uses it
- You have a class with a massive conditional statement that switches between different variants of the same algorithm
- You want to hide complex, algorithm-specific data structures from the client

## When NOT to Use It

❌ **Avoid Strategy when:**

- You only have a couple of algorithms that rarely change (the pattern might be overkill)
- The strategies are very simple and don't justify separate classes (consider lambda expressions instead)
- Clients must be aware of all strategies to pick the right one (this increases coupling)
- The overhead of many small classes is not justified by flexibility benefits

## Real-World Examples

- **Payment processing**: Credit card, PayPal, cryptocurrency, bank transfer
- **Compression algorithms**: ZIP, RAR, TAR
- **Sorting algorithms**: QuickSort, MergeSort, BubbleSort
- **Validation rules**: Different validation for different contexts
- **Pricing calculations**: Standard pricing, seasonal discounts, membership discounts
- **File export formats**: PDF, CSV, Excel, JSON

## Key Participants

1. **Strategy Interface**: Declares operations common to all supported algorithms
2. **Concrete Strategies**: Implement the algorithm using the Strategy interface
3. **Context**: Maintains a reference to a Strategy object and delegates work to it

## Structure

```
Context ──────> Strategy (interface)
                   ↑
                   │
          ┌────────┼────────┐
          │        │        │
   ConcreteStrategyA  ConcreteStrategyB  ConcreteStrategyC
```

## In This Module

### Exercise 1: Pattern in Isolation

**Domain**: Simple payment method selection

**Focus**: Understanding the core mechanics of the Strategy pattern

You'll implement a basic payment processing system where customers can pay using different payment methods (credit card, PayPal, bank transfer). Each payment method is a strategy with the same interface.

**Learning Objectives**:
- Define a strategy interface
- Create concrete strategy implementations
- Use a context class to delegate to strategies
- Switch strategies at runtime

### Exercise 2: Real-World Simulation

**Domain**: Dynamic pricing engine for an e-commerce platform

**Focus**: Production-ready implementation with extensibility and real-world constraints

You'll build a sophisticated pricing engine that applies different pricing strategies based on customer segments, time periods, promotional campaigns, and business rules. The system must handle strategy composition, validation, and audit trails.

**Learning Objectives**:
- Apply Strategy pattern in a complex business domain
- Design for extensibility and maintainability
- Combine multiple strategies when needed
- Handle edge cases and validation
- Write production-quality code with proper separation of concerns

## Hints for Success

1. Start by defining a clear interface for the strategy
2. Keep strategies focused - each should do one thing well
3. Don't couple strategies to specific implementations
4. Consider using constructor injection to set strategies in the context
5. Think about whether strategies need to be stateless or can hold state
6. In real-world scenarios, consider strategy selection logic separate from strategies themselves

## References

- [Refactoring.Guru: Strategy Pattern](https://refactoring.guru/design-patterns/strategy)
- [Wikipedia: Strategy Pattern](https://en.wikipedia.org/wiki/Strategy_pattern)
