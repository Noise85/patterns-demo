# Flyweight Pattern

**Category**: Structural Design Pattern

## What is the Flyweight Pattern?

The Flyweight pattern minimizes memory usage by sharing as much data as possible with similar objects. It's used when you need to create a large number of objects that share common state, allowing you to save memory by storing shared state externally.

### Key Characteristics

- **Intrinsic state**: Shared, immutable data stored in flyweight objects
- **Extrinsic state**: Context-dependent data passed to flyweight methods
- **Object pooling**: Flyweights are cached and reused via a factory
- **Memory optimization**: Dramatically reduces memory footprint for large object sets
- **Immutability**: Flyweight objects should be immutable and thread-safe

## When to Use the Flyweight Pattern

### Ideal Scenarios

- **Large numbers of objects**: Application creates thousands or millions of similar objects
- **Memory constraints**: Available memory is limited
- **Shared state**: Objects have significant shared (intrinsic) state
- **Identity independence**: Objects don't need unique identities
- **External state**: Context-specific (extrinsic) data can be computed or passed in

### Real-World Examples

- **Text editors**: Character objects sharing font/style (one object per character type, not per character instance)
- **Game engines**: Particle systems, trees in forests (shared textures, models)
- **Graphics**: Shape rendering with shared colors, fonts, images
- **Network connections**: Connection pool sharing socket configurations
- **String interning**: Java's String pool (`"abc" == "abc"`)

## Why Use the Flyweight Pattern?

### Benefits

✅ **Reduced memory usage**: Share data instead of duplicating  
✅ **Better performance**: Less garbage collection pressure  
✅ **Scalability**: Handle larger datasets with same memory  
✅ **Cache efficiency**: Better CPU cache utilization  
✅ **Centralized management**: Factory controls object lifecycle

### Trade-offs

⚠️ **Complexity**: Separating intrinsic/extrinsic state adds design complexity  
⚠️ **Runtime cost**: Method calls may be slower (passing extrinsic state)  
⚠️ **Thread safety**: Shared objects must be immutable or synchronized  
⚠️ **Not always beneficial**: Overhead can outweigh benefits for small object counts

## Structure

```
FlyweightFactory (maintains pool)
    ↓
Flyweight (immutable, stores intrinsic state)
    ↓
Client (passes extrinsic state to flyweight methods)
```

**Intrinsic state**: What's the same across instances (shared, stored in flyweight)  
**Extrinsic state**: What's different per instance (passed as parameters)

## Exercises

This module contains two hands-on TDD exercises:

1. **Pattern in Isolation** (`exercise-1-pattern-in-isolation.md`)  
   Focus: Character rendering system for text editor with shared glyph objects

2. **Real-World Simulation** (`exercise-2-real-world-simulation.md`)  
   Focus: Game forest rendering with shared tree types supporting thousands of tree instances

## Learning Objectives

After completing these exercises, you will:

- Distinguish between intrinsic and extrinsic state
- Implement flyweight factories with proper caching
- Measure memory savings from object sharing
- Apply the pattern to realistic large-scale scenarios
- Understand when flyweight is appropriate vs. premature optimization

## References

- [Refactoring Guru - Flyweight Pattern](https://refactoring.guru/design-patterns/flyweight)
- Gang of Four Design Patterns
- Joshua Bloch's "Effective Java" (Item 6: Avoid creating unnecessary objects)
