# Design Patterns Education Project

This workspace is dedicated to generating a comprehensive multi-module Maven Java 21 project for teaching design patterns.

## Default Agent

Use the **@design-patterns-educator** agent for all work in this workspace. This agent specializes in:

- Generating pattern modules with TDD exercises
- Validating module structure and code quality  
- Debugging educational scaffolding
- Maintaining consistency across 22 pattern modules
- Following Refactoring Guru as source of truth

To invoke manually: `@design-patterns-educator <task>`

## Project Context

- **Goal**: 22 design pattern modules (5 creational, 7 structural, 10 behavioral)
- **Each module**: 2 exercises (isolation + real-world simulation)
- **Approach**: TDD with starter code, no solutions revealed
- **Domains**: Realistic business scenarios (payments, notifications, pricing, etc.)
- **Progress tracking**: `.generation-progress.md` shows completion status

## Workflow

The agent follows a 4-step systematic process per module:
1. Documentation (pom, README, 3 docs)
2. Source code (isolation + simulation with TODOs)
3. Tests (comprehensive JUnit 5 + AssertJ)
4. Integration (update parent POM + progress tracker)

Continue multi-session work by checking progress tracker and resuming where left off.
