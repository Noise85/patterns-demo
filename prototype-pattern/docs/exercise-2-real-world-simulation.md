# Exercise 2: Real-World Simulation

## Title
Game Entity Spawner with Prototype Registry

## Learning Objectives

- Implement prototype registry pattern
- Use cloning for efficient object creation
- Manage prototype templates
- Understand when cloning is more efficient than construction
- Handle prototype customization after cloning

## Scenario

You're building a game engine. Creating enemy and item instances involves expensive initialization: loading stats from database, calculating attributes, parsing AI scripts. Creating thousands of instances per second from scratch is too slow. You'll use a prototype registry to store pre-initialized templates and spawn new instances by cloning.

## Functional Requirements

### 1. GameEntity Interface

- Method: `clone()` returns cloned entity
- Methods: `getId()`, `getName()`, `getAttributes()` (Map<String, Object>)

### 2. Concrete Entities

Implement at least:
- `Enemy` (name, health, damage, behavior)
- `Item` (name, value, effects)

### 3. PrototypeRegistry

- `registerPrototype(String key, GameEntity prototype)` - Store template
- `spawnEntity(String key)` - Clone and return new instance
- `removePrototype(String key)` - Remove template
- `hasPrototype(String key)` - Check existence

### 4. EntitySpawner

- Uses PrototypeRegistry to spawn entities
- `spawn(String entityType)` - Gets entity from registry and clones it
- Optional: `spawnWithModifications(String entityType, Map<String, Object> customizations)`

## Non-Functional Expectations

- Thread-safe registry (use concurrent collections or synchronization)
- Efficient cloning (deep copy only when necessary)
- Each spawn creates independent instance
- Support for entity customization after cloning

## Constraints

- Registry must store prototypes, not create them
- Spawned entities must be independent clones
- Handle missing prototypes gracefully

## Starter Code Location

`src/main/java/com/patterns/prototype/simulation/`

## Acceptance Criteria

✅ All tests in `SimulationExerciseTest.java` pass

## Stretch Goals

1. Add lazy initialization for expensive entity properties
2. Implement prototype versioning
3. Add entity pooling with prototype reset
4. Support prototype inheritance chains

## Hints

<details>
<summary>Click to reveal hints</summary>

- Use `Map<String, GameEntity>` for registry storage
- Clone before returning from registry (never return stored prototype)
- Use copy constructor in entity clone() methods
- Generate new unique IDs for cloned entities
- Consider using `ConcurrentHashMap` for thread safety
</details>
