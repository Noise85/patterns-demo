package com.patterns.bridge.isolation;

/**
 * Raster graphics renderer (pixel-based output).
 */
public class RasterRenderer implements Renderer {
    @Override
    public void renderCircle(double x, double y, double radius) {
        System.out.println(String.format(
            "Rasterizing circle at pixel (%.0f, %.0f) with radius %.0f pixels",
            x, y, radius
        ));
    }

    @Override
    public void renderRectangle(double x, double y, double width, double height) {
        System.out.println(String.format(
            "Rasterizing rectangle at pixel (%.0f, %.0f) with dimensions %.0fx%.0f pixels",
            x, y, width, height
        ));
    }
}
