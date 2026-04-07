# Chain of Responsibility Pattern

**Category**: Behavioral Design Pattern

## What is the Chain of Responsibility Pattern?

The Chain of Responsibility pattern allows passing requests along a chain of handlers. Each handler decides either to process the request or to pass it to the next handler in the chain. This decouples the sender of a request from its receivers.

### Key Characteristics

- **Handler chain**: Multiple handlers linked together
- **Request passing**: Request flows through chain until handled
- **Decoupling**: Sender doesn't know which handler will process the request
- **Dynamic behavior**: Chain can be modified at runtime
- **Conditional processing**: Each handler decides if it can handle the request

## Types of Chain Behavior

### Pure Chain
Only one handler processes the request (stops after first successful handling).  
**Example**: Exception handling, event bubbling

### Impure Chain
Multiple handlers can process the same request (all handlers in chain execute).  
**Example**: Logging pipeline, middleware stack

### Conditional Chain
Handler decides whether to process AND whether to continue the chain.  
**Example**: Validation pipeline, filter chain

## When to Use the Chain of Responsibility Pattern

### Ideal Scenarios

- **Multiple processors**: Several objects can handle a request
- **Unknown handler**: Don't know in advance which handler will process
- **Dynamic chain**: Set of handlers changes at runtime
- **Avoid coupling**: Sender shouldn't know about all possible receivers
- **Sequential processing**: Request needs to pass through multiple stages
- **Early termination**: Want to stop processing once a handler succeeds

### Real-World Examples

- **Logging frameworks**: DEBUG → INFO → WARN → ERROR handlers
- **Authentication/Authorization**: Multiple authentication strategies
- **Validation pipelines**: Field validators → Form validators → Business rule validators
- **Support ticket routing**: L1 → L2 → L3 support escalation
- **Middleware**: Web server request processing (CORS → Auth → Rate Limiting → Handler)
- **Approval workflows**: Manager → Director → VP approval chain
- **Exception handling**: Try-catch blocks in nested scopes

## Why Use the Chain of Responsibility Pattern?

### Benefits

✅ **Reduced coupling**: Sender doesn't need to know handlers  
✅ **Flexibility**: Add/remove handlers dynamically  
✅ **Single Responsibility**: Each handler has one clear task  
✅ **Open/Closed**: Add new handlers without modifying existing code  
✅ **Runtime configuration**: Build different chains for different scenarios

### Trade-offs

⚠️ **No guarantee**: Request might not be handled by any handler  
⚠️ **Performance**: Request may traverse entire chain  
⚠️ **Debugging**: Harder to trace which handler processed request  
⚠️ **Chain setup**: Requires proper chain configuration

## Structure

```
Client
   ↓
Handler (abstract)
   ├─→ ConcreteHandler1 → ConcreteHandler2 → ConcreteHandler3
   ↓
   handle(request):
     if canHandle(request):
       process(request)
     else if next != null:
       next.handle(request)
```

Each handler:
1. Checks if it can handle the request
2. If yes: processes it (and optionally continues chain)
3. If no: passes to next handler

## Common Implementations

### Abstract Handler Base
```java
abstract class Handler {
    protected Handler next;
    
    void setNext(Handler next) {
        this.next = next;
    }
    
    void handle(Request request) {
        if (canHandle(request)) {
            process(request);
        } else if (next != null) {
            next.handle(request);
        }
    }
    
    abstract boolean canHandle(Request request);
    abstract void process(Request request);
}
```

### Fluent Chain Building
```java
Handler chain = new HandlerA()
    .setNext(new HandlerB()
        .setNext(new HandlerC()));
```

## Exercises

This module contains two hands-on TDD exercises:

1. **Pattern in Isolation** (`exercise-1-pattern-in-isolation.md`)  
   Focus: Logging system with severity-based handler chain (DEBUG → INFO → WARN → ERROR)

2. **Real-World Simulation** (`exercise-2-real-world-simulation.md`)  
   Focus: Support ticket routing system with escalation chain and SLA tracking

## Learning Objectives

After completing these exercises, you will:

- Build handler chains for sequential processing
- Implement conditional handling logic (can this handler process the request?)
- Create both pure chains (single handler) and impure chains (all handlers)
- Design fluent chain builders for easy configuration
- Handle edge cases (no handler accepts, chain exhaustion)
- Apply the pattern to realistic business scenarios

## References

- [Refactoring Guru - Chain of Responsibility Pattern](https://refactoring.guru/design-patterns/chain-of-responsibility)
- Gang of Four Design Patterns
- Servlet Filters (Java EE) - classic Chain of Responsibility example
- Express.js middleware - JavaScript's popular chain pattern
