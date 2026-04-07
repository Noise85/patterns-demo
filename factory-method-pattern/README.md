# Factory Method Pattern

## Overview

The **Factory Method** pattern is a creational design pattern that provides an interface for creating objects in a superclass, but allows subclasses to alter the type of objects that will be created.

## Problem It Solves

When you need to create objects but:

- You don't know the exact type of object until runtime
- You want to delegate the instantiation logic to subclasses
- You need to centralize object creation to make it easier to modify
- You want to promote loose coupling between classes

**Without Factory Method**: You create objects directly with `new`, scattering instantiation logic throughout your code and coupling your code to concrete classes.

**With Factory Method**: Object creation is delegated to factory methods, allowing subclasses to decide which class to instantiate.

## When to Use It

✅ **Use Factory Method when:**

- You don't know beforehand the exact types and dependencies of objects your code should work with
- You want to provide users of your library/framework with a way to extend internal components
- You want to save system resources by reusing existing objects instead of rebuilding them
- You need to decouple object creation from usage
- The creation logic is complex or might change

## When NOT to Use It

❌ **Avoid Factory Method when:**

- Object creation is simple and unlikely to change (use `new` directly)
- You only have one concrete product type
- The pattern would add unnecessary complexity to simple code
- You can use simpler creational patterns like Simple Factory

## Real-World Examples

- **Document generators**: Creating different document formats (PDF, HTML, DOCX)
- **Notification systems**: Creating different notification channels (Email, SMS, Push)
- **Database connections**: Creating connections for different database types
- **UI components**: Creating platform-specific UI elements
- **Logging**: Creating different logger types (file, console, remote)
- **Payment processors**: Creating payment handlers for different gateways

## Key Participants

1. **Product Interface**: Declares the interface of objects the factory method creates
2. **Concrete Products**: Implement the Product interface
3. **Creator (Abstract)**: Declares the factory method that returns Product objects
4. **Concrete Creators**: Override the factory method to return different Product types

## Structure

```
Creator (abstract)
  + factoryMethod(): Product
  + someOperation()
       ↓ uses
    Product
       ↑
       │
ConcreteCreatorA        ConcreteCreatorB
  + factoryMethod()      + factoryMethod()
       ↓ creates             ↓ creates
  ConcreteProductA      ConcreteProductB
```

## In This Module

### Exercise 1: Pattern in Isolation

**Domain**: Document export system

**Focus**: Understanding factory method basics

You'll implement a simple document export system where different exporters (PDF, HTML, Markdown) create different document types. Each exporter is a concrete creator with its own factory method.

**Learning Objectives**:
- Define a product interface
- Create concrete products
- Implement creator with factory method
- Use factory method in business logic

### Exercise 2: Real-World Simulation

**Domain**: Multi-channel notification system

**Focus**: Production-ready notification infrastructure

You'll build a notification system that sends messages through different channels (Email, SMS, Push, Slack). Each channel has different requirements, validation rules, delivery tracking, and retry logic. The system must be extensible to add new channels without modifying existing code.

**Learning Objectives**:
- Apply Factory Method in a complex business domain
- Handle channel-specific configuration and validation
- Design for extensibility (Open/Closed Principle)
- Manage dependencies and lifecycle
- Write production-quality code

## Hints for Success

1. The factory method should return the Product interface, not concrete types
2. Keep the factory method focused on object creation only
3. Complex initialization logic belongs in the factory method
4. Consider parameterized factory methods when needed
5. The creator class can have other methods that use the products

## References

- [Refactoring.Guru: Factory Method](https://refactoring.guru/design-patterns/factory-method)
- [Wikipedia: Factory Method Pattern](https://en.wikipedia.org/wiki/Factory_method_pattern)
