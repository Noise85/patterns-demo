# Exercise 2: Real-World Simulation

## Title
Organization Chart Composite

## Learning Objectives

- Apply Composite to business domain
- Handle complex recursive aggregations
- Model organizational hierarchies
- Implement multiple operations on composites
- Design flexible employee-department structures

## Scenario

Build an organization chart where:
- **Employees** are leaf nodes (have salary, role)
- **Departments** are composites (contain employees and subdepartments)
- Operations work uniformly: total salary budget, employee count, find by name

## Functional Requirements

### 1. Component Interface (`OrganizationComponent`)

```java
String getName();
double getSalaryBudget();     // Employee returns salary, Dept returns sum of all
int getEmployeeCount();        // Employee returns 1, Dept returns recursive count
OrganizationComponent findByName(String name);  // Recursive search
void printStructure(int depth);
```

### 2. Leaf (`Employee`)

- Fields: name, role, salary
- `getSalaryBudget()` returns individual salary
- `getEmployeeCount()` returns 1
- `findByName()` returns this if name matches, else null

### 3. Composite (`Department`)

- Fields: name, List<OrganizationComponent> members
- Methods: `add(member)`, `remove(member)`, `getMembers()`
- `getSalaryBudget()` sums all members' budgets (recursive)
- `getEmployeeCount()` sums all members' counts (recursive)
- `findByName()` searches recursively through members

## Non-Functional Expectations

- Handle nested departments (3+ levels)
- Efficient recursive traversal
- Uniform operations across hierarchy

## Constraints

- No type-checking with `instanceof` in operations
- Department can contain both employees and subdepartments

## Starter Code Location

`src/main/java/com/patterns/composite/simulation/`

## Acceptance Criteria

✅ All tests in `SimulationExerciseTest.java` pass

## Stretch Goals

1. Add `getAverageSalary()` operation
2. Implement visitor for different traversals
3. Add department manager concept
4. Implement budget allocation warnings

## Hints

<details>
<summary>Click to reveal hints</summary>

- Department.getSalaryBudget(): `members.stream().mapToDouble(OrganizationComponent::getSalaryBudget).sum()`
- Department.findByName(): Check own name first, then recursively search members
- printStructure() uses recursion with increasing depth
- Employee implements interface directly with simple returns
</details>
