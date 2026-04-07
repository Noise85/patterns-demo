# Exercise 2: Real-World Simulation

## Title
Database Connection Pool Manager

## Learning Objectives

- Build production-ready singleton for resource management
- Handle connection pooling with thread safety
- Implement resource lifecycle management
- Design blocking pool with wait/notify
- Practice singleton with complex state

## Scenario

You're building a database abstraction layer. Creating database connections is expensive (network handshake, authentication). You need a single connection pool that creates a fixed number of connections upfront, hands them out to threads, and reuses them after return. The pool must be a singleton to prevent multiple pools competing for resources.

## Functional Requirements

### 1. ConnectionPool Singleton

- **Private constructor**: Initialize pool with fixed connection count
- **Static getInstance()**: Returns the single pool instance
- **Connection management**:
  - `Connection getConnection()` - Get available connection (blocks if none available)
  - `Connection getConnection(long timeoutMs)` - Get with timeout
  - `void releaseConnection(Connection conn)` - Return connection to pool
  - `int getPoolSize()` - Total pool capacity
  - `int getAvailableCount()` - Currently available connections
  - `int getInUseCount()` - Currently checked out connections

### 2. Connection Class (Simple Simulation)

- Represents database connection
- Fields: `id` (String), `isValid` (boolean)
- Methods: `getId()`, `isValid()`, `close()` (marks invalid)

### 3. Pooling Behavior

- Create fixed number of connections on initialization
- Track available and in-use connections
- Block when pool exhausted until connection returned
- Validate connections before returning them
- Handle connection timeout

### 4. Thread Safety

- Thread-safe connection checkout/return
- Use synchronization/locks appropriately
- No race conditions in pool state

## Non-Functional Expectations

- Thread-safe under high concurrency
- Efficient blocking/unblocking on pool exhaustion
- Connection validation before reuse
- Clean resource management

## Constraints

- Pool size fixed at creation (e.g., 5 connections)
- Must use singleton pattern (Bill Pugh or double-checked locking)
- Handle InterruptedException appropriately

## Starter Code Location

`src/main/java/com/patterns/singleton/simulation/`

## Acceptance Criteria

✅ All tests in `SimulationExerciseTest.java` pass

## Stretch Goals

1. Add connection health checks
2. Implement connection refresh/recreation
3. Add pool shutdown method
4. Implement connection timeout with auto-return
5. Add connection usage statistics

## Hints

<details>
<summary>Click to reveal hints</summary>

- Use LinkedList or Queue for available connections
- Synchronize on pool operations (checkout/return)
- Use wait() when pool exhausted, notify() when connection returned
- Bill Pugh singleton (static inner helper class) is cleanest for this
- Track in-use connections separately or calculate as (total - available)

**Bill Pugh Pattern:**
```java
public class ConnectionPool {
    private ConnectionPool() { /* initialize pool */ }
    
    private static class Holder {
        private static final ConnectionPool INSTANCE = new ConnectionPool();
    }
    
    public static ConnectionPool getInstance() {
        return Holder.INSTANCE;
    }
}
```
</details>
