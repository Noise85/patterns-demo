# Bridge Pattern

## Overview

The **Bridge** pattern is a structural design pattern that separates an abstraction from its implementation so that the two can vary independently. It uses composition to delegate work from abstraction to implementation objects.

## Problem It Solves

When you have multiple dimensions of variation (e.g., shape + color, device + remote), creating subclasses for every combination leads to **class explosion**.

**Without Bridge**: `RedCircle`, `BlueCircle`, `RedSquare`, `BlueSquare`... (n×m classes)

**With Bridge**: Separate `Shape` hierarchy from `Color` hierarchy, compose them (n+m classes).

## When to Use It

✅ **Use Bridge when:**

- You want to avoid permanent binding between abstraction and implementation
- Both abstractions and implementations should be extensible via subclassing
- You have multiple dimensions of variation
- You need to share implementation among multiple objects
- Changes in implementation shouldn't affect clients

## When NOT to Use It

❌ **Avoid Bridge when:**

- You have only one dimension of variation
- Abstraction and implementation are tightly coupled by design
- Simple inheritance suffices
- The complexity of two hierarchies isn't justified

## Real-World Examples

- **Remote controls × Devices**: Different remotes control different devices
- **Messages × Platforms**: Email, SMS, Push sent via different channels
- **Shapes × Renderers**: Shapes drawn on different platforms (Vector, Raster)
- **Reports × Exporters**: Reports formatted in different ways (PDF, HTML)
- **GUIAbstractions × Platform Implementations**: Cross-platform GUI toolkits

## Key Participants

1. **Abstraction**: High-level control interface, delegates to Implementor
2. **RefinedAbstraction**: Extends Abstraction with additional operations
3. **Implementor**: Interface for implementation classes
4. **ConcreteImplementor**: Actual platform-specific implementations

## Structure

```
Abstraction ──────► Implementor (interface)
     ↑                      ↑
     |                      |
RefinedAbstraction    ConcreteImplementorA
                      ConcreteImplementorB
```

## In This Module

### Exercise 1: Pattern in Isolation

**Domain**: Shape rendering across platforms

**Focus**: Separating shape abstraction from rendering implementation

You'll implement shapes (Circle, Rectangle) that can be rendered using different rendering engines (VectorRenderer, RasterRenderer) without creating shape-renderer combinations.

### Exercise 2: Real-World Simulation

**Domain**: Notification system with multiple channels

**Focus**: Decoupling message abstraction from delivery platform

You'll build a notification system where different message types (Alert, Reminder) can be sent through various channels (Email, SMS, Push) independently.

## Hints for Success

1. Abstraction holds reference to Implementor
2. Abstraction delegates work to Implementor
3. Both hierarchies can evolve independently
4. Use composition, not inheritance, to link them
5. Implementor defines low-level operations
6. Abstraction defines high-level operations

## References

- [Refactoring.Guru: Bridge](https://refactoring.guru/design-patterns/bridge)
