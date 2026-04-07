package com.patterns.bridge.isolation;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Exercise 1: Shape Rendering Bridge
 */
class IsolationExerciseTest {

    @Test
    void circle_withVectorRenderer_delegatesCorrectly() {
        ByteArrayOutputStream output = captureSystemOut();
        Renderer renderer = new VectorRenderer();
        Shape circle = new Circle(renderer, 10, 20, 5);

        circle.draw();

        assertThat(output.toString()).contains("Drawing circle with SVG");
        assertThat(output.toString()).contains("10.0, 20.0");
        assertThat(output.toString()).contains("5.0");
    }

    @Test
    void circle_withRasterRenderer_delegatesCorrectly() {
        ByteArrayOutputStream output = captureSystemOut();
        Renderer renderer = new RasterRenderer();
        Shape circle = new Circle(renderer, 15, 25, 10);

        circle.draw();

        assertThat(output.toString()).contains("Rasterizing circle");
        assertThat(output.toString()).contains("15");
        assertThat(output.toString()).contains("25");
        assertThat(output.toString()).contains("10");
    }

    @Test
    void rectangle_withVectorRenderer_delegatesCorrectly() {
        ByteArrayOutputStream output = captureSystemOut();
        Renderer renderer = new VectorRenderer();
        Shape rectangle = new Rectangle(renderer, 0, 0, 100, 50);

        rectangle.draw();

        assertThat(output.toString()).contains("Drawing rectangle with SVG");
        assertThat(output.toString()).contains("0.0, 0.0");
        assertThat(output.toString()).contains("100.0x50.0");
    }

    @Test
    void rectangle_withRasterRenderer_delegatesCorrectly() {
        ByteArrayOutputStream output = captureSystemOut();
        Renderer renderer = new RasterRenderer();
        Shape rectangle = new Rectangle(renderer, 10, 20, 80, 40);

        rectangle.draw();

        assertThat(output.toString()).contains("Rasterizing rectangle");
        assertThat(output.toString()).contains("10");
        assertThat(output.toString()).contains("20");
        assertThat(output.toString()).contains("80");
        assertThat(output.toString()).contains("40");
    }

    @Test
    void shapes_canSwitchRenderers() {
        ByteArrayOutputStream output1 = captureSystemOut();
        Renderer vectorRenderer = new VectorRenderer();
        Shape circle1 = new Circle(vectorRenderer, 5, 5, 3);
        circle1.draw();
        assertThat(output1.toString()).contains("SVG");

        ByteArrayOutputStream output2 = captureSystemOut();
        Renderer rasterRenderer = new RasterRenderer();
        Shape circle2 = new Circle(rasterRenderer, 5, 5, 3);
        circle2.draw();
        assertThat(output2.toString()).contains("Rasterizing");
    }

    @Test
    void differentShapes_sameRenderer_workCorrectly() {
        Renderer renderer = new VectorRenderer();
        
        Circle circle = new Circle(renderer, 10, 10, 5);
        Rectangle rectangle = new Rectangle(renderer, 0, 0, 20, 30);

        ByteArrayOutputStream output = captureSystemOut();
        circle.draw();
        rectangle.draw();

        String result = output.toString();
        assertThat(result).contains("Drawing circle with SVG");
        assertThat(result).contains("Drawing rectangle with SVG");
    }

    @Test
    void circleProperties_areAccessible() {
        Renderer renderer = new VectorRenderer();
        Circle circle = new Circle(renderer, 7.5, 8.5, 12.3);

        assertThat(circle.getX()).isEqualTo(7.5);
        assertThat(circle.getY()).isEqualTo(8.5);
        assertThat(circle.getRadius()).isEqualTo(12.3);
    }

    @Test
    void rectangleProperties_areAccessible() {
        Renderer renderer = new RasterRenderer();
        Rectangle rectangle = new Rectangle(renderer, 1.0, 2.0, 30.0, 40.0);

        assertThat(rectangle.getX()).isEqualTo(1.0);
        assertThat(rectangle.getY()).isEqualTo(2.0);
        assertThat(rectangle.getWidth()).isEqualTo(30.0);
        assertThat(rectangle.getHeight()).isEqualTo(40.0);
    }

    private ByteArrayOutputStream captureSystemOut() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        return outputStream;
    }
}
