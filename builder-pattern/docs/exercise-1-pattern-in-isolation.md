# Exercise 1: Pattern in Isolation

## Title
HTTP Request Builder

## Learning Objectives

- Construct complex objects with many optional parameters
- Implement fluent API with method chaining
- Create immutable result objects
- Validate object construction
- Understand builder vs constructor approach

## Scenario

You're building an HTTP client library. HTTP requests have many optional components: headers, query parameters, body, timeout, authentication. Creating requests with constructors is unwieldy. You need a clean, type-safe way to build requests.

## Functional Requirements

1. **Product Class** (`HttpRequest`):
   - Required: `method` (GET, POST, etc.), `url`
   - Optional: `headers` (Map), `queryParams` (Map), `body` (String), `timeoutMs` (Integer)
   - Immutable once constructed
   - Getter methods for all fields

2. **Builder Class** (`HttpRequest.Builder`):
   - Fluent methods: `method()`, `url()`, `header()`, `queryParam()`, `body()`, `timeout()`
   - Method chaining (each method returns `this`)
   - `build()` method returns immutable `HttpRequest`

3. **Validation**:
   - Method and URL are required
   - Validate URL format (starts with http:// or https://)
   - Throw `IllegalStateException` on invalid build

## Non-Functional Expectations

- Type-safe builder API
- Clear error messages for validation failures
- Defaults: GET method, empty headers/params, null body, 30000ms timeout

## Constraints

- `HttpRequest` must be immutable (all fields final)
- Builder can be reused to create multiple requests
- No setters on `HttpRequest`

## Starter Code Location

`src/main/java/com/patterns/builder/isolation/`

## Acceptance Criteria

✅ All tests in `IsolationExerciseTest.java` pass

## Hints

<details>
<summary>Click to reveal hints</summary>

- Builder methods return `this` for chaining
- Store values in builder fields, copy to product in `build()`
- Use `Map.copyOf()` for immutable collections
- Validate in `build()` before creating the product
</details>
