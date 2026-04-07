# Observer Pattern

## Intent

**Observer** is a behavioral design pattern that lets you define a subscription mechanism to notify multiple objects about any events that happen to the object they're observing. The pattern establishes a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.

## Problem

Imagine you have two types of objects: a Publisher (Subject) and some Subscribers (Observers). Subscribers are interested in tracking changes to the Publisher's state. The naive approach would be for subscribers to constantly check the publisher's state ("polling"). This wastes resources and is inefficient.

Alternatively, the publisher could notify all subscribers whenever something interesting happens. But this creates tight coupling: the publisher must know about all subscriber types.

## Solution

The Observer pattern suggests that you add a subscription mechanism to the publisher class so individual objects can subscribe to or unsubscribe from the stream of events coming from that publisher. This mechanism consists of:

1. An array field for storing a list of references to subscriber objects
2. Several public methods for adding/removing subscribers from that list
3. A notification method that calls an update method on each subscriber

When an important event happens, the publisher goes through its list of subscribers and calls their update method. Subscribers implement a common interface so the publisher doesn't need to know their concrete types.

## Structure

- **Subject (Observable/Publisher)**: Maintains list of observers, provides methods to attach/detach observers, notifies all observers when state changes
- **Observer (Subscriber)**: Defines an updating interface for objects that should be notified of changes
- **ConcreteSubject**: Stores state of interest, sends notifications when state changes
- **ConcreteObserver**: Implements Observer interface, maintains reference to ConcreteSubject, stores state consistent with subject's

## When to Use

- When changes to one object require changing others, and you don't know how many objects need to be changed
- When an object should be able to notify other objects without making assumptions about who these objects are
- When you need to maintain consistency between related objects without making them tightly coupled
- When you want to provide a subscription mechanism for objects to receive notifications

## Real-World Applications

1. **Event Management Systems**: GUI frameworks (button clicks notify all listeners)
2. **Stock Market**: Price changes notify all registered traders/displays
3. **News Feeds**: Publishers broadcast updates to all subscribers
4. **Model-View-Controller (MVC)**: Model notifies views when data changes
5. **Logging Systems**: Log events notify multiple appenders (file, console, database)
6. **Caching**: Cache invalidation notifications across distributed systems
7. **Reactive Programming**: Observable streams (RxJava, Project Reactor)

## Observer vs. Mediator

| Aspect | Observer | Mediator |
|--------|----------|----------|
| **Communication** | One-way from subject to observers | Bidirectional through mediator |
| **Relationship** | Subject knows observers (as interface) | Colleagues only know mediator |
| **Intent** | Notify dependents of state changes | Coordinate interactions between objects |
| **Coupling** | Subjects and observers loosely coupled | Colleagues decoupled from each other |
| **Notification** | Subject broadcasts to all observers | Mediator routes specific messages |

## Observer vs. Pub/Sub

- **Observer**: Direct relationship between subject and observers; observers register with subject
- **Pub/Sub**: Intermediary (event channel/broker) between publishers and subscribers; complete decoupling

## Push vs. Pull Model

**Push Model**: Subject sends detailed information about the change to observers
- Pros: Observers get all necessary data
- Cons: Subject must know what data observers need

**Pull Model**: Subject sends minimal notification; observers query subject for details
- Pros: Subject doesn't need to know observer data requirements
- Cons: Observers must know subject interface; may require multiple queries

## Key Design Decisions

1. **Update Protocol**: Push vs. Pull model
2. **Subject State**: Who triggers notification (subject or client)?
3. **Observer Lifecycle**: How to avoid memory leaks (weak references, unsubscribe)
4. **Notification Order**: Does order of observer notification matter?
5. **Thread Safety**: Handle concurrent modifications to observer list
6. **Fine-Grained Notifications**: Notify only for specific changes vs. all changes

## Benefits

- **Open/Closed Principle**: Introduce new subscriber classes without changing publisher code
- **Runtime Relationships**: Establish relationships between objects at runtime
- **Decoupling**: Publishers and subscribers can vary independently
- **Broadcast Communication**: One-to-many updates

## Drawbacks

- **Unexpected Updates**: Observers may update in unpredictable order
- **Memory Leaks**: Forgetting to unsubscribe can cause memory leaks
- **Performance**: Notifying many observers can be slow
- **Cascading Updates**: Updates can trigger chain reactions
- **Debugging Difficulty**: Hard to trace notification chains

## Implementation Variations

1. **Event Objects**: Pass rich event objects instead of simple notifications
2. **Aspect-Specific Observers**: Observers subscribe to specific aspects/events
3. **Self-Observation**: Subject observes itself (less common)
4. **Change Manager**: Intermediary to reduce redundant updates in complex dependency graphs

## References

- Refactoring Guru: https://refactoring.guru/design-patterns/observer
- Gang of Four: Design Patterns (1994)
- Java's Built-in Observer: `java.util.Observable` (deprecated in Java 9, use PropertyChangeListener or event frameworks)
