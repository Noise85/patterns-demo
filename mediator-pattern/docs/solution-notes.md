# Solution Notes: Mediator Pattern

## Core Implementation Strategy

### Exercise 1: Chat Room System

**ChatRoom (Mediator) Implementation**:

1. **User Registry**:
   - Use `Map<String, User>` for fast name-based lookup
   - Check for duplicate names on registration
   - Remove user from map when they leave

2. **Message Broadcasting**:
   ```
   For sendMessage(message, sender):
     For each user in registry:
       If user != sender:
         user.receive(sender.getName() + ": " + message)
   ```

3. **Private Messaging**:
   ```
   For sendPrivateMessage(message, sender, recipientName):
     Find recipient in registry by name
     If found:
       recipient.receive(sender.getName() + " (private): " + message)
     Else:
       sender.receive("System: User " + recipientName + " not found")
   ```

4. **Join/Leave Announcements**:
   - On addUser: broadcast "[name] joined the chat" to all existing users, THEN add new user
   - On removeUser: broadcast "[name] left the chat" to all remaining users, THEN remove user

**User (Colleague) Implementation**:

1. **Store Mediator Reference**: Pass in constructor, store as final field
2. **Delegate Communication**: All send methods call mediator methods
3. **Accumulate Messages**: Use `List<String>` to store received messages in order
4. **No Direct References**: User class should have ZERO references to other User instances

**Key Design Decision**: Who formats messages?
- Option 1: Mediator formats, user just stores
- Option 2: User formats on receive
- **Recommended**: Mediator formats (centralized string formatting logic)

### Exercise 2: Smart Home System

**SmartHomeHub Implementation**:

1. **Device Registry Structure**:
   ```java
   private Map<String, SmartDevice> devicesById;
   private Map<DeviceType, List<SmartDevice>> devicesByType;
   private Map<String, List<SmartDevice>> devicesByZone;
   ```
   - Update all three maps on register/unregister
   - Use defensive copies when returning lists

2. **Event Routing**:
   ```java
   public void notify(SmartDevice sender, String event) {
       logEvent(sender, event);
       
       switch(event) {
           case "MOTION_DETECTED" -> handleMotionDetected(sender);
           case "DOOR_UNLOCKED" -> handleDoorUnlocked(sender);
           case "TEMPERATURE_CHANGED" -> handleTemperatureChange(sender);
           // ... other events
       }
   }
   ```

3. **Automation Rules**:
   - Implement each rule as a separate private method
   - Use mode and device zone to determine appropriate response
   - Example:
     ```java
     private void handleMotionDetected(SmartDevice sensor) {
         String zone = sensor.getZone();
         int brightness = (mode == AWAY) ? 100 : 50;
         
         getDevicesByType(LIGHT).stream()
             .filter(d -> d.getZone().equals(zone))
             .map(d -> (SmartLight) d)
             .forEach(light -> light.turnOn(brightness));
         
         if (mode == AWAY) {
             getDevicesByType(SECURITY_CAMERA).stream()
                 .filter(d -> d.getZone().equals(zone))
                 .map(d -> (SecurityCamera) d)
                 .forEach(camera -> camera.startRecording());
         }
     }
     ```

4. **Mode Changes**:
   - setMode() should trigger cascading automation
   - AWAY mode: lock all doors, arm all cameras, turn off all lights, set thermostat to eco
   - HOME mode: disarm cameras, unlock main door, set normal lighting

**SmartDevice Base Class**:

1. **Constructor**: Accept id, name, zone, hub - all required
2. **Protected notifyHub()**: Convenience method for subclasses
3. **Abstract Methods**: getState(), getDescription() - force subclasses to implement
4. **Online Status**: Track isOnline for health monitoring

**Concrete Devices**:

Each device should:
- Maintain device-specific state (brightness, temperature, locked status, etc.)
- Call `notifyHub()` when state changes
- Implement state query methods (isOn(), getTemperature(), isLocked(), etc.)
- Return comprehensive state map for debugging

**Event Logging**:
- Log every significant action (device state change, automation trigger, mode change)
- Use `LocalDateTime.now()` for timestamps
- Store in chronological order
- Include device ID, name, event type, and description

## Common Pitfalls to Avoid

1. **God Object**: Don't overload mediator with business logic unrelated to coordination
2. **Tight Coupling**: Colleagues should only know mediator interface, not concrete implementation
3. **Bidirectional References**: Ensure colleagues hold mediator reference, mediator holds colleague references
4. **Null Checks**: Validate recipient exists before sending private messages
5. **State Synchronization**: Ensure hub's view of device state matches actual device state
6. **Circular Notifications**: Prevent infinite loops (device notifies hub → hub updates device → device notifies hub...)

## Testing Strategy

**Exercise 1**:
- Test message flow (broadcast, private, announcements)
- Test edge cases (empty room, single user, non-existent recipient)
- Verify message ordering
- Confirm users have no direct dependencies

**Exercise 2**:
- Test each automation rule independently
- Test mode transitions
- Test zone-based coordination
- Test event logging completeness
- Test device registry operations
- Test concurrent device events

## Extension Ideas

**Exercise 1**:
- Add typing indicators
- Implement read receipts
- Support multiple chat rooms
- Add user status (online, away, busy)
- Implement message history persistence

**Exercise 2**:
- Add scheduling (turn on lights at sunset)
- Implement energy monitoring
- Support scenes (movie mode, party mode)
- Add voice control integration
- Implement device grouping (all bedroom lights)
- Support conditional automation (if-then rules)

## Pattern Variations

1. **Event-Based Mediator**: Use event objects instead of string event types
2. **Async Mediator**: Use callbacks or CompletableFuture for non-blocking coordination
3. **Hierarchical Mediators**: Split large systems into multiple specialized mediators
4. **Mediator with Observers**: Combine with Observer pattern for notifications

## Performance Considerations

- For large numbers of colleagues, consider indexing by type or category
- Cache frequently accessed device groups
- Use concurrent collections if multi-threaded
- Consider lazy initialization for expensive resources
- Log asynchronously to avoid blocking coordination logic

## Real-World Evolution

As systems grow, consider transitioning to:
- **Event Bus**: For publish-subscribe at scale
- **Message Queue**: For distributed systems
- **CQRS**: For complex state management
- **Saga Pattern**: For long-running coordinated transactions

The Mediator pattern provides the foundation, but production systems often need additional patterns for scalability and resilience.
