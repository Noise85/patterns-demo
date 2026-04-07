# Exercise 2: Real-World Simulation

## Objective

Build a production-grade game forest rendering system using the Flyweight pattern to efficiently manage thousands of tree instances by sharing tree type data (models, textures, behaviors).

## Business Context

You're developing an open-world game with vast forests containing hundreds of thousands of trees. Each tree has:

**Intrinsic state** (shared among all trees of the same type):
- Tree species name (e.g., "Oak", "Pine", "Birch")
- 3D model data (simulated as model identifier)
- Texture set (bark, leaves)
- Growth behavior constants (max height, growth rate)
- Physics properties (wind resistance, collision bounds)

**Extrinsic state** (unique per tree instance):
- Position (x, y, z coordinates)
- Current height (varies as tree grows)
- Health percentage (0-100)
- Rotation angle

Loading separate model/texture data for each tree would be prohibitively expensive. With flyweights, a forest of 100,000 trees with 10 species only needs 10 heavy objects (tree types) instead of 100,000.

## Domain Model

### Tree Type (Flyweight)
Represents shared data for all trees of a species:
- `String species`
- `String modelId`
- `String textureSet`
- `double maxHeight`
- `double growthRate`
- `double windResistance`

### Tree Instance
Represents a specific tree in the world:
- Reference to `TreeType` (flyweight)
- `double x, y, z` (position)
- `double currentHeight`
- `int health` (0-100)
- `double rotation`

### Forest
Manages all tree instances and the tree type factory

## Your Tasks

### 1. Implement Tree Type Flyweight

Create `TreeType`:
- Immutable object storing all intrinsic state
- `render(TreeInstance instance)` - simulates rendering with both intrinsic and extrinsic data
- `calculateWindEffect(double windSpeed, TreeInstance instance)` - uses wind resistance + tree height
- Use record or final fields

### 2. Create Flyweight Factory

Create `TreeTypeFactory`:
- Maintains cache of tree types by species name
- `getTreeType(String species)` - returns cached or creates new with predefined data
- Predefined tree species:
  - **Oak**: maxHeight=25m, growthRate=0.3, windResistance=0.7
  - **Pine**: maxHeight=35m, growthRate=0.5, windResistance=0.4
  - **Birch**: maxHeight=20m, growthRate=0.4, windResistance=0.6
  - **Maple**: maxHeight=30m, growthRate=0.35, windResistance=0.65
  - **Willow**: maxHeight=15m, growthRate=0.25, windResistance=0.3
- Methods:
  - `getCacheSize()` - number of unique types loaded
  - `getMemoryFootprint()` - estimate memory usage
  - `clearCache()` - for testing

### 3. Create Tree Instance

Create `TreeInstance`:
- Holds reference to shared `TreeType`
- Stores extrinsic state (position, height, health, rotation)
- Methods:
  - `render()` - delegates to TreeType
  - `grow(double deltaTime)` - increases height based on TreeType's growth rate
  - `takeDamage(int damage)` - reduces health
  - `applyWind(double windSpeed)` - calculates sway using TreeType's wind resistance

### 4. Create Forest Manager

Create `Forest`:
- Stores collection of `TreeInstance` objects
- Uses `TreeTypeFactory` to create trees
- Methods:
  - `plantTree(String species, double x, double y, double z)` - creates new tree instance
  - `renderAll()` - renders all trees
  - `simulate(double deltaTime)` - grows all trees
  - `applyWind(double windSpeed)` - affects all trees
  - `getTreeCount()` - total trees in forest
  - `getUniqueTypeCount()` - number of unique tree types (shows sharing)
  - `getMemorySavings()` - calculates memory saved by sharing

### 5. Statistics and Metrics

Implement memory tracking:
- Calculate theoretical memory without flyweight (each tree = full tree type data)
- Calculate actual memory with flyweight (shared tree types)
- Track sharing efficiency: (trees - unique types) / trees * 100%

## Example Usage

```java
TreeTypeFactory typeFactory = new TreeTypeFactory();
Forest forest = new Forest(typeFactory);

// Plant 10,000 oak trees
for (int i = 0; i < 10000; i++) {
    forest.plantTree("Oak", 
        Math.random() * 1000, 
        0, 
        Math.random() * 1000
    );
}

// Plant 5,000 pine trees
for (int i = 0; i < 5000; i++) {
    forest.plantTree("Pine", 
        Math.random() * 1000, 
        0, 
        Math.random() * 1000
    );
}

// Only 2 TreeType objects created, despite 15,000 trees!
assert typeFactory.getCacheSize() == 2;
assert forest.getTreeCount() == 15000;
assert forest.getUniqueTypeCount() == 2;

// Simulate growth
forest.simulate(1.0); // 1 second

// Apply wind to all trees
forest.applyWind(15.0); // 15 m/s wind

// Render the forest
forest.renderAll();

// Check memory savings
System.out.println(forest.getMemorySavings()); // e.g., "99.98% memory saved"
```

## Testing Strategy

Your implementation must handle:

1. **Basic flyweight sharing**: Multiple trees of same species share TreeType
2. **Multiple species**: Different species create different flyweights
3. **Large-scale simulation**: 100,000+ trees perform well
4. **Growth simulation**: Trees grow according to their type's growth rate
5. **Wind effects**: Wind resistance varies by tree type
6. **Memory calculations**: Accurate memory savings reported
7. **Rendering delegation**: TreeType renders with extrinsic state from instance
8. **Cache efficiency**: Factory maintains minimal cache size

## Success Criteria

- [ ] All tests in `SimulationExerciseTest.java` pass
- [ ] TreeType objects are properly shared across instances
- [ ] Factory caches tree types by species
- [ ] Clear separation of intrinsic vs. extrinsic state
- [ ] Memory savings are significant and measurable
- [ ] Simulation methods properly combine shared and instance state
- [ ] Code is production-quality and thread-safe

## Time Estimate

**2-3 hours** for a senior developer.

## Advanced Challenges

If you complete the base requirements:

1. **Spatial partitioning**: Add quadtree for efficient tree queries by region
2. **LOD (Level of Detail)**: Use different TreeType instances for near/far rendering
3. **Serialization**: Save/load forest with proper flyweight reconstruction
4. **Multi-threading**: Parallelize forest simulation safely
5. **Dynamic loading**: Lazy-load tree types only when first used
6. **Memory pooling**: Pool TreeInstance objects for frequent plant/remove operations

## Hints

- Use Java records for immutable TreeType
- Factory pattern + caching = flyweight factory
- Calculate memory: TreeType size * unique types vs. TreeType size * total trees
- Sharing ratio = 1 - (unique types / total instances)
- Consider using WeakHashMap for factory cache to allow garbage collection
- For wind effects, combine TreeType's resistance with instance's current height
- Growth calculation: `newHeight = min(currentHeight + growthRate * deltaTime, maxHeight)`
- Track statistics separately for testing verification
- Remember: flyweight is about separating what's shared from what's unique
