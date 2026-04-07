# Exercise 1: Pattern in Isolation

## Objective

Implement the Flyweight pattern for a text editor that renders characters efficiently by sharing glyph (character appearance) objects across all character instances with the same font and style.

## Scenario

You're building a text editor that displays documents with potentially millions of characters. Each character has:

**Intrinsic state** (shared across all instances of the same character):
- Character value (e.g., 'A', 'b', '5')
- Font family (e.g., "Arial", "Times New Roman")
- Font size (e.g., 12, 14, 16)
- Style (e.g., BOLD, ITALIC, REGULAR)

**Extrinsic state** (unique per character instance in the document):
- Position (x, y coordinates)
- Color (foreground color for this specific instance)

Without the Flyweight pattern, a 1,000,000 character document with the same font settings would create 1,000,000 separate character objects. With flyweights, you might only need ~100 shared glyph objects (one per unique character with that font/style combination).

## Your Tasks

### 1. Define Intrinsic State

Create `FontStyle` enum:
- `REGULAR`, `BOLD`, `ITALIC`, `BOLD_ITALIC`

Create `CharacterGlyph` (the Flyweight):
- Stores intrinsic state: `char value`, `String fontFamily`, `int fontSize`, `FontStyle style`
- Immutable (all fields final)
- Method: `render(int x, int y, String color)` - renders character at position with color
- Use `record` for immutability

### 2. Create Flyweight Factory

Create `GlyphFactory`:
- Maintains cache of created glyphs: `Map<GlyphKey, CharacterGlyph>`
- `getGlyph(char value, String fontFamily, int fontSize, FontStyle style)` → returns cached or creates new
- Track cache hit/miss statistics for testing
- `getCacheSize()` to verify sharing is working

Create `GlyphKey` (for cache lookup):
- Composite key using all intrinsic state fields
- Properly implement `equals()` and `hashCode()`
- Use `record` for automatic equality

### 3. Create Context Objects

Create `CharacterInstance`:
- Holds reference to shared `CharacterGlyph`
- Stores extrinsic state: `int x`, `int y`, `String color`
- Method: `render()` - delegates to glyph with extrinsic state

### 4. Demonstrate Memory Savings

Create `TextDocument`:
- Stores list of `CharacterInstance` objects
- Uses `GlyphFactory` to create characters
- Method: `addCharacter(char value, int x, int y, String color, font details)`
- Method: `render()` - renders all characters
- Method: `getUniqueGlyphCount()` - shows how many actual glyph objects exist

## Example Usage

```java
GlyphFactory factory = new GlyphFactory();
TextDocument doc = new TextDocument(factory);

// Add same character multiple times
doc.addCharacter('A', 10, 20, "black", "Arial", 12, FontStyle.REGULAR);
doc.addCharacter('A', 50, 20, "black", "Arial", 12, FontStyle.REGULAR);
doc.addCharacter('A', 90, 20, "red", "Arial", 12, FontStyle.REGULAR);

// Only ONE CharacterGlyph created for 'A' Arial 12 REGULAR
// Three CharacterInstance objects with different positions/colors
assert factory.getCacheSize() == 1;

// Add different character
doc.addCharacter('B', 130, 20, "black", "Arial", 12, FontStyle.REGULAR);

// Now TWO glyphs in cache
assert factory.getCacheSize() == 2;

// Same character, different font
doc.addCharacter('A', 170, 20, "black", "Times", 12, FontStyle.REGULAR);

// Three glyphs now (different font = different intrinsic state)
assert factory.getCacheSize() == 3;
```

## Testing Strategy

Your implementation must pass tests that verify:

1. **Glyph sharing**: Same character with same font reuses glyph object
2. **Factory caching**: Factory returns same object for identical intrinsic state
3. **Extrinsic state independence**: Different positions/colors don't create new glyphs
4. **Memory efficiency**: Document with 1000 identical characters uses only 1 glyph
5. **Different fonts create different glyphs**: Font is part of intrinsic state
6. **Immutability**: Glyphs cannot be modified after creation

## Success Criteria

- [ ] All tests in `IsolationExerciseTest.java` pass
- [ ] CharacterGlyph is immutable
- [ ] GlyphFactory properly caches and reuses objects
- [ ] Clear separation between intrinsic and extrinsic state
- [ ] Memory savings are measurable and significant

## Time Estimate

**45-60 minutes** for a developer familiar with caching patterns.

## Hints

- Use Java records for immutable value objects
- `GlyphKey` needs proper `equals()`/`hashCode()` - records provide this automatically
- Factory should use `Map.computeIfAbsent()` for clean caching logic
- Track cache statistics (hits/misses) to verify sharing is working
- Consider using identity comparison (`==`) in tests to verify object reuse
- Remember: intrinsic state = what's shared, extrinsic state = what's unique per instance
