# Exercise 2: Real-World Simulation

## Title
Cross-Platform Document Export System

## Learning Objectives

- Apply Abstract Factory to complex product families
- Coordinate multiple related components
- Handle format-specific rendering pipelines
- Design extensible export architecture
- Ensure components from same format work together

## Scenario

You're building a document export service that generates documents in multiple formats: PDF, HTML, and Markdown. Each format requires coordinated components working together:
- **Renderer**: Converts content to format-specific structure
- **Formatter**: Applies format-specific text styling
- **StyleApplier**: Adds headers, footers, and visual elements

Components from different formats are incompatible and must not be mixed.

## Functional Requirements

### 1. Product Interfaces

- `DocumentRenderer` - `String renderContent(String content)`
- `TextFormatter` - `String formatText(String text, String style)`
- `StyleApplier` - `String applyStyles(String content, Map<String, String> styles)`

### 2. Concrete Products

For each format (PDF, HTML, Markdown), implement all three product types.

### 3. Abstract Factory

`DocumentFactory` interface with methods to create each component type.

### 4. Concrete Factories

- `PdfDocumentFactory`
- `HtmlDocumentFactory`
- `MarkdownDocumentFactory`

### 5. Document Exporter

Coordinator that uses factory to create all components and exports complete documents.

## Non-Functional Expectations

- Thread-safe factories
- Components validate they receive compatible inputs
- Format-specific rendering logic
- Clean separation of concerns

## Constraints

- No mixing components from different formats
- All factories must implement DocumentFactory
- Exporter uses factory interface only

## Starter Code Location

`src/main/java/com/patterns/abstractfactory/simulation/`

## Acceptance Criteria

✅ All tests in `SimulationExerciseTest.java` pass

## Stretch Goals

1. Add XML export format
2. Implement factory registration system
3. Add validation for component compatibility
4. Support custom style themes per format

## Hints

<details>
<summary>Click to reveal hints</summary>

- Each format's components should produce output in that format
- Factory creates all components needed for one export format
- Exporter coordinates components to produce final output
- Keep format-specific logic in the components
</details>
