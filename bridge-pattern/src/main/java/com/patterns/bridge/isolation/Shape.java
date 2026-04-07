package com.patterns.bridge.isolation;

/**
 * Abstraction for shapes.
 * Delegates rendering to the Renderer implementor.
 */
public abstract class Shape {
    protected Renderer renderer;

    protected Shape(Renderer renderer) {
        this.renderer = renderer;
    }

    /**
     * Draws the shape using the configured renderer.
     */
    public abstract void draw();
}
