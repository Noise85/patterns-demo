# Exercise 2: Real-World Simulation

## Title
SQL Query Builder with Validation

## Learning Objectives

- Build complex, validated objects step-by-step
- Implement production-ready builder with safety checks
- Handle conditional construction logic
- Generate parameterized output to prevent injection
- Design extensible builder architecture

## Scenario

You're building a database abstraction layer. Raw SQL strings are error-prone and vulnerable to injection. You need a type-safe query builder that constructs valid SELECT queries with WHERE clauses, JOINs, and ordering while preventing SQL injection through parameterization.

## Functional Requirements

### 1. Product Class (`SqlQuery`)

- Immutable query representation
- Fields: `sql` (String), `parameters` (List of values)
- Methods: `getSql()`, `getParameters()`

### 2. Builder Class (`SqlQueryBuilder`)

Fluent methods:
- `select(String... columns)` - SELECT clause
- `from(String table)` - FROM clause
- `where(String condition, Object... params)` - WHERE clause (can be called multiple times for AND)
- `join(String table, String condition)` - JOIN clause
- `orderBy(String... columns)` - ORDER BY clause
- `limit(int limit)` - LIMIT clause
- `build()` - Returns immutable `SqlQuery`

### 3. Query Generation

- Generate valid SQL with `?` placeholders
- Collect parameters in order
- Handle multiple WHERE conditions with AND
- Validate required clauses (must have SELECT and FROM)

### 4. Validation

- SELECT and FROM are required
- ORDER BY columns can't be empty if specified
- Limit must be positive
- Throw `IllegalStateException` for invalid queries

## Non-Functional Expectations

- Thread-safe query generation
- Proper SQL formatting (spaces, keywords)
- Parameter order matches placeholder order
- Extensible for future query types (UPDATE, DELETE)

## Constraints

- Use parameterized queries (no string concatenation of values)
- `SqlQuery` immutable
- Builder validates before building

## Starter Code Location

`src/main/java/com/patterns/builder/simulation/`

## Acceptance Criteria

✅ All tests in `SimulationExerciseTest.java` pass

## Stretch Goals

1. Add GROUP BY and HAVING clauses
2. Support INNER/LEFT/RIGHT JOIN types
3. Add OR support for WHERE clauses
4. Implement UPDATE and DELETE builders

## Hints

<details>
<summary>Click to reveal hints</summary>

- Store query parts (columns, table, conditions) in builder fields
- Assemble SQL string in `build()` method
- Use `List<Object>` to collect parameters from WHERE clauses
- Validate required parts before generating SQL
- Consider using StringBuilder for SQL construction
</details>
