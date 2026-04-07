# Exercise 1: Pattern in Isolation

## Objective

Implement a **Virtual Proxy** that delays the creation of expensive database connections until they're actually needed, reducing startup time and resource usage.

## Scenario

You're building a data access layer where database connections are expensive to establish (network latency, authentication, connection pooling). Creating connections eagerly at application startup wastes resources when some connections may never be used.

A virtual proxy solves this by:
- Implementing the same interface as the real database connection
- Only creating the actual connection when the first query is executed
- Tracking connection statistics (lazy vs. eager creation comparison)

## Your Tasks

### 1. Define Subject Interface

Create `DatabaseConnection` interface:
```java
String executeQuery(String sql)
List<String> executeBatch(List<String> queries)
void close()
boolean isConnected()
```

### 2. Implement Real Subject

Create `RealDatabaseConnection`:
- Simulates expensive connection establishment (delay or logging)
- Implements all `DatabaseConnection` methods
- Tracks when connection was created
- Constructor: `RealDatabaseConnection(String url, String username, String password)`
- Constructor should simulate expensive operation (e.g., log "Establishing connection to {url}...")
- `executeQuery()`: Return simulated result like "Result for: {sql}"
- `executeBatch()`: Execute multiple queries, return results
- `isConnected()`: Returns true after construction
- `close()`: Closes connection

### 3. Create Virtual Proxy

Create `DatabaseConnectionProxy` implements `DatabaseConnection`:
- Holds connection parameters (url, username, password)
- **Lazy creation**: `RealDatabaseConnection` created only on first method call requiring it
- Delegates all operations to real connection after creation
- Tracks:
  - Whether real connection has been created
  - When it was created (for statistics)
  - Total number of operations

**Key behavior**: 
- Constructor is lightweight (just stores parameters)
- First call to `executeQuery()` or `executeBatch()` triggers real connection creation
- `isConnected()` checks if real connection exists (doesn't trigger creation)
- Subsequent calls reuse the same real connection

### 4. Statistics Tracking

Add to proxy:
```java
boolean isRealConnectionCreated()
long getCreationTimestamp()  // When real connection was created
int getOperationCount()       // Number of queries executed
```

### 5. Demonstrate Lazy Loading

Show the difference:

**Without proxy (eager)**:
```java
// Connection created immediately, even if not used
RealDatabaseConnection conn = new RealDatabaseConnection(url, user, pass);
// Expensive operation happened here ^
// ... maybe never actually use the connection
```

**With proxy (lazy)**:
```java
// Lightweight proxy created
DatabaseConnectionProxy proxy = new DatabaseConnectionProxy(url, user, pass);
// No expensive operation yet

// Real connection created only when needed
proxy.executeQuery("SELECT * FROM users");  // NOW the connection is established
```

## Example Usage

```java
// Create proxy (lightweight)
DatabaseConnection proxy = new DatabaseConnectionProxy(
    "jdbc:postgresql://localhost/mydb",
    "admin",
    "secret"
);

// At this point, no real connection exists
assert !((DatabaseConnectionProxy) proxy).isRealConnectionCreated();

// First query triggers connection creation
String result = proxy.executeQuery("SELECT * FROM users");

// Now real connection exists
assert ((DatabaseConnectionProxy) proxy).isRealConnectionCreated();
assert result.contains("Result for");

// Subsequent queries reuse existing connection
proxy.executeQuery("SELECT * FROM orders");
assert ((DatabaseConnectionProxy) proxy).getOperationCount() == 2;
```

## Testing Strategy

Your implementation must pass tests that verify:

1. **Lazy initialization**: Real connection not created in proxy constructor
2. **Deferred creation**: Real connection created on first query
3. **Reuse**: Same real connection used for all subsequent operations
4. **Delegation**: Proxy correctly forwards all operations
5. **Statistics**: Operation count and creation tracking work correctly
6. **Same interface**: Proxy and real connection are interchangeable
7. **Batch operations**: Batch queries also trigger lazy initialization
8. **Connection status**: `isConnected()` reflects actual state

## Success Criteria

- [ ] All tests in `IsolationExerciseTest.java` pass
- [ ] Proxy implements same interface as real connection
- [ ] Real connection created lazily, not in constructor
- [ ] Subsequent operations reuse the same connection
- [ ] Statistics accurately track creation and usage
- [ ] Code demonstrates clear performance benefit of lazy loading

## Time Estimate

**45-60 minutes** for a developer familiar with the proxy pattern.

## Hints

- Store connection parameters in proxy constructor, don't create real connection yet
- Use a `RealDatabaseConnection` field initialized to `null`
- Check if field is `null` before each operation; create if needed
- After creation, delegate all operations to the real connection
- Track first creation time with `System.currentTimeMillis()`
- Use a counter for tracking operation count
- Consider extracting "ensure connection created" logic to a private method
- Remember: proxy should be transparent - clients can't tell difference from real object
