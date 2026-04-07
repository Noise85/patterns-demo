# Solution Notes

## Exercise 1: HTTP Request Builder

### Architecture

```
HttpRequest (immutable product)
  - method, url, headers, queryParams, body, timeoutMs
  + getters
  
  HttpRequest.Builder (nested static class)
    - mutable fields
    + method() -> this
    + url() -> this
    + header(k, v) -> this
    + queryParam(k, v) -> this
    + body(content) -> this
    + timeout(ms) -> this
    + build() -> HttpRequest
```

### Key Points

- Builder is typically a **static nested class** of the product
- Each builder method modifies internal state and **returns `this`**
- `build()` validates, creates immutable product, returns it
- Product fields are **final** (immutability)
- Use defensive copying for collections

### Implementation Tips

**Fluent API**: All builder methods return `this`
```java
return this;  // Enable chaining
```

**Validation**: In `build()` method
```java
if (url == null) {
    throw new IllegalStateException("URL is required");
}
```

**Immutability**: Use `Map.copyOf()` for collections
```java
this.headers = Map.copyOf(builder.headers);
```

## Exercise 2: SQL Query Builder

### Architecture

```
SqlQuery (immutable product)
  - sql: String
  - parameters: List<Object>
  
SqlQueryBuilder
  - columns: List<String>
  - table: String
  - whereClauses: List<String>
  - whereParams: List<Object>
  - joins: List<String>
  - orderByColumns: List<String>
  - limitValue: Integer
  
  + select(cols...) -> this
  + from(table) -> this
  + where(condition, params...) -> this
  + join(table, condition) -> this
  + orderBy(cols...) -> this
  + limit(n) -> this
  + build() -> SqlQuery
```

### Key Points

- Builder accumulates parts, assembles in `build()`
- Parameters collected separately from SQL structure
- Validation ensures query is complete and valid
- Use StringBuilder for efficient SQL assembly

### Common Pitfalls

❌ Concatenating values into SQL (injection risk)
❌ Forgetting to validate required parts
❌ Parameter order doesn't match placeholder order
✅ Use `?` placeholders with separate parameter list
✅ Validate SELECT and FROM in `build()`
✅ Maintain parameter order

### SQL Assembly Pattern

```java
StringBuilder sql = new StringBuilder();
sql.append("SELECT ").append(String.join(", ", columns));
sql.append(" FROM ").append(table);
if (!whereClauses.isEmpty()) {
    sql.append(" WHERE ").append(String.join(" AND ", whereClauses));
}
// ... more clauses
```
