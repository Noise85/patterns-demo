# Adapter Pattern

## Overview

The **Adapter** pattern is a structural design pattern that allows objects with incompatible interfaces to collaborate. It wraps an existing class with a new interface to make it compatible with client code.

## Problem It Solves

When you need to use a class whose interface doesn't match what you need:

- Third-party libraries with different interfaces
- Legacy code integration
- Standardizing heterogeneous interfaces
- Making reusable code work with new systems

**Without Adapter**: Modify existing classes (risky, often impossible with libraries) or duplicate code.

**With Adapter**: Create an adapter wrapping the incompatible interface, translating calls.

## When to Use It

✅ **Use Adapter when:**

- You want to use an existing class with an incompatible interface
- You need to create a reusable class that works with unrelated classes
- You're integrating third-party libraries
- You need to convert interfaces for legacy code
- Several existing subclasses need a common interface

## When NOT to Use It

❌ **Avoid Adapter when:**

- You control both interfaces (refactor instead)
- A simple facade would suffice
- You can modify the adaptee directly
- The adaptation is trivial (direct delegation)

## Real-World Examples

- **Payment gateways**: Adapting Stripe, PayPal, Square to common interface
- **Data sources**: Adapting SQL, NoSQL, REST APIs to repository interface
- **Logging**: Adapting different logging libraries (Log4j, SLF4J) to common facade
- **File systems**: Adapting local, S3, FTP storage to unified interface
- **Authentication**: Adapting OAuth, SAML, JWT to common auth interface

## Key Participants

1. **Target**: Interface expected by client
2. **Adapter**: Implements Target, wraps Adaptee
3. **Adaptee**: Existing class with incompatible interface
4. **Client**: Works with Target interface

## Structure

```
Client → Target (interface)
              ↑
              |
         Adapter (implements Target)
              |
         Adaptee (has different interface)
```

## In This Module

### Exercise 1: Pattern in Isolation

**Domain**: Temperature sensor adaptation

**Focus**: Simple adapter converting interfaces

You'll adapt legacy Fahrenheit temperature sensors to work with a modern system expecting Celsius measurements.

### Exercise 2: Real-World Simulation

**Domain**: Multi-vendor paymentgateway integration

**Focus**: Production-ready adapter for third-party services

You'll integrate multiple third-party payment processors (each with different APIs) behind a unified payment interface using adapters.

## Hints for Success

1. Adapter implements the Target interface
2. Adapter holds reference to Adaptee
3. Adapter translates calls from Target to Adaptee
4. Consider object adapter (composition) vs class adapter (inheritance)
5. Adapters should be thin translation layers

## References

- [Refactoring.Guru: Adapter](https://refactoring.guru/design-patterns/adapter)
