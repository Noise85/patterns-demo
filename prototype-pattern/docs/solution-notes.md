# Solution Notes

## Exercise 1: Document Cloning

### Architecture

```
Document
  - title: String (immutable, safe)
  - sections: List<Section> (mutable, needs deep copy)
  + clone(): Document
  
Section
  - heading: String (immutable, safe)
  - content: String (immutable, safe)
  - metadata: Map<String, String> (mutable, needs deep copy)
  + clone(): Section
```

### Key Points

- **Shallow copy**: Copies references (not enough!)
- **Deep copy**: Recursively clones nested objects
- Strings are immutable (safe to share)
- Collections need defensive copying
- Nested objects must be cloned

### Implementation Pattern

```java
public Document clone() {
    // Deep copy sections list
    List<Section> clonedSections = this.sections.stream()
            .map(Section::clone)
            .collect(Collectors.toList());
    
    return new Document(this.title, clonedSections);
}
```

### Common Pitfalls

❌ `new ArrayList<>(original)` for objects (shallow copy of elements)
❌ Sharing mutable nested objects
❌ Forgetting to clone collections
✅ Clone each element in collections
✅ Deep copy all mutable fields
✅ Use copy constructors for clarity

## Exercise 2: Game Entity Spawner

### Architecture

```
GameEntity (interface)
  + clone(): GameEntity
  + getId(): String
  + getName(): String
  + getAttributes(): Map<String, Object>
        ↑
  ┌─────┴─────┐
Enemy        Item
+ clone()    + clone()

PrototypeRegistry
  - prototypes: Map<String, GameEntity>
  + registerPrototype(key, entity)
  + spawnEntity(key): GameEntity
  + hasPrototype(key): boolean

EntitySpawner
  - registry: PrototypeRegistry
  + spawn(type): GameEntity
```

### Key Points

- Registry stores **templates**, never modifies them
- Always clone before returning from registry
- Generate new IDs for cloned entities
- Thread safety with ConcurrentHashMap
- Registry pattern + Prototype pattern combination

### Cloning with New IDs

```java
public Enemy clone() {
    Enemy clone = new Enemy(
        UUID.randomUUID().toString(),  // New ID!
        this.name,
        this.health,
        this.damage,
        new HashMap<>(this.attributes)  // Deep copy
    );
    return clone;
}
```

### Registry Pattern

```java
public GameEntity spawnEntity(String key) {
    GameEntity prototype = prototypes.get(key);
    if (prototype == null) {
        throw new IllegalArgumentException("Unknown prototype: " + key);
    }
    return prototype.clone();  // Always clone!
}
```

### When to Use

✅ Expensive initialization (DB queries, file loading)
✅ Many instances of same configuration
✅ Runtime object composition
❌ Simple objects
❌ Complex deep copy requirements
