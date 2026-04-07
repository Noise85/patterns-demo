# Design Patterns Training

A comprehensive multi-module Maven project for learning design patterns through hands-on exercises based on realistic software engineering scenarios.

## 📚 Purpose

This project helps developers master the 22 classic design patterns from the Gang of Four catalog through:

- **Isolation exercises**: Focused, minimal examples that teach the core intent of each pattern
- **Real-world simulations**: Production-like coding challenges that mirror senior-level engineering tasks

All patterns and terminology follow [Refactoring.Guru](https://refactoring.guru/design-patterns/catalog) as the source of truth.

## 🏗️ Project Structure

```
design-patterns-training/
├── pom.xml (parent)
├── README.md (this file)
│
├── Creational Patterns (5 modules)
│   ├── factory-method-pattern/
│   ├── abstract-factory-pattern/
│   ├── builder-pattern/
│   ├── prototype-pattern/
│   └── singleton-pattern/
│
├── Structural Patterns (7 modules)
│   ├── adapter-pattern/
│   ├── bridge-pattern/
│   ├── composite-pattern/
│   ├── decorator-pattern/
│   ├── facade-pattern/
│   ├── flyweight-pattern/
│   └── proxy-pattern/
│
└── Behavioral Patterns (10 modules)
    ├── chain-of-responsibility-pattern/
    ├── command-pattern/
    ├── iterator-pattern/
    ├── mediator-pattern/
    ├── memento-pattern/
    ├── observer-pattern/
    ├── state-pattern/
    ├── strategy-pattern/
    ├── template-method-pattern/
    └── visitor-pattern/
```

## 🚀 Quick Start

### Prerequisites

- Java 21 or later
- Maven 3.8+ (or use the included Maven wrapper)

### Build All Modules

```bash
./mvnw clean install
```

### Work on a Specific Pattern

```bash
cd strategy-pattern
../mvnw test
```

### Run Tests for a Specific Exercise

```bash
cd strategy-pattern
../mvnw test -Dtest=IsolationExerciseTest
../mvnw test -Dtest=SimulationExerciseTest
```

## 📖 How Exercises Are Organized

Each pattern module contains exactly **two exercises**:

### Exercise 1: Pattern in Isolation

**Goal**: Understand and practice the pattern in a focused, minimal context.

**Characteristics**:
- Small and self-contained
- Centered on the core intent of the pattern
- More guided with clear TODOs
- Perfect for learning what the pattern does

**Example domains**: Simple calculators, basic payment selection, trivial formatters

### Exercise 2: Real-World Simulation

**Goal**: Apply the pattern to a realistic business/domain problem.

**Characteristics**:
- Simulates senior-level coding tasks
- Feels like production code with real constraints
- Includes ambiguity, tradeoffs, and extensibility concerns
- May combine multiple patterns where appropriate
- Tests separation of concerns, maintainability, and clean architecture

**Example domains**: Pricing engines, notification systems, workflow orchestration, fraud detection, approval pipelines

## 📋 Module Structure

Each module follows this consistent structure:

```
<pattern-name>-pattern/
├── pom.xml
├── README.md                              # Pattern overview, when to use, when not to use
├── docs/
│   ├── exercise-1-pattern-in-isolation.md # Detailed exercise 1 instructions
│   ├── exercise-2-real-world-simulation.md # Detailed exercise 2 instructions
│   └── solution-notes.md                   # High-level solution guidance (no code)
└── src/
    ├── main/java/com/patterns/<pattern>/
    │   ├── isolation/                      # Exercise 1 starter code with TODOs
    │   └── simulation/                     # Exercise 2 starter code with TODOs
    └── test/java/com/patterns/<pattern>/
        ├── isolation/                      # Exercise 1 tests (make them pass!)
        └── simulation/                     # Exercise 2 tests (make them pass!)
```

## 🎯 Recommended Study Order

### For Beginners

Start with the most commonly used and easiest to grasp:

1. **Strategy** - Different algorithms, same interface
2. **Observer** - Event notification system
3. **Factory Method** - Object creation with subclasses
4. **Decorator** - Add behavior dynamically
5. **Template Method** - Algorithm skeleton with customizable steps
6. **Adapter** - Make incompatible interfaces work together
7. **Singleton** - Single instance control
8. **Command** - Encapsulate requests as objects

### For Intermediate Developers

Continue with more sophisticated patterns:

9. **Builder** - Construct complex objects step by step
10. **Facade** - Simplified interface to complex subsystem
11. **Proxy** - Control access to objects
12. **State** - Object behavior changes with internal state
13. **Chain of Responsibility** - Pass requests along a chain
14. **Composite** - Tree structures of objects
15. **Iterator** - Sequential access without exposing representation

### For Advanced Developers

Tackle the more specialized patterns:

16. **Abstract Factory** - Families of related objects
17. **Bridge** - Separate abstraction from implementation
18. **Mediator** - Centralize complex communications
19. **Memento** - Capture and restore object state
20. **Prototype** - Clone objects
21. **Flyweight** - Share state among many objects
22. **Visitor** - Operations on object structures

## 🧪 Test-Driven Approach

All exercises follow a **Test-Driven Development** approach:

1. Read the exercise instructions in `docs/`
2. Review the failing tests in `src/test/`
3. Implement the solution in `src/main/`
4. Run tests until they all pass: `mvn test`
5. Refactor and improve your code
6. Check `docs/solution-notes.md` for high-level guidance (if stuck)

**Passing all tests means your solution meets the acceptance criteria!**

## 🎓 Learning Tips

1. **Do the isolation exercise first** - Build confidence with the pattern mechanics
2. **Then tackle the simulation** - Apply your knowledge to realistic scenarios
3. **Don't skip the README** - Each pattern's README explains:
   - What problem it solves
   - When to use it
   - When NOT to use it
4. **Make the tests pass** - Tests are your acceptance criteria
5. **Read hints sparingly** - Struggle a bit before looking at hints
6. **Study solution notes only after** - Try your own solution first
7. **Use your brain, not AI assistants** - The learning happens when you struggle and solve problems yourself. Resist the temptation to ask AI tools for solutions. Think, experiment, fail, and learn.
8. **Understand that implementations are intentionally simplified** - Some exercises present "school-like" implementations to focus on pattern mechanics (e.g., State pattern). In production systems, you'll encounter more complex scenarios requiring advanced techniques.
9. **Experiment beyond the exercises** - Once you pass the tests, challenge yourself:
   - Create new packages within the module (e.g., `isolation-advanced/` or `simulation-complex/`)
   - Implement variations that handle edge cases, concurrency, or performance constraints
   - Combine patterns to solve more sophisticated problems
   - Refactor your solution to support different complexity levels

## 🏆 Pattern Categories

### Creational Patterns
Focus on object creation mechanisms, increasing flexibility and reuse.

- Factory Method, Abstract Factory, Builder, Prototype, Singleton

### Structural Patterns
Explain how to assemble objects and classes into larger structures while keeping them flexible and efficient.

- Adapter, Bridge, Composite, Decorator, Facade, Flyweight, Proxy

### Behavioral Patterns
Concerned with algorithms and the assignment of responsibilities between objects.

- Chain of Responsibility, Command, Iterator, Mediator, Memento, Observer, State, Strategy, Template Method, Visitor

## 🛠️ Technology Stack

- **Java 21** - Latest LTS with modern language features
- **Maven** - Project management and build tool
- **JUnit 5** - Testing framework
- **AssertJ** - Fluent assertions for readable tests
- **Mockito** - Mocking framework (when needed)

## 📚 References

- [Refactoring.Guru Design Patterns Catalog](https://refactoring.guru/design-patterns/catalog)
- [Gang of Four Design Patterns](https://en.wikipedia.org/wiki/Design_Patterns)

## 📝 License

This project is provided for educational purposes.

---

**Happy Learning! 🚀**

Start with any pattern that interests you, or follow the recommended study order above.
