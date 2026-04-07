# Solution Notes

**IMPORTANT**: This document provides guidance on the approach and design considerations. It does NOT contain implementation code. Students must implement the solution themselves.

## Exercise 1: Pattern in Isolation

### Design Approach

**Core Architecture**:
- `CharacterGlyph` (Flyweight): Immutable, stores intrinsic state
- `GlyphKey`: Composite key for cache lookup
- `GlyphFactory`: Manages glyph pool with caching
- `CharacterInstance`: Stores extrinsic state + reference to shared glyph

**Intrinsic vs. Extrinsic State**:

Intrinsic (shared):
- Character value ('A', 'B', etc.)
- Font family, size, style
- Stored in `CharacterGlyph`

Extrinsic (unique per instance):
- Position (x, y)
- Color
- Passed to `render()` method

**Factory Implementation**:

1. **Cache structure**: `Map<GlyphKey, CharacterGlyph>`
2. **Key design**: Use record with all intrinsic fields
3. **Cache lookup**: `getGlyph()` checks cache first, creates if missing
4. **Statistics tracking**: Count hits, misses, total requests

Example factory method:
```
getGlyph(char, font, size, style):
  key = new GlyphKey(char, font, size, style)
  return cache.computeIfAbsent(key, k -> new CharacterGlyph(...))
```

**GlyphKey Considerations**:
- Must implement proper `equals()` and `hashCode()`
- Records provide this automatically
- All fields must participate in equality

**Rendering Flow**:
```
CharacterInstance.render():
  glyph.render(this.x, this.y, this.color)
```

### Common Pitfalls

- Making glyphs mutable (breaks thread safety)
- Including position/color in intrinsic state (defeats purpose)
- Not implementing proper equals/hashCode for cache key
- Creating new glyph instead of checking cache
- Not using identity (`==`) to verify object reuse in tests

## Exercise 2: Real-World Simulation

### Design Approach

**Core Architecture**:
- `TreeType` (Flyweight): Immutable, heavy shared data
- `TreeTypeFactory`: Species-based cache
- `TreeInstance`: Lightweight, stores position and instance-specific state
- `Forest`: Manages all tree instances

**Memory Model**:

Without Flyweight:
- Each tree: TreeType data (~KB) + instance data (~bytes)
- 100,000 trees = ~100 MB

With Flyweight:
- Shared TreeTypes: 10 species * ~KB = ~10 KB
- Instances: 100,000 * ~bytes = ~100 KB
- Total: ~110 KB (99.9% savings)

**Factory Design**:

Predefined species data:
```
Map<String, TreeTypeData> TREE_SPECS = {
  "Oak" -> {model, texture, maxHeight=25, growth=0.3, wind=0.7},
  "Pine" -> {model, texture, maxHeight=35, growth=0.5, wind=0.4},
  ...
}

getTreeType(species):
  return cache.computeIfAbsent(species, s -> {
    spec = TREE_SPECS.get(s)
    return new TreeType(spec...)
  })
```

**Tree Instance Implementation**:

State:
- `TreeType type` (reference to shared flyweight)
- `double x, y, z`
- `double currentHeight`
- `int health`
- `double rotation`

Methods delegate to TreeType with extrinsic state:
```
grow(deltaTime):
  increment = type.growthRate * deltaTime
  currentHeight = min(currentHeight + increment, type.maxHeight)

render():
  type.render(this) // passes entire instance for context
```

**Forest Management**:

```
plantTree(species, x, y, z):
  treeType = factory.getTreeType(species)
  instance = new TreeInstance(treeType, x, y, z, initialHeight, health, rotation)
  trees.add(instance)

simulate(deltaTime):
  for tree in trees:
    tree.grow(deltaTime)

applyWind(windSpeed):
  for tree in trees:
    sway = calculateSway(tree.type.windResistance, tree.currentHeight, windSpeed)
    // apply sway effect
```

**Memory Calculations**:

Theoretical without flyweight:
- Assume TreeType = 1000 bytes (model, texture, constants)
- TreeInstance overhead = 50 bytes
- Total per tree = 1050 bytes
- 100,000 trees = 105 MB

With flyweight:
- TreeType * unique species = 1000 * 10 = 10 KB
- TreeInstance * total = 50 * 100,000 = 5 MB
- Total = ~5 MB

Savings = (105 MB - 5 MB) / 105 MB = ~95%

**Wind Effect Calculation**:
```
calculateWindEffect(windSpeed, instance):
  // Lower wind resistance = more sway
  // Taller trees = more sway
  baseSway = windSpeed * (1 - type.windResistance)
  heightFactor = instance.currentHeight / type.maxHeight
  return baseSway * heightFactor
```

### Technical Considerations

**Thread Safety**:
- TreeType must be immutable (all fields final)
- Factory cache needs synchronization or use ConcurrentHashMap
- TreeInstance can be mutable (belongs to single thread typically)

**Performance**:
- Factory lookup should be O(1) with good hash function
- Large forests benefit from spatial indexing (not required for base exercise)
- Rendering can be parallelized (each tree independent)

**Testing Strategy**:
- Create forest with known tree counts/species
- Verify factory cache size matches unique species
- Test memory calculations with small numbers
- Verify growth: height increases by growthRate * time
- Verify wind: effect varies by species resistance
- Test identity: same species returns same TreeType object

### Common Pitfalls

- Making TreeType mutable
- Storing position in TreeType (extrinsic state in intrinsic object)
- Not caching TreeType objects
- Incorrect memory calculations
- Forgetting to pass extrinsic state to TreeType methods
- Creating separate factory instances (should be singleton or shared)
- Not considering thread safety for shared objects

## Design Pattern Insights

### When to Use Flyweight

✅ **Use when:**
- Creating many objects with shared state
- Memory is constrained
- Object identity doesn't matter
- Extrinsic state can be computed or passed in

❌ **Avoid when:**
- Few objects (overhead not worth it)
- No shared state
- Objects need unique identity
- Simplicity is more important than memory

### Flyweight vs. Other Patterns

**Flyweight vs. Singleton**:
- Flyweight: Multiple shared instances (one per unique state)
- Singleton: Exactly one instance globally

**Flyweight vs. Object Pool**:
- Flyweight: Share for memory efficiency, objects distinguished by state
- Object Pool: Reuse for creation cost, objects are interchangeable

**Flyweight vs. Prototype**:
- Flyweight: Share existing objects
- Prototype: Clone to create new objects

### Memory Profiling

To verify flyweight effectiveness:
1. Count unique flyweight objects
2. Count total instances using those flyweights
3. Calculate sharing ratio: (total - unique) / total
4. Estimate memory: unique * size(flyweight) vs. total * size(full object)

## Verification Checklist

- [ ] Flyweight objects are immutable
- [ ] Factory properly caches and reuses flyweights
- [ ] Clear separation of intrinsic vs. extrinsic state
- [ ] Extrinsic state passed to flyweight methods
- [ ] Identity tests verify object reuse (use `==`)
- [ ] Memory savings calculated and significant
- [ ] Thread safety considered for shared objects
- [ ] Factory uses proper cache key with correct equals/hashCode
