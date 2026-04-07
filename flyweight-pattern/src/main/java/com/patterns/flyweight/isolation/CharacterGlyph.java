package com.patterns.flyweight.isolation;

/**
 * Flyweight object representing a character glyph.
 * Stores intrinsic state (shared): character value, font family, size, style.
 * Immutable and thread-safe.
 */
public record CharacterGlyph(
    char value,
    String fontFamily,
    int fontSize,
    FontStyle style
) {
    
    /**
     * Renders this glyph at the specified position with the given color.
     * Extrinsic state (position, color) is passed as parameters.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param color the foreground color
     * @return rendering description for testing
     */
    public String render(int x, int y, String color) {
        // TODO: Implement rendering simulation
        // Return a string describing the render operation:
        // "Rendering '{value}' at ({x},{y}) in {color} with {fontFamily} {fontSize}pt {style}"
        // Example: "Rendering 'A' at (10,20) in black with Arial 12pt REGULAR"
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
