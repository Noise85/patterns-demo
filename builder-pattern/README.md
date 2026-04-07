# Builder Pattern

## Overview

The **Builder** pattern is a creational design pattern that lets you construct complex objects step by step. It allows you to produce different types and representations of an object using the same construction code.

## Problem It Solves

When you need to create complex objects with many optional parameters:

- Constructors with many parameters become unwieldy (telescoping constructor anti-pattern)
- Objects require step-by-step construction with validation
- You want immutable objects with optional fields
- You need to create different representations of the same object

**Without Builder**: You create constructors with many parameters or use setters (making objects mutable).

**With Builder**: You construct objects step-by-step through a fluent interface, enforcing validation and immutability.

## When to Use It

✅ **Use Builder when:**

- Your object has many optional parameters (4+)
- You want to create immutable objects
- Construction requires multiple steps or validation
- You need different representations of the same object
- You want to avoid telescoping constructors

## When NOT to Use It

❌ **Avoid Builder when:**

- Objects have few parameters (use constructors or factory methods)
- All parameters are required
- Objects are simple with no construction logic
- The added complexity isn't justified

## Real-World Examples

- **HTTP clients**: Building requests with headers, query params, body, timeout
- **SQL query builders**: SELECT, WHERE, JOIN, GROUP BY, ORDER BY
- **Document builders**: Creating PDFs/reports with sections, styling, metadata
- **Configuration objects**: App configs with dozens of optional settings
- **Test data builders**: Creating complex test fixtures with sensible defaults

## Key Participants

1. **Builder**: Declares construction steps
2. **Concrete Builder**: Implements steps and provides result retrieval
3. **Director** (optional): Defines order of construction steps
4. **Product**: Complex object being constructed

## Structure

```
Client
   |
   v
Builder (interface/abstract)
  + buildPartA()
  + buildPartB()
  + getResult()
        ↑
        |
  ConcreteBuilder
  creates and assembles parts
  returns final Product
```

## In This Module

### Exercise 1: Pattern in Isolation

**Domain**: HTTP request builder

**Focus**: Fluent API, optional parameters, immutability

You'll implement a builder for HTTP requests with optional headers, query parameters, body, and configuration. The builder ensures valid requests and produces immutable objects.

### Exercise 2: Real-World Simulation

**Domain**: SQL query builder

**Focus**: Production-grade query construction with validation

You'll build a SQL query builder supporting SELECT, WHERE clauses, JOINs, and ordering. The builder validates query structure and prevents SQL injection through parameterization.

## Hints for Success

1. Use method chaining (return `this` from builder methods)
2. Store state in the builder, construct product in `build()`
3. Validate required fields in `build()` method
4. Consider making the product immutable
5. Builders can have defaults for optional parameters

## References

- [Refactoring.Guru: Builder](https://refactoring.guru/design-patterns/builder)
