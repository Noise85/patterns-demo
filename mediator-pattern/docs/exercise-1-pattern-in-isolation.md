# Exercise 1: Mediator Pattern in Isolation

## Objective

Implement a **chat room system** where users communicate through a mediator rather than directly with each other. This exercise demonstrates the core Mediator pattern: centralizing communication logic and eliminating direct dependencies between colleagues.

## Scenario

You're building a chat application where users can:
- Send messages to other users
- Send messages to everyone in the room
- Receive messages from other users
- Join and leave chat rooms

**Key Constraint**: Users should NOT know about other users directly. All communication flows through the ChatRoom mediator.

## Requirements

### 1. ChatMediator Interface

Define the mediator interface:

```java
public interface ChatMediator {
    void sendMessage(String message, User sender);
    void sendPrivateMessage(String message, User sender, String recipientName);
    void addUser(User user);
    void removeUser(User user);
}
```

### 2. ChatRoom (ConcreteMediator)

Implement the mediator:

- **State**: Maintains a list of all users in the room
- **sendMessage()**: Broadcasts a message to all users except the sender
- **sendPrivateMessage()**: Sends message to a specific recipient by name
- **addUser()**: Registers a new user and announces their arrival
- **removeUser()**: Unregisters a user and announces their departure

**Implementation Notes**:
- Store users in a `List<User>` or `Map<String, User>` for name lookup
- When broadcasting, iterate through users and call their `receive()` method
- For private messages, find the recipient by name and deliver only to them
- Announce join/leave events by calling `receive()` on all users

### 3. User (Colleague)

Implement the colleague class:

- **Fields**:
  - `name`: User's display name
  - `mediator`: Reference to the ChatMediator
  - `messages`: List storing received messages for verification
  
- **Methods**:
  - `send(String message)`: Delegates to `mediator.sendMessage()`
  - `sendPrivate(String message, String recipientName)`: Delegates to `mediator.sendPrivateMessage()`
  - `receive(String message)`: Receives a message and stores it
  - `getMessages()`: Returns list of received messages (for testing)

**Key Point**: User class should NOT have references to other User instances. All communication is mediated.

## Design Characteristics

1. **Decoupling**: Users don't know about each other's existence
2. **Centralized Logic**: All routing and broadcasting logic is in ChatRoom
3. **Extensibility**: New message types (e.g., typing indicators, read receipts) added only to mediator
4. **Single Responsibility**: User handles display/storage, ChatRoom handles routing

## Expected Behavior

```java
ChatMediator chatRoom = new ChatRoom();

User alice = new User("Alice", chatRoom);
User bob = new User("Bob", chatRoom);
User charlie = new User("Charlie", chatRoom);

chatRoom.addUser(alice);
chatRoom.addUser(bob);
chatRoom.addUser(charlie);

// Alice broadcasts: Bob and Charlie receive
alice.send("Hello everyone!");
// Bob's messages: ["Alice joined the chat", "Charlie joined the chat", "Alice: Hello everyone!"]

// Bob sends private message to Alice: Only Alice receives
bob.sendPrivate("Hi Alice, can we talk?", "Alice");
// Alice's messages: ["Bob joined the chat", "Charlie joined the chat", "Alice: Hello everyone!", "Bob (private): Hi Alice, can we talk?"]
// Charlie's messages: ["Alice joined the chat", "Bob joined the chat", "Alice: Hello everyone!"]

chatRoom.removeUser(charlie);
// Bob's messages: [..., "Charlie left the chat"]
```

## Testing Focus

Your tests should verify:

1. ✅ Users can send broadcast messages through the mediator
2. ✅ Broadcast messages reach all users except the sender
3. ✅ Users can send private messages to specific recipients
4. ✅ Private messages reach only the intended recipient
5. ✅ Join announcements are broadcast to existing users
6. ✅ Leave announcements are broadcast to remaining users
7. ✅ Users accumulate messages in order received
8. ✅ Messages are formatted with sender's name
9. ✅ Users have no direct references to other users
10. ✅ Private messages to non-existent users are handled gracefully

## Implementation Tasks

1. ✅ Implement `ChatMediator` interface
2. ✅ Implement `ChatRoom` with user management and message routing
3. ✅ Implement `User` with mediator-based communication
4. ✅ Add proper message formatting (e.g., "Alice: message" vs "Bob (private): message")
5. ✅ Handle edge cases (empty room, invalid recipient, user leaving mid-chat)

## Key Takeaway

This exercise demonstrates the Mediator pattern's core value: **reducing coupling by centralizing communication**. Users are completely independent of each other, making it easy to add new users, change message routing, or extend functionality without modifying user code.
