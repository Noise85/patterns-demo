# Exercise 1: State Pattern in Isolation

## Objective

Implement the State pattern for a simple document workflow system. You'll create states for different stages of document review and publishing.

## Scenario

You're building a content management system where documents progress through a workflow:
1. **Draft**: Initial creation, can be edited freely
2. **Review**: Submitted for review, no editing allowed
3. **Published**: Approved and published, read-only, can be archived

Each state has different allowed operations:
- **Draft**: edit(), submit()
- **Review**: approve(), reject()
- **Published**: archive()

Invalid operations should throw `IllegalStateException`.

## Requirements

### 1. State Interface

Define a common interface for all states:

```java
interface DocumentState {
    void edit(Document document);
    void submit(Document document);
    void approve(Document document);
    void reject(Document document);
    void archive(Document document);
    
    String getStateName();
}
```

### 2. Concrete States

Implement three state classes:

#### DraftState
- `edit()`: Allowed - increments edit count
- `submit()`: Allowed - transitions to ReviewState
- Other operations: Throw `IllegalStateException`

#### ReviewState
- `approve()`: Allowed - transitions to PublishedState
- `reject()`: Allowed - transitions back to DraftState
- Other operations: Throw `IllegalStateException`

#### PublishedState
- `archive()`: Allowed - transitions to ArchivedState
- Other operations: Throw `IllegalStateException`

#### ArchivedState
- All operations: Throw `IllegalStateException`
- Represents terminal state

### 3. Document (Context)

The Document class maintains current state:

```java
class Document {
    private DocumentState currentState;
    private String content;
    private int editCount;
    
    // Starts in Draft state
    public Document(String title) {
        this.content = title;
        this.currentState = new DraftState();
        this.editCount = 0;
    }
    
    // Delegates to current state
    public void edit() {
        currentState.edit(this);
    }
    
    public void setState(DocumentState newState) {
        this.currentState = newState;
    }
}
```

## Implementation Guidelines

### DraftState Example
```java
public class DraftState implements DocumentState {
    
    @Override
    public void edit(Document document) {
        // TODO: Increment edit count
        // This is allowed in Draft state
    }
    
    @Override
    public void submit(Document document) {
        // TODO: Transition to ReviewState
        // document.setState(new ReviewState());
    }
    
    @Override
    public void approve(Document document) {
        // TODO: Throw IllegalStateException
        // Cannot approve a draft directly
    }
    
    @Override
    public String getStateName() {
        return "Draft";
    }
}
```

### State Transitions
```
Draft ──submit()──> Review ──approve()──> Published ──archive()──> Archived
          ▲                    │
          │                    │
          └────── reject() ────┘
```

### Document Methods
The Document class should provide:
- `edit()`: Delegate to `currentState.edit(this)`
- `submit()`: Delegate to `currentState.submit(this)`
- `approve()`: Delegate to `currentState.approve(this)`
- `reject()`: Delegate to `currentState.reject(this)`
- `archive()`: Delegate to `currentState.archive(this)`
- `getCurrentStateName()`: Return `currentState.getStateName()`
- `getEditCount()`: Return edit count
- `incrementEditCount()`: Increment edit count (called by DraftState)

## Example Usage

```java
Document doc = new Document("My Article");

// In Draft state
doc.edit();  // OK - increments edit count
doc.edit();  // OK - increments edit count
System.out.println(doc.getEditCount());  // 2

doc.submit();  // Transitions to Review
System.out.println(doc.getCurrentStateName());  // "Review"

// In Review state
try {
    doc.edit();  // Throws IllegalStateException
} catch (IllegalStateException e) {
    System.out.println("Cannot edit in Review state");
}

doc.approve();  // Transitions to Published
System.out.println(doc.getCurrentStateName());  // "Published"

// In Published state
doc.archive();  // Transitions to Archived
```

## Key Learning Points

1. **State Encapsulation**: Each state handles its own behavior
2. **Transition Logic**: States control their own transitions
3. **Context Delegation**: Document delegates all operations to current state
4. **Invalid Operations**: Clear error handling for disallowed operations
5. **Open/Closed Principle**: Add new states without modifying existing ones

## Testing

The test suite verifies:
- ✅ Document starts in Draft state
- ✅ Edit allowed only in Draft state
- ✅ Submit transitions Draft → Review
- ✅ Approve transitions Review → Published
- ✅ Reject transitions Review → Draft
- ✅ Archive transitions Published → Archived
- ✅ Invalid operations throw IllegalStateException
- ✅ State names are correct
- ✅ Edit count tracked correctly

## Challenge Questions

1. What if you need to add a "Pending Approval" state between Review and Published?
2. How would you implement "undo" functionality to revert to previous state?
3. Could you make states singletons? What are the trade-offs?
4. How would you prevent cyclic transitions (e.g., Published → Draft)?

## Common Pitfalls

- ❌ Putting state transition logic in Document instead of states
- ❌ Using instanceof checks in Document to determine state
- ❌ Sharing mutable data between state instances
- ❌ Forgetting to throw exceptions for invalid operations
- ❌ Not delegating all operations to states
