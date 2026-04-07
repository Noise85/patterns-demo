# Composite Pattern

## Overview

The **Composite** pattern is a structural design pattern that lets you compose objects into tree structures to represent part-whole hierarchies. It allows clients to treat individual objects and compositions uniformly.

## Problem It Solves

When dealing with tree structures (files/folders, UI components, organizational charts), you want to treat leaves and branches uniformly without type-checking.

**Without Composite**: Client code must distinguish between leaves and composites, leading to conditionals and duplicated logic.

**With Composite**: Define a common interface. Leaves and composites implement it. Client treats all objects uniformly.

## When to Use It

✅ **Use Composite when:**

- Objects form a tree structure (part-whole hierarchy)
- You want to ignore differences between leaf and composite objects
- Operations should work uniformly on individual and composite objects
- You're modeling hierarchical data (file systems, UI trees, org charts)

## When NOT to Use It

❌ **Avoid Composite when:**

- Your structure is not hierarchical
- Leaves and composites have fundamentally different operations
- Overly generalizing makes the design unclear
- Simple list/collection suffices

## Real-World Examples

- **File systems**: Files and folders (folders contain files/folders)
- **UI components**: Panels containing buttons/labels/other panels
- **Organization charts**: Employees and departments (departments contain employees/subdepartments)
- **Graphics**: Shapes and groups (groups contain shapes/subgroups)
- **Menu systems**: Menu items and submenus

## Key Participants

1. **Component**: Common interface for leaves and composites
2. **Leaf**: Represents end objects with no children
3. **Composite**: Stores children, implements operations by delegating
4. **Client**: Works with objects via Component interface

## Structure

```
Component (interface)
    ↑
    |
    +------- Leaf (no children)
    |
    +------- Composite (contains children)
                 |
                 +---> [Component, Component, ...]
```

## In This Module

### Exercise 1: Pattern in Isolation

**Domain**: File system hierarchy

**Focus**: Uniform treatment of files and folders

You'll implement a file system where files and folders share a common interface, allowing operations like calculating total size to work uniformly on both.

### Exercise 2: Real-World Simulation

**Domain**: Organization chart with employees and departments

**Focus**: Recursive structures and aggregation operations

You'll build an org chart where departments contain employees and subdepartments. Operations like calculating total salary budget work recursively.

## Hints for Success

1. Component interface declares operations common to leaves and composites
2. Composite stores children in a collection (List<Component>)
3. Composite delegates operations to children (iterate and aggregate)
4. Leaf implements operations directly
5. Client code is unaware of leaf vs composite distinction

## References

- [Refactoring.Guru: Composite](https://refactoring.guru/design-patterns/composite)
