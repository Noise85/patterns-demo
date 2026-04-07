# Exercise 2: Real-World Simulation

## Objective

Build a production-grade document management system using the **Protection Proxy** pattern to enforce role-based access control (RBAC) with comprehensive logging and audit trails.

## Business Context

You're developing an enterprise document management system where documents contain sensitive information. Different users have different permission levels:

- **Viewers**: Can only read documents
- **Editors**: Can read and edit documents
- **Admins**: Can read, edit, delete, and change permissions

The protection proxy ensures:
- All access is permission-checked before operations
- Every operation is logged for audit compliance
- Security logic is centralized, not scattered across business code
- Failed access attempts are tracked
- Transparent to clients (same interface as real document)

## Domain Model

### User & Permissions
- `User`: username, role (VIEWER, EDITOR, ADMIN)
- `Role`: VIEWER, EDITOR, ADMIN
- Permission mappings:
  - VIEWER → read
  - EDITOR → read, edit
  - ADMIN → read, edit, delete, setPermissions

### Document
- `Document` interface with operations:
  - `String getContent()`
  - `void editContent(String newContent)`
  - `void delete()`
  - `void setRequiredRole(Role role)`
  - `String getMetadata()` (title, author, created date, etc.)

### Real Document
- `RealDocument`: Actual document implementation
- Stores content, metadata, required role
- No security checks (pure business logic)

### Protection Proxy
- `DocumentProxy`: Adds security layer
- Checks user permissions before each operation
- Logs all operations (successful and failed)
- Maintains audit trail
- Throws `AccessDeniedException` for unauthorized access

## Your Tasks

### 1. Define Core Types

Create `Role` enum:
- `VIEWER`, `EDITOR`, `ADMIN`

Create `User` record:
- `String username`
- `Role role`

Create `Permission` enum (for granular permissions):
- `READ`, `EDIT`, `DELETE`, `SET_PERMISSIONS`

### 2. Create Document Interface

Create `Document` interface:
```java
String getContent()
void editContent(String newContent)
void delete()
void setRequiredRole(Role role)
String getMetadata()
boolean isDeleted()
```

### 3. Implement Real Document

Create `RealDocument` implements `Document`:
- Fields: `String title`, `String content`, `String author`, `LocalDateTime created`, `Role requiredRole`, `boolean deleted`
- No security checks - just business logic
- `getMetadata()`: Returns formatted metadata string
- `delete()`: Sets `deleted = true`
- All methods should work without any permission checks

### 4. Create Protection Proxy

Create `DocumentProxy` implements `Document`:
- Holds reference to `RealDocument`
- Holds reference to current `User`
- Maintains audit log: `List<AuditEntry>`
- Before each operation:
  1. Check if user has required permission
  2. Throw `AccessDeniedException` if not authorized
  3. Log the operation (success or failure)
  4. Delegate to real document if authorized

**Permission rules**:
- `getContent()`, `getMetadata()`: Requires READ permission
- `editContent()`: Requires EDIT permission
- `delete()`: Requires DELETE permission
- `setRequiredRole()`: Requires SET_PERMISSIONS permission

**Role to Permission mapping**:
- VIEWER: READ
- EDITOR: READ, EDIT
- ADMIN: READ, EDIT, DELETE, SET_PERMISSIONS

### 5. Audit Trail

Create `AuditEntry` record:
- `LocalDateTime timestamp`
- `String username`
- `String operation` (e.g., "getContent", "editContent")
- `boolean successful`
- `String details` (e.g., "Access denied" or "Content modified")

Proxy should track:
```java
List<AuditEntry> getAuditLog()
long getSuccessfulOperations()
long getFailedOperations()
```

### 6. Custom Exception

Create `AccessDeniedException` extends `RuntimeException`:
- Constructor: `AccessDeniedException(String username, String operation, Role userRole, Permission required)`
- Message format: "User '{username}' with role {userRole} cannot perform {operation} (requires {required})"

### 7. Document Factory

Create `DocumentFactory`:
```java
static Document createSecureDocument(String title, String content, String author, Role requiredRole, User currentUser)
```
- Creates `RealDocument`
- Wraps in `DocumentProxy` with current user
- Returns as `Document` interface (transparent)

## Example Usage

```java
User viewer = new User("alice", Role.VIEWER);
User editor = new User("bob", Role.EDITOR);
User admin = new User("charlie", Role.ADMIN);

// Create document requiring VIEWER role
Document doc = DocumentFactory.createSecureDocument(
    "Q4 Report",
    "Confidential earnings data...",
    "Finance Team",
    Role.VIEWER,
    viewer
);

// Viewer can read
String content = doc.getContent();  // ✓ Success (logged)

// Viewer cannot edit
doc.editContent("Modified!");  // ✗ Throws AccessDeniedException (logged)

// Switch user context (in real app, would be different proxy instance)
Document docForEditor = DocumentFactory.createSecureDocument(
    "Q4 Report",
    "Confidential earnings data...",
    "Finance Team",
    Role.VIEWER,
    editor
);

// Editor can read and edit
docForEditor.getContent();  // ✓ Success
docForEditor.editContent("Updated numbers");  // ✓ Success

// But cannot delete
docForEditor.delete();  // ✗ Throws AccessDeniedException

// Check audit log
DocumentProxy proxy = (DocumentProxy) docForEditor;
System.out.println(proxy.getAuditLog());  // Shows all operations
```

## Testing Strategy

Your implementation must handle:

1. **Basic access control**: Each role has correct permissions
2. **Read permissions**: All roles can read (if document allows)
3. **Edit permissions**: Only EDITOR and ADMIN can edit
4. **Delete permissions**: Only ADMIN can delete
5. **Set permissions**: Only ADMIN can change required role
6. **Access denied exceptions**: Proper exception thrown with details
7. **Audit logging**: All operations logged (success and failure)
8. **Audit statistics**: Counts of successful/failed operations accurate
9. **Transparency**: Proxy implements same interface as real document
10. **Document state**: Real document unaffected by failed attempts
11. **Metadata access**: Metadata access follows same permission rules
12. **Deleted documents**: Can't operate on deleted documents

## Success Criteria

- [ ] All tests in `SimulationExerciseTest.java` pass
- [ ] Protection proxy enforces all permissions correctly
- [ ] Comprehensive audit logging of all operations
- [ ] Clear exception messages for access violations
- [ ] Real document contains no security logic
- [ ] Proxy and real document are interchangeable (same interface)
- [ ] Production-quality code with proper error handling

## Time Estimate

**2-3 hours** for a senior developer.

## Advanced Challenges

If you complete the base requirements:

1. **Hierarchical permissions**: Implement permission inheritance
2. **Time-based access**: Add expiring permissions
3. **Content encryption**: Proxy transparently encrypts/decrypts content
4. **Caching proxy**: Cache read operations for performance
5. **Multi-tenancy**: Add organization-level access control
6. **Audit export**: Export audit log to CSV/JSON
7. **Permission delegation**: Users can grant temporary access to others

## Hints

- Create a helper method `hasPermission(User user, Permission permission)` in proxy
- Map roles to permissions using a static `Map<Role, Set<Permission>>`
- Log operations in try-finally to capture both success and failure
- Use `LocalDateTime.now()` for audit timestamps
- Check document deletion state before all operations
- Consider using a builder pattern for audit entries
- Real document should be completely unaware of security
- Proxy pattern key: same interface, additional behavior before/after delegation
- Protection proxy = decorator with access control focus
- Test both happy path (authorized) and sad path (unauthorized)
