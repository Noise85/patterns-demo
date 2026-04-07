# Exercise 1: Pattern in Isolation

## Title
Shape Rendering Bridge

## Learning Objectives

- Understand separation of concerns (abstraction vs implementation)
- Avoid class explosion from multiple inheritance
- Use composition to bridge abstraction and implementation
- Enable independent variation of two hierarchies
- Practice delegation pattern

## Scenario

You're building a graphics library that must support multiple shapes (Circle, Rectangle) rendered on different platforms (Vector graphics, Raster graphics). Creating classes like `VectorCircle`, `RasterCircle`, `VectorRectangle`, `RasterRectangle` leads to combinatorial explosion. Use the Bridge pattern to separate shapes from renderers.

## Functional Requirements

### 1. Implementor Interface (`Renderer`)

```java
void renderCircle(double x, double y, double radius);
void renderRectangle(double x, double y, double width, double height);
```

### 2. Concrete Implementors

- `VectorRenderer` - Outputs SVG-like commands
- `RasterRenderer` - Outputs pixel-based commands

### 3. Abstraction (`Shape`)

Abstract class with:
- `Renderer renderer` field
- Constructor accepting Renderer
- `abstract void draw()` method

### 4. Refined Abstractions

- `Circle` - Stores x, y, radius. Calls `renderer.renderCircle()`
- `Rectangle` - Stores x, y, width, height. Calls `renderer.renderRectangle()`

## Non-Functional Expectations

- Shapes and renderers vary independently
- Adding a new shape doesn't require modifying renderers
- Adding a new renderer doesn't require modifying shapes

## Constraints

- Use composition (Shape has-a Renderer)
- No shape-renderer combined classes

## Starter Code Location

`src/main/java/com/patterns/bridge/isolation/`

## Acceptance Criteria

✅ All tests in `IsolationExerciseTest.java` pass

## Hints

<details>
<summary>Click to reveal hints</summary>

- Shape constructor takes Renderer as parameter
- In Circle.draw(), call `renderer.renderCircle(x, y, radius)`
- In Rectangle.draw(), call `renderer.renderRectangle(x, y, width, height)`
- VectorRenderer outputs "Drawing circle with SVG..."
- RasterRenderer outputs "Rasterizing circle at..."
</details>
