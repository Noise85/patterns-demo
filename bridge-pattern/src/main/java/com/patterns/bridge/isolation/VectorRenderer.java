package com.patterns.bridge.isolation;

/**
 * Vector graphics renderer (SVG-like output).
 */
public class VectorRenderer implements Renderer {
    @Override
    public void renderCircle(double x, double y, double radius) {
        System.out.println(String.format(
            "Drawing circle with SVG at (%.1f, %.1f) with radius %.1f",
            x, y, radius
        ));
    }

    @Override
    public void renderRectangle(double x, double y, double width, double height) {
        System.out.println(String.format(
            "Drawing rectangle with SVG at (%.1f, %.1f) with dimensions %.1fx%.1f",
            x, y, width, height
        ));
    }
}
