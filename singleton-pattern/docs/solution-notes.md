# Solution Notes

## Exercise 1: Configuration Manager

### Architecture

```
ConfigurationManager (Singleton)
  - static volatile instance: ConfigurationManager
  - private constructor
  - properties: Map<String, String>
  + static getInstance(): ConfigurationManager
  + getProperty(key): String
  + setProperty(key, value)
```

### Key Points

- **Private constructor**: Prevents `new ConfigurationManager()`
- **volatile**: Ensures visibility of instance across threads
- **Double-checked locking**: Minimize synchronization overhead
- **Lazy initialization**: Instance created on first `getInstance()` call

### Thread Safety Explained

```java
private static volatile ConfigurationManager instance;  // volatile!

public static ConfigurationManager getInstance() {
    if (instance == null) {              // Check 1: Fast path (no lock)
        synchronized (ConfigurationManager.class) {  // Lock acquired
            if (instance == null) {      // Check 2: Thread-safe creation
                instance = new ConfigurationManager();
            }
        }
    }
    return instance;
}
```

**Why volatile?** Without it, thread A might see partially constructed object.
**Why double-check?** First check avoids locking after initialization.

### Alternative: Bill Pugh (Simpler, Recommended)

```java
private static class Holder {
    static final ConfigurationManager INSTANCE = new ConfigurationManager();
}

public static ConfigurationManager getInstance() {
    return Holder.INSTANCE;  // Lazy + thread-safe via classloader
}
```

## Exercise 2: Connection Pool

### Architecture

```
ConnectionPool (Singleton)
  - static instance (via Bill Pugh)
  - availableConnections: Queue<Connection>
  - poolSize: int
  + getConnection(): Connection
  + releaseConnection(Connection)
  + getAvailableCount(): int
  
Connection
  - id: String
  - valid: boolean
```

### Key Points

- **Bill Pugh singleton**: Static inner class holder
- **Synchronization**: On pool operations (checkout/return)
- **Blocking**: wait() when empty, notifyAll() on return
- **Validation**: Check connection validity before returning

### Pooling Pattern

```java
public synchronized Connection getConnection() throws InterruptedException {
    while (availableConnections.isEmpty()) {
        wait();  // Block until connection available
    }
    Connection conn = availableConnections.poll();
    return conn;
}

public synchronized void releaseConnection(Connection conn) {
    if (conn.isValid()) {
        availableConnections.offer(conn);
        notifyAll();  // Wake waiting threads
    }
}
```

### Singleton Variants Summary

| Variant | Thread-Safe | Lazy | Complexity | Recommended |
|---------|-------------|------|------------|-------------|
| Eager | ✅ | ❌ | Low | Simple cases |
| Double-checked | ✅ | ✅ | High | Advanced only |
| Bill Pugh | ✅ | ✅ | Low | **Best choice** |
| Enum | ✅ | ❌ | Low | Special cases |

### When to Use Each

- **Eager**: Simple, initialization cheap
- **Double-checked locking**: Legacy code only
- **Bill Pugh**: Default choice
- **Enum**: Serialization-safe singletons
