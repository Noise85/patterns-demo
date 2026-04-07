package com.patterns.bridge.isolation;

/**
 * Refined abstraction for Rectangle shape.
 */
public class Rectangle extends Shape {
    private final double x;
    private final double y;
    private final double width;
    private final double height;

    public Rectangle(Renderer renderer, double x, double y, double width, double height) {
        super(renderer);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw() {
        // TODO: Delegate to renderer.renderRectangle()
        throw new UnsupportedOperationException("TODO: Implement Rectangle.draw()");
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
