package com.patterns.bridge.isolation;

/**
 * Refined abstraction for Circle shape.
 */
public class Circle extends Shape {
    private final double x;
    private final double y;
    private final double radius;

    public Circle(Renderer renderer, double x, double y, double radius) {
        super(renderer);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void draw() {
        // TODO: Delegate to renderer.renderCircle()
        throw new UnsupportedOperationException("TODO: Implement Circle.draw()");
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }
}
