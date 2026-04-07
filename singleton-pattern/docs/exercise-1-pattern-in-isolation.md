# Exercise 1: Pattern in Isolation

## Title
Thread-Safe Configuration Manager

## Learning Objectives

- Implement thread-safe singleton with lazy initialization
- Understand double-checked locking pattern
- Handle concurrent access properly
- Implement global access point
- Practice defensive initialization

## Scenario

You're building an application that loads configuration from a file. Loading configuration is expensive (file I/O, parsing). You need one configuration manager instance shared across the application, created lazily only when first needed, with thread-safe initialization.

## Functional Requirements

### 1. ConfigurationManager Class

- **Private constructor**: Prevent external instantiation
- **Static getInstance()**: Returns the single instance
- **Thread-safe lazy initialization**: Create instance only on first access
- **Configuration storage**: Map<String, String> for key-value pairs
- **Methods**:
  - `String getProperty(String key)` - Get configuration value
  - `String getProperty(String key, String defaultValue)` - Get with default
  - `void setProperty(String key, String value)` - Update configuration
  - `Map<String, String> getAllProperties()` - Get all configuration

### 2. Thread Safety Requirements

- Only one instance created even under concurrent access
- Use double-checked locking pattern
- Instance field must be `volatile`
- Synchronize only on first initialization

### 3. Initialization

- Load configuration in constructor (simulate expensive operation)
- Provide some default values

## Non-Functional Expectations

- Thread-safe under concurrent getInstance() calls
- Lazy initialization (instance created on first use, not class loading)
- Minimal synchronization overhead (double-checked locking)

## Constraints

- Constructor must be private
- Instance field must be volatile
- Use double-checked locking (not synchronized method)

## Starter Code Location

`src/main/java/com/patterns/singleton/isolation/`

## Acceptance Criteria

✅ All tests in `IsolationExerciseTest.java` pass

## Hints

<details>
<summary>Click to reveal hints</summary>

**Double-Checked Locking Pattern:**
```java
private static volatile ConfigurationManager instance;

public static ConfigurationManager getInstance() {
    if (instance == null) {  // First check (no locking)
        synchronized (ConfigurationManager.class) {
            if (instance == null) {  // Second check (with locking)
                instance = new ConfigurationManager();
            }
        }
    }
    return instance;
}
```

- volatile ensures visibility across threads
- First check avoids synchronization overhead after initialization
- Second check ensures only one instance is created
</details>
