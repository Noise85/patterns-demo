package com.patterns.flyweight.isolation;

/**
 * Cache key for glyph lookup.
 * Composite key using all intrinsic state fields.
 * Record provides automatic equals/hashCode implementation.
 */
public record GlyphKey(
    char value,
    String fontFamily,
    int fontSize,
    FontStyle style
) {
    // Record automatically provides:
    // - equals() comparing all fields
    // - hashCode() using all fields
    // - Immutability
}
