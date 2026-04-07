# Proxy Pattern

**Category**: Structural Design Pattern

## What is the Proxy Pattern?

The Proxy pattern provides a surrogate or placeholder for another object to control access to it. A proxy acts as an intermediary between a client and the real object, adding additional functionality without changing the real object's interface.

### Key Characteristics

- **Same interface**: Proxy implements the same interface as the real subject
- **Controlled access**: Proxy controls access to the real object
- **Lazy initialization**: Can defer creation of expensive objects
- **Additional behavior**: Can add logging, caching, access control, etc.
- **Transparent**: Clients interact with proxy as if it's the real object

## Types of Proxies

### Virtual Proxy
Delays creation of expensive objects until needed (lazy loading).  
**Example**: Loading large images only when displayed

### Protection Proxy
Controls access to an object based on permissions.  
**Example**: Authorization checks before method execution

### Remote Proxy
Represents objects in different address spaces (RPC, network).  
**Example**: RMI stubs, REST API clients

### Smart Proxy
Adds additional housekeeping behavior.  
**Example**: Reference counting, logging, caching

### Cache Proxy
Caches results of expensive operations.  
**Example**: Database query caching

## When to Use the Proxy Pattern

### Ideal Scenarios

- **Lazy initialization**: Object creation is expensive (memory/time)
- **Access control**: Need authentication/authorization before operations
- **Remote access**: Object exists in different address space
- **Logging/auditing**: Track all access to an object
- **Resource management**: Control resource-intensive operations
- **Caching**: Store results of expensive computations

### Real-World Examples

- **Virtual proxies**: Image viewers (load full image on demand)
- **Protection proxies**: Document management (permission checks)
- **Remote proxies**: Distributed systems, web services
- **Smart proxies**: ORM frameworks (lazy loading relationships)
- **Cache proxies**: HTTP caching, memoization

## Why Use the Proxy Pattern?

### Benefits

✅ **Controlled access**: Gate operations with security, validation, logging  
✅ **Performance**: Defer expensive operations (lazy loading)  
✅ **Separation of concerns**: Keep access logic separate from business logic  
✅ **Transparent**: Clients don't know they're using a proxy  
✅ **Open/Closed**: Add functionality without modifying real object

### Trade-offs

⚠️ **Complexity**: Additional layer of indirection  
⚠️ **Response time**: Proxy checks add overhead (though often negligible)  
⚠️ **Maintenance**: Must keep proxy and real subject in sync  
⚠️ **Overuse**: Not every class needs a proxy

## Structure

```
Client
   ↓
Subject (interface)
   ↓
   ├─→ RealSubject (actual implementation)
   └─→ Proxy (controls access to RealSubject)
```

The proxy holds a reference to the real subject and delegates calls, adding behavior before/after.

## Common Implementations

### Virtual Proxy (Lazy Loading)
```java
class ImageProxy implements Image {
    private String filename;
    private RealImage realImage;  // Created on demand
    
    void display() {
        if (realImage == null) {
            realImage = new RealImage(filename);  // Lazy load
        }
        realImage.display();
    }
}
```

### Protection Proxy (Access Control)
```java
class DocumentProxy implements Document {
    private RealDocument document;
    private User currentUser;
    
    void edit() {
        if (!currentUser.hasPermission("EDIT")) {
            throw new AccessDeniedException();
        }
        document.edit();
    }
}
```

## Exercises

This module contains two hands-on TDD exercises:

1. **Pattern in Isolation** (`exercise-1-pattern-in-isolation.md`)  
   Focus: Virtual proxy for lazy-loading expensive database connections

2. **Real-World Simulation** (`exercise-2-real-world-simulation.md`)  
   Focus: Protection proxy for document management system with role-based access control

## Learning Objectives

After completing these exercises, you will:

- Implement virtual proxies with lazy initialization
- Create protection proxies with access control
- Understand the difference between proxy types
- Apply transparent substitution (same interface)
- Add cross-cutting concerns (logging, caching) without modifying core logic
- Recognize when proxies add value vs. unnecessary complexity

## References

- [Refactoring Guru - Proxy Pattern](https://refactoring.guru/design-patterns/proxy)
- Gang of Four Design Patterns
- Java's dynamic proxies (`java.lang.reflect.Proxy`)
- Spring AOP (uses proxies for cross-cutting concerns)
