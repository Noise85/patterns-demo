# Singleton Pattern

## Overview

The **Singleton** pattern is a creational design pattern that ensures a class has only one instance and provides a global point of access to it.

## Problem It Solves

When you need exactly one instance of a class:

- Only one instance should coordinate actions across the system
- Global access to a shared resource is needed
- Lazy initialization with thread safety is required
- You want to control instantiation tightly

**Without Singleton**: Multiple instances could be created, causing state inconsistency or resource conflicts.

**With Singleton**: The class controls its instantiation, ensuring only one instance exists.

## When to Use It

✅ **Use Singleton when:**

- You need exactly one instance of a class
- You require global access to that instance
- The instance should be created lazily when first needed
- You need thread-safe initialization

## When NOT to Use It

❌ **Avoid Singleton when:**

- Multiple instances might be needed in the future
- It introduces global state (makes testing harder)
- Dependency injection would be clearer
- It violates Single Responsibility Principle
- Thread safety overhead isn't justified

## Real-World Examples

- **Configuration manager**: Application settings loaded once
- **Logger**: Single logging instance for the application
- **Database connection pool**: Centralized connection management
- **Cache manager**: Single cache instance
- **Thread pool executor**: Shared thread pool

## Key Participants

1. **Singleton**: Class with private constructor and static getInstance() method
2. **Instance field**: Static field holding the single instance
3. **Client**: Access singleton via getInstance()

## Structure

```
Singleton
  - static instance: Singleton
  - private Singleton() (constructor)
  + static getInstance(): Singleton
```

## Implementation Variants

1. **Eager initialization**: Instance created at class loading
2. **Lazy initialization**: Instance created on first access
3. **Thread-safe lazy**: Double-checked locking
4. **Enum singleton**: Thread-safe, JVM-guaranteed (best practice in Java)
5. **Bill Pugh**: Inner static helper class (safe, lazy)

## In This Module

### Exercise 1: Pattern in Isolation

**Domain**: Configuration manager

**Focus**: Thread-safe singleton with lazy initialization

You'll implement a thread-safe configuration manager using double-checked locking. The manager loads configuration from a source once and provides global access.

### Exercise 2: Real-World Simulation

**Domain**: Database connection pool

**Focus**: Production-ready singleton with resource management

You'll build a database connection pool singleton that manages a fixed pool of connections. The singleton ensures only one pool exists, handles thread-safe connection checkout/return, and manages pool lifecycle.

## Hints for Success

1. Make constructor private
2. Use static getInstance() method
3. For thread safety: synchronize, use volatile, or enum
4. Consider Bill Pugh idiom (static inner class)
5. Be aware of serialization and reflection challenges

## References

- [Refactoring.Guru: Singleton](https://refactoring.guru/design-patterns/singleton)
