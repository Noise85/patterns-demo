# Prototype Pattern

## Overview

The **Prototype** pattern is a creational design pattern that lets you copy existing objects without making your code dependent on their classes. It uses cloning to create new instances instead of construction.

## Problem It Solves

When you need to create copies of complex objects:

- Object creation is expensive (database queries, file I/O, complex initialization)
- You want to create objects with the same or similar state as existing ones
- You need to avoid coupling to concrete classes
- Objects have many configurations and you want to clone pre-configured templates

**Without Prototype**: You create new objects from scratch or manually copy all fields.

**With Prototype**: You clone existing objects, optionally modifying them after cloning.

## When to Use It

✅ **Use Prototype when:**

- Object creation is more expensive than cloning
- You need copies of objects at runtime
- You want to reduce the number of subclasses (clone instead of subclass)
- You need to preserve object state for undo/redo
- You're implementing a prototype registry of pre-configured objects

## When NOT to Use It

❌ **Avoid Prototype when:**

- Objects are simple and cheap to construct
- Cloning circular references is complex
- Deep copying is difficult (nested mutable objects)
- Objects don't support cloning well

## Real-World Examples

- **Document editors**: Cloning shapes, paragraphs, or entire documents
- **Game development**: Spawning enemies/items from templates
- **Configuration management**: Cloning configuration objects with defaults
- **Form builders**: Duplicating form fields or entire forms
- **Test data**: Creating test fixtures from templates

## Key Participants

1. **Prototype**: Declares cloning interface (typically `clone()`)
2. **Concrete Prototype**: Implements cloning, creates a copy of itself
3. **Client**: Creates new objects by cloning prototypes
4. **Registry** (optional): Stores and retrieves prototype instances

## Structure

```
Prototype (interface)
  + clone(): Prototype
        ↑
        |
  ConcretePrototype
  + clone(): ConcretePrototype
  creates copy of itself
  
PrototypeRegistry (optional)
  stores prototypes by key
  + getPrototype(key): Prototype
```

## In This Module

### Exercise 1: Pattern in Isolation

**Domain**: Document cloning system

**Focus**: Implementing clone() with deep copy semantics

You'll implement a document structure with paragraphs and images that supports cloning. You must handle deep copying of nested objects to ensure independence between clones.

### Exercise 2: Real-World Simulation

**Domain**: Game entity spawner with prototype registry

**Focus**: Production-ready prototype registry and entity cloning

You'll build a game entity system where enemies and items are spawned from pre-configured templates. The system uses a prototype registry to efficiently create instances without expensive initialization for each spawn.

## Hints for Success

1. Implement Cloneable interface or create custom clone() method
2. Ensure deep copy for mutable nested objects
3. Clone collections (use new ArrayList<>(original))
4. Consider implementing copy constructor as alternative to clone()
5. Be aware of cloning pitfalls (shallow vs deep copy)

## References

- [Refactoring.Guru: Prototype](https://refactoring.guru/design-patterns/prototype)
