# Command Pattern

**Category**: Behavioral Design Pattern

## What is the Command Pattern?

The Command pattern encapsulates a request as an object, allowing you to parameterize clients with different requests, queue or log requests, and support undoable operations. It decouples the object that invokes the operation from the one that knows how to perform it.

### Key Characteristics

- **Encapsulation**: Request details wrapped in command objects
- **Decoupling**: Invoker doesn't know about receiver implementation
- **Queuing**: Commands can be stored, scheduled, or queued
- **Undo/Redo**: Commands track state for reversibility
- **Logging**: Command history for audit trails
- **Macro commands**: Composite commands execute multiple operations

## Structure

```
Client
   ↓ creates
Command (interface)
   ↓ implements
ConcreteCommand
   ↓ holds reference to
Receiver
   ↓ performs
actual work

Invoker
   ↓ stores and executes
Command
```

### Core Components

**Command Interface**:
```java
interface Command {
    void execute();
    void undo();
}
```

**Concrete Command**:
```java
class PasteTextCommand implements Command {
    private TextEditor editor;  // Receiver
    private String text;
    private int position;
    
    void execute() {
        editor.insertText(position, text);
    }
    
    void undo() {
        editor.deleteText(position, text.length());
    }
}
```

**Invoker**:
```java
class TextEditorInvoker {
    private Stack<Command> history = new Stack<>();
    
    void executeCommand(Command cmd) {
        cmd.execute();
        history.push(cmd);
    }
    
    void undo() {
        if (!history.isEmpty()) {
            history.pop().undo();
        }
    }
}
```

## When to Use the Command Pattern

### Ideal Scenarios

- **Undo/Redo operations**: Text editors, drawing programs
- **Task scheduling**: Job queues, cron-like systems
- **Transaction systems**: Database operations, sagas
- **Macro recording**: Sequence of commands replayed together
- **Request logging**: Audit trails, event sourcing
- **Callback mechanisms**: GUI buttons, menu items
- **Delayed execution**: Commands stored and executed later
- **Remote procedure calls**: Network commands

### Real-World Examples

- **Text editors**: Cut, copy, paste, undo, redo
- **Database transactions**: BEGIN, COMMIT, ROLLBACK
- **Task schedulers**: Cron jobs, background processing
- **GUI frameworks**: Button clicks, menu actions
- **Game input**: Player actions with replay capability
- **Smart home**: Device control commands with scheduling
- **CI/CD pipelines**: Build steps, deployment stages
- **Event sourcing**: Store commands as events

## Why Use the Command Pattern?

### Benefits

✅ **Decoupling**: Separates operation invoker from performer  
✅ **Undo/Redo**: Easy to implement reversible operations  
✅ **Queuing**: Commands can be scheduled or batched  
✅ **Logging**: Track all operations for audit or replay  
✅ **Macro commands**: Combine multiple operations  
✅ **Open/Closed**: Add new commands without changing invoker  
✅ **Testability**: Commands easy to test in isolation

### Trade-offs

⚠️ **Class proliferation**: Many command classes for small operations  
⚠️ **Memory overhead**: Storing command history for undo  
⚠️ **Complexity**: May be overkill for simple operations  
⚠️ **State management**: Tracking state for undo can be complex

## Command vs. Strategy Pattern

| Aspect | Command | Strategy |
|--------|---------|----------|
| **Intent** | Encapsulate request | Encapsulate algorithm |
| **Focus** | What to do | How to do it |
| **State** | Often stores parameters | Usually stateless |
| **Undo** | Supports undo/redo | Typically doesn't |
| **Timeline** | Can be queued/delayed | Executed immediately |

## Types of Commands

### Simple Command
Execute single operation, no undo support.

### Undoable Command
Track state before execution, support undo().

### Macro Command
Composite command executing multiple sub-commands.

### Transactional Command
All-or-nothing execution with rollback.

### Async Command
Commands executed asynchronously with callbacks.

## Common Implementations

### Basic Structure
```java
interface Command {
    void execute();
}

class ConcreteCommand implements Command {
    private Receiver receiver;
    private String param;
    
    void execute() {
        receiver.action(param);
    }
}
```

### With Undo
```java
interface Command {
    void execute();
    void undo();
}

class UndoableCommand implements Command {
    private State previousState;
    
    void execute() {
        previousState = captureState();
        performAction();
    }
    
    void undo() {
        restoreState(previousState);
    }
}
```

### Macro Command
```java
class MacroCommand implements Command {
    private List<Command> commands;
    
    void execute() {
        commands.forEach(Command::execute);
    }
    
    void undo() {
        // Undo in reverse order
        for (int i = commands.size() - 1; i >= 0; i--) {
            commands.get(i).undo();
        }
    }
}
```

## Exercises

This module contains two hands-on TDD exercises:

1. **Pattern in Isolation** (`exercise-1-pattern-in-isolation.md`)  
   Focus: Text editor with undo/redo for Insert, Delete, Replace commands

2. **Real-World Simulation** (`exercise-2-real-world-simulation.md`)  
   Focus: Task scheduling system with job queue, retry, and execution history

## Learning Objectives

After completing these exercises, you will:

- Encapsulate operations as command objects
- Implement undo/redo functionality with command history
- Build command invokers that manage execution
- Create macro commands (composite operations)
- Design queueing and scheduling systems
- Handle command failures and retries
- Implement audit logging through command tracking
- Apply commands to realistic business scenarios

## References

- [Refactoring Guru - Command Pattern](https://refactoring.guru/design-patterns/command)
- Gang of Four Design Patterns
- Java's `Runnable` interface - simple command pattern
- Transaction processing systems - real-world command usage
