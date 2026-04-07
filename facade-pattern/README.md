# Facade Pattern

**Category**: Structural Design Pattern

## What is the Facade Pattern?

The Facade pattern provides a simplified, unified interface to a complex subsystem. It hides the complexities of the underlying system and provides a higher-level interface that makes the subsystem easier to use.

### Key Characteristics

- **Simplified interface**: Single entry point for complex operations
- **Subsystem independence**: Clients don't need to know subsystem details
- **Loose coupling**: Reduces dependencies between clients and subsystems
- **Encapsulation**: Hides implementation complexity
- **Optional access**: Clients can still access subsystem directly if needed

## When to Use the Facade Pattern

### Ideal Scenarios

- **Complex subsystems**: Many interdependent classes with intricate interactions
- **Layered architecture**: Need clear boundaries between layers
- **Legacy code integration**: Simplify interaction with legacy systems
- **Third-party library wrapping**: Create simpler API for complex libraries
- **Reduce coupling**: Minimize client dependencies on internal implementations

### Real-World Examples

- **Compiler**: High-level compile() method hides lexing, parsing, optimization, code generation
- **Database frameworks**: ORM facades hide connection, query building, result mapping
- **Home theater**: Single "watch movie" button controls projector, speakers, lights, player
- **E-commerce checkout**: Simple checkout() hides inventory, payment, shipping, notifications
- **Cloud SDKs**: Simplified APIs wrapping complex REST endpoints

## Why Use the Facade Pattern?

### Benefits

✅ **Simplified interface**: Easier for clients to use  
✅ **Reduced coupling**: Clients depend on facade, not subsystem  
✅ **Easier testing**: Mock facade instead of entire subsystem  
✅ **Subsystem flexibility**: Change internals without affecting clients  
✅ **Clear boundaries**: Well-defined entry points

### Trade-offs

⚠️ **God object risk**: Facade can become too large  
⚠️ **Limited flexibility**: May not expose all subsystem capabilities  
⚠️ **Indirection**: Extra layer can impact performance (minimal)  
⚠️ **Partial abstraction**: Clients may still need subsystem access for advanced use

## Structure

```
Client → Facade → Subsystem Classes (A, B, C, D, ...)
```

The facade delegates client requests to appropriate subsystem objects, orchestrating complex workflows.

## Exercises

This module contains two hands-on TDD exercises:

1. **Pattern in Isolation** (`exercise-1-pattern-in-isolation.md`)  
   Focus: Home theater system with simplified movie-watching interface

2. **Real-World Simulation** (`exercise-2-real-world-simulation.md`)  
   Focus: E-commerce order processing facade coordinating inventory, payment, shipping, notifications

## Learning Objectives

After completing these exercises, you will:

- Identify when subsystems need simplification
- Design clean facade interfaces hiding complexity
- Orchestrate multiple subsystem interactions
- Balance simplicity with flexibility
- Apply the pattern to production-like scenarios

## References

- [Refactoring Guru - Facade Pattern](https://refactoring.guru/design-patterns/facade)
- Gang of Four Design Patterns
- Martin Fowler's "Patterns of Enterprise Application Architecture"
