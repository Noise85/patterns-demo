package com.patterns.flyweight.isolation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Flyweight factory for character glyphs.
 * Maintains a cache of shared glyph objects.
 */
public class GlyphFactory {
    
    private final Map<GlyphKey, CharacterGlyph> cache = new ConcurrentHashMap<>();
    private int cacheHits = 0;
    private int cacheMisses = 0;
    
    /**
     * Gets or creates a character glyph with the specified intrinsic state.
     * Returns cached glyph if one exists, otherwise creates and caches new one.
     *
     * @param value the character value
     * @param fontFamily the font family
     * @param fontSize the font size
     * @param style the font style
     * @return shared CharacterGlyph instance
     */
    public CharacterGlyph getGlyph(char value, String fontFamily, int fontSize, FontStyle style) {
        // TODO: Implement flyweight factory logic
        // 1. Create GlyphKey from parameters
        // 2. Check if glyph exists in cache
        // 3. If exists: increment cacheHits and return cached glyph
        // 4. If not: increment cacheMisses, create new glyph, cache it, return it
        // Hint: Use Map.computeIfAbsent() for clean implementation
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Returns the number of unique glyphs in the cache.
     *
     * @return cache size
     */
    public int getCacheSize() {
        return cache.size();
    }
    
    /**
     * Returns the number of times a cached glyph was reused.
     *
     * @return cache hit count
     */
    public int getCacheHits() {
        return cacheHits;
    }
    
    /**
     * Returns the number of times a new glyph was created.
     *
     * @return cache miss count
     */
    public int getCacheMisses() {
        return cacheMisses;
    }
    
    /**
     * Clears the cache and resets statistics (for testing).
     */
    public void clearCache() {
        cache.clear();
        cacheHits = 0;
        cacheMisses = 0;
    }
}
