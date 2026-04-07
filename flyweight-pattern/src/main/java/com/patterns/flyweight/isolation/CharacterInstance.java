package com.patterns.flyweight.isolation;

/**
 * Represents a character instance in a document.
 * Holds reference to shared CharacterGlyph (intrinsic state)
 * and stores extrinsic state (position, color).
 */
public class CharacterInstance {
    
    private final CharacterGlyph glyph;
    private final int x;
    private final int y;
    private final String color;
    
    /**
     * Creates a character instance.
     *
     * @param glyph the shared glyph
     * @param x the x coordinate
     * @param y the y coordinate
     * @param color the foreground color
     */
    public CharacterInstance(CharacterGlyph glyph, int x, int y, String color) {
        this.glyph = glyph;
        this.x = x;
        this.y = y;
        this.color = color;
    }
    
    /**
     * Renders this character instance.
     * Delegates to glyph with extrinsic state.
     *
     * @return rendering description
     */
    public String render() {
        // TODO: Implement rendering delegation
        // Delegate to glyph.render() passing extrinsic state (x, y, color)
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * Gets the shared glyph (for testing identity).
     *
     * @return the character glyph
     */
    public CharacterGlyph getGlyph() {
        return glyph;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public String getColor() {
        return color;
    }
}
