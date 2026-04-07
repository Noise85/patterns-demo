# Abstract Factory Pattern

## Overview

The **Abstract Factory** pattern is a creational design pattern that lets you produce families of related objects without specifying their concrete classes.

## Problem It Solves

When you need to create sets of related objects that must work together:

- You have families of related products (e.g., Light theme + Dark theme UI components)
- You want to ensure products from the same family are used together
- You need to support multiple families without coupling code to specific classes
- You want to provide a library of products revealing only interfaces, not implementations

**Without Abstract Factory**: You create objects directly and risk mixing incompatible products from different families.

**With Abstract Factory**: You use a factory interface that creates entire families of related objects, ensuring compatibility.

## When to Use It

✅ **Use Abstract Factory when:**

- Your code needs to work with various families of related products
- You don't want to depend on concrete classes of products
- You want to ensure products from the same family are used together
- You're providing a library and want to reveal only interfaces

## When NOT to Use It

❌ **Avoid Abstract Factory when:**

- You only have one family of products (use Factory Method instead)
- Product families don't need to vary independently
- The complexity of multiple factory interfaces isn't justified
- Adding new product types requires changing all factories (breaks Open/Closed)

## Real-World Examples

- **UI toolkits**: Windows/macOS/Linux UI component families
- **Database access**: MySQL/PostgreSQL/Oracle connection+command+result families
- **Document formats**: PDF/HTML/Excel exporter+formatter+renderer families
- **Cloud providers**: AWS/Azure/GCP service families (storage, compute, networking)
- **Payment gateways**: Different provider families (auth, charge, refund)

## Key Participants

1. **Abstract Factory**: Declares creation methods for each product type
2. **Concrete Factories**: Implement creation methods to produce concrete products
3. **Abstract Products**: Declare interfaces for product types
4. **Concrete Products**: Implement product interfaces, grouped by variants

## Structure

```
AbstractFactory              AbstractProductA    AbstractProductB
  + createProductA()             ↑                   ↑
  + createProductB()             │                   │
        ↑                   ProductA1  ProductA2  ProductB1  ProductB2
        │                      
  ConcreteFactory1    ConcreteFactory2
  creates ProductA1   creates ProductA2
  creates ProductB1   creates ProductB2
```

## In This Module

### Exercise 1: Pattern in Isolation

**Domain**: UI theme system

**Focus**: Understanding product families and factory interfaces

You'll implement a simple UI component system with two themes (Light and Dark). Each theme is a product family containing related UI elements (Button, Checkbox) that must work together visually.

### Exercise 2: Real-World Simulation

**Domain**: Cross-platform document export system

**Focus**: Production-ready document generation infrastructure

You'll build a document export system supporting multiple output formats (PDF, HTML, Markdown). Each format is a product family with coordinated components: Renderer, Formatter, and StyleApplier. The system must ensure components from the same format work together.

## Hints for Success

1. Factory interface declares methods for creating each product type
2. Each concrete factory creates products belonging to ONE family
3. Products from the same family share visual/functional consistency
4. Client code works with factories and products through interfaces only

## References

- [Refactoring.Guru: Abstract Factory](https://refactoring.guru/design-patterns/abstract-factory)
