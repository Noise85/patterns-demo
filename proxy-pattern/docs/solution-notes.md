# Solution Notes

**IMPORTANT**: This document provides guidance on the approach and design considerations. It does NOT contain implementation code. Students must implement the solution themselves.

## Exercise 1: Pattern in Isolation (Virtual Proxy)

### Design Approach

**Virtual Proxy Purpose**: Defer expensive object creation until first use.

**Key Components**:

1. **Subject Interface** (`DatabaseConnection`):
   - Defines operations common to real and proxy
   - Pure abstraction, no implementation

2. **Real Subject** (`RealDatabaseConnection`):
   - Expensive to create (network, authentication, etc.)
   - Contains actual business logic
   - No awareness of proxy pattern

3. **Proxy** (`DatabaseConnectionProxy`):
   - Lightweight constructor (just stores parameters)
   - Lazy creation on first method call
   - Delegates to real connection after creation

**Lazy Initialization Pattern**:

```
Proxy Constructor:
  - Store connection parameters
  - Set realConnection = null
  - No expensive operations

First Operation Call:
  if (realConnection == null):
    realConnection = new RealDatabaseConnection(params)
    recordCreationTime()
  return realConnection.operation()
  
Subsequent Calls:
  return realConnection.operation()  // Already created
```

**Statistics Tracking**:
- `isRealConnectionCreated()`: Check if `realConnection != null`
- `getCreationTimestamp()`: Record `System.currentTimeMillis()` at creation
- `getOperationCount()`: Increment on each query

**Implementation Notes**:

Private helper method:
```
ensureConnected():
  if (realConnection == null):
    realConnection = new RealDatabaseConnection(...)
    creationTimestamp = System.currentTimeMillis()
```

Each public method:
```
executeQuery(sql):
  ensureConnected()
  operationCount++
  return realConnection.executeQuery(sql)
```

**Benefits Demonstrated**:

Without proxy:
- 10 connection objects created at startup
- Only 3 actually used
- 7 connections wasted resources

With proxy:
- 10 lightweight proxies created
- Real connections created only for 3 used ones
- 70% resource savings

### Common Pitfalls

- Creating real connection in proxy constructor (defeats purpose)
- Forgetting to delegate to real connection after creation
- Not maintaining single real connection instance (creating multiple)
- Not tracking statistics correctly
- Breaking interface substitution (proxy should be transparent)

## Exercise 2: Real-World Simulation (Protection Proxy)

### Design Approach

**Protection Proxy Purpose**: Add access control layer without modifying real object.

**Core Architecture**:

1. **Permission Model**:
   - Roles: VIEWER, EDITOR, ADMIN
   - Permissions: READ, EDIT, DELETE, SET_PERMISSIONS
   - Mapping: Role → Set<Permission>

2. **Real Document**:
   - Pure business logic
   - No security checks
   - Stores content, metadata
   - Stateful (content can change)

3. **Document Proxy**:
   - Wraps real document
   - Knows current user
   - Checks permissions before delegation
   - Logs all operations
   - Throws exception on unauthorized access

**Permission Mapping**:

```
static Map<Role, Set<Permission>> ROLE_PERMISSIONS = {
  VIEWER -> {READ},
  EDITOR -> {READ, EDIT},
  ADMIN -> {READ, EDIT, DELETE, SET_PERMISSIONS}
}

hasPermission(User user, Permission required):
  return ROLE_PERMISSIONS.get(user.role()).contains(required)
```

**Operation Flow**:

Every proxy method follows pattern:
```
operation():
  1. Check if document is deleted
  2. Determine required permission
  3. if (!hasPermission(currentUser, requiredPermission)):
       logOperation(operation, false, "Access denied")
       throw AccessDeniedException(...)
  4. logOperation(operation, true, "Success")
  5. return realDocument.operation()
```

**Audit Logging**:

Structure:
```
AuditEntry {
  timestamp: LocalDateTime.now()
  username: currentUser.username()
  operation: "getContent" / "editContent" / etc.
  successful: true/false
  details: "Content read" / "Access denied" / etc.
}

Store in: List<AuditEntry> auditLog
```

Statistics:
```
getSuccessfulOperations():
  return auditLog.stream().filter(e -> e.successful()).count()

getFailedOperations():
  return auditLog.stream().filter(e -> !e.successful()).count()
```

**Exception Design**:

```
AccessDeniedException:
  message = "User 'alice' with role VIEWER cannot perform editContent (requires EDIT)"
  
  Constructor params:
    - username
    - operation name
    - user's role
    - required permission
```

**Factory Pattern Integration**:

Why use factory:
- Hides proxy creation complexity
- Ensures real document always wrapped in proxy
- Returns interface type (client doesn't know it's a proxy)
- Centralizes secure document creation

```
createSecureDocument(title, content, author, requiredRole, currentUser):
  realDoc = new RealDocument(title, content, author, requiredRole)
  return new DocumentProxy(realDoc, currentUser)
```

### Permission Matrix

| Operation | VIEWER | EDITOR | ADMIN |
|-----------|--------|--------|-------|
| getContent | ✓ | ✓ | ✓ |
| getMetadata | ✓ | ✓ | ✓ |
| editContent | ✗ | ✓ | ✓ |
| delete | ✗ | ✗ | ✓ |
| setRequiredRole | ✗ | ✗ | ✓ |

### Testing Strategy

**Access Control Tests**:
- Create proxy for each role
- Attempt each operation
- Verify success/failure matches permission matrix
- Check exception messages are clear

**Audit Trail Tests**:
- Perform operations (success and failure)
- Verify audit log entries created
- Check timestamps, usernames, operations recorded
- Verify statistics counts match

**Transparency Tests**:
- Proxy and real document both implement Document
- Can substitute one for the other
- Type checks work correctly

**Edge Cases**:
- Operating on deleted documents
- Null users
- Empty content
- Multiple failed attempts
- Changing required role mid-operation

### Common Pitfalls

- Adding security logic to RealDocument (violates separation of concerns)
- Not logging failed operations
- Forgetting to check document deletion state
- Incorrect permission mappings
- Not delegating after successful check
- Breaking interface substitution
- Weak exception messages
- Not tracking audit trail correctly

## Design Pattern Insights

### Virtual Proxy vs. Lazy Initialization

**Virtual Proxy** (object-level):
- Separate proxy class implementing interface
- Transparent to client
- Can add additional behavior (logging, stats)

**Lazy Initialization** (field-level):
- Direct field initialization on first access
- Simpler but less flexible
- No separate abstraction

Virtual proxy is more powerful when:
- Need to track statistics
- Want to add multiple behaviors
- Working with interfaces/substitution

### Protection Proxy vs. Decorator

**Similarities**:
- Both wrap objects
- Both implement same interface
- Both add behavior

**Differences**:

Protection Proxy:
- Focus: Access control, security
- Decision: Allow or deny operation
- Can prevent delegation entirely
- Typically one wrapper per security context

Decorator:
- Focus: Adding responsibilities
- Always delegates (enhances, doesn't block)
- Can stack multiple decorators
- Composition of behaviors

### When NOT to Use Proxy

❌ **Avoid proxy when:**
- Object creation is cheap (no lazy loading benefit)
- No access control needed (protection)
- Direct access is fine (no security concerns)
- Complexity outweighs benefits
- Performance overhead matters more than control

✅ **Use proxy when:**
- Expensive object creation (virtual)
- Access control required (protection)
- Cross-cutting concern to add (smart proxy)
- Remote object access (remote proxy)
- Resource management needed (caching, pooling)

### Proxy Pattern in the Wild

**Java Examples**:
- `java.lang.reflect.Proxy` - Dynamic proxies
- JPA/Hibernate - Lazy loading of entities
- Spring AOP - Method interceptors
- RMI - Remote method invocation

**Design Principles**:
- **Open/Closed**: Add functionality without modifying real object
- **Single Responsibility**: Proxy handles cross-cutting concern
- **Liskov Substitution**: Proxy substitutable for real object
- **Interface Segregation**: Proxy implements subject interface

## Verification Checklist

### Virtual Proxy
- [ ] Real object created lazily, not in constructor
- [ ] Subsequent calls reuse same real object
- [ ] Proxy and real object implement same interface
- [ ] Statistics track creation and usage correctly
- [ ] No expensive operations in proxy constructor

### Protection Proxy
- [ ] Permission checks before all operations
- [ ] Clear exception messages for access denials
- [ ] Comprehensive audit logging
- [ ] Real document contains no security logic
- [ ] Proxy transparent to clients (same interface)
- [ ] Audit statistics accurate
- [ ] All roles have correct permissions
