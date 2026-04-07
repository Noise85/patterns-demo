package com.patterns.bridge.isolation;

/**
 * Implementor interface for rendering operations.
 * Different renderers can implement this interface.
 */
public interface Renderer {
    /**
     * Renders a circle.
     *
     * @param x      Center X coordinate
     * @param y      Center Y coordinate
     * @param radius Circle radius
     */
    void renderCircle(double x, double y, double radius);

    /**
     * Renders a rectangle.
     *
     * @param x      Top-left X coordinate
     * @param y      Top-left Y coordinate
     * @param width  Rectangle width
     * @param height Rectangle height
     */
    void renderRectangle(double x, double y, double width, double height);
}
