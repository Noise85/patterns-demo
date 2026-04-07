# Mediator Pattern

## Intent

**Mediator** is a behavioral design pattern that lets you reduce chaotic dependencies between objects. The pattern restricts direct communications between the objects and forces them to collaborate only via a mediator object.

## Problem

When you have many objects that need to communicate with each other, direct object-to-object communication can create a tangled web of dependencies. Each object needs to know about many other objects, making the system hard to understand and maintain. Changes to one object can ripple through the entire system.

## Solution

The Mediator pattern suggests that you should cease all direct communication between the components which you want to make independent of each other. Instead, these components must collaborate indirectly, by calling a special mediator object that redirects the calls to appropriate components. As a result, the components depend only on a single mediator class instead of being coupled to dozens of their colleagues.

## Structure

- **Mediator**: Interface declaring methods for communicating with components (colleagues)
- **ConcreteMediator**: Implements coordination logic between components, maintains references to all components
- **Colleague**: Components that communicate through the mediator, each holds a reference to the mediator
- **Communication Flow**: Component → Mediator → Other Components

## When to Use

- When a set of objects communicate in well-defined but complex ways
- When you want to reuse components without their dependencies
- When you find yourself creating lots of component subclasses just to reuse basic behavior in different contexts
- When behavior distributed between several classes should be customizable without excessive subclassing

## Real-World Applications

1. **Air Traffic Control**: Aircraft don't communicate directly; they communicate through the control tower
2. **Chat Rooms**: Users send messages through a chat room mediator
3. **GUI Dialog Systems**: Form components (buttons, text fields) communicate through dialog coordinator
4. **Smart Home Systems**: Devices coordinate actions through a central hub
5. **Workflow Engines**: Steps in a workflow coordinate through a workflow manager

## Mediator vs. Observer

| Aspect | Mediator | Observer |
|--------|----------|----------|
| **Communication** | Bidirectional between mediator and colleagues | One-way from subject to observers |
| **Coupling** | Colleagues know only the mediator | Observers and subjects are loosely coupled |
| **Coordination** | Centralized coordination logic | Distributed notification logic |
| **Use Case** | Complex interactions between objects | Broadcasting changes to multiple objects |
| **Colleague Independence** | Colleagues are unaware of each other | Observers are independent of each other |

## Mediator vs. Facade

- **Facade** provides a simplified interface to a subsystem but doesn't add new functionality; subsystem classes can work independently
- **Mediator** centralizes communication between components; components only work through the mediator

## Key Design Decisions

1. **Mediator Responsibilities**: Keep mediator focused on coordination, not business logic
2. **Colleague Independence**: Colleagues should not know about each other
3. **Event Types**: Define clear event/message types for communication
4. **State Management**: Decide whether mediator or colleagues maintain state
5. **Mediator Complexity**: Watch for "god object" anti-pattern; consider splitting into multiple mediators

## Benefits

- Loosens coupling between colleagues
- Centralizes control of interactions
- Simplifies object protocols (many-to-many → one-to-many)
- Promotes reusability of colleague objects

## Drawbacks

- Mediator can become too complex ("god object")
- Mediator becomes a single point of failure
- May reduce system flexibility if coordination logic is too rigid

## References

- Refactoring Guru: https://refactoring.guru/design-patterns/mediator
- Gang of Four: Design Patterns (1994)
