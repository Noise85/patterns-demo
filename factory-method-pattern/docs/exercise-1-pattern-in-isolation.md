# Exercise 1: Pattern in Isolation

## Title
Document Export System

## Learning Objectives

By completing this exercise, you will:

- Understand the Factory Method pattern structure
- Define a product interface for related objects
- Implement concrete products with different behaviors
- Create abstract creator with factory method
- Implement concrete creators that override the factory method
- Use factory methods in business operations

## Scenario

You're building a simple document export system. The application can export documents in different formats: PDF, HTML, and Markdown. Each format has its own exporter that knows how to create and configure the appropriate document type.

## Functional Requirements

Implement the following:

1. **Document Interface** (`Document`):
   - Method: `String getContent()` - returns the formatted content
   - Method: `String getFormat()` - returns the format type

2. **Concrete Documents**:
   - `PdfDocument` - returns content with "PDF: " prefix, format "application/pdf"
   - `HtmlDocument` - returns content wrapped in HTML tags, format "text/html"
   - `MarkdownDocument` - returns content as-is, format "text/markdown"

3. **DocumentExporter (Abstract Creator)**:
   - Abstract method: `Document createDocument(String content)` - factory method
   - Concrete method: `String export(String content)` - uses factory method

4. **Concrete Exporters**:
   - `PdfExporter` - creates PdfDocument instances
   - `HtmlExporter` - creates HtmlDocument instances
   - `MarkdownExporter` - creates MarkdownDocument instances

## Non-Functional Expectations

- Keep it simple - focus on the pattern mechanics
- Factory method should be abstract in the base creator
- The `export()` method should use the factory method internally
- Products should be simple data holders

## Constraints

- The creator's factory method must be abstract
- Concrete creators only override the factory method
- Do NOT put export logic in the constructor
- Each document type must implement the Document interface

## Starter Code Location

- Product: `src/main/java/com/patterns/factorymethod/isolation/Document.java`
- Concrete Products: `src/main/java/com/patterns/factorymethod/isolation/`
- Creator: `src/main/java/com/patterns/factorymethod/isolation/DocumentExporter.java`
- Concrete Creators: `src/main/java/com/patterns/factorymethod/isolation/`

Look for `TODO` comments in the code.

## Acceptance Criteria

✅ All tests in `IsolationExerciseTest.java` pass

The tests verify:
- Each document type formats content correctly
- Each exporter creates the correct document type
- The export method delegates to the factory method
- Different exporters produce different output

## Stretch Goals (Optional)

1. Add a fourth document type: `JsonDocument`
2. Add metadata to documents (author, creation date)
3. Implement a document validator that checks format-specific rules

## Hints

<details>
<summary>Click to reveal hints</summary>

**Product Interface**:
- Should define common operations all documents support
- Keep it focused on document representation, not creation

**Concrete Products**:
- Implement formatting logic specific to each type
- For HTML: wrap content in `<html><body>...</body></html>`
- For PDF: add a "PDF: " prefix to simulate PDF format
- For Markdown: return content as-is

**Abstract Creator**:
- Declare factory method as `abstract`
- Implement `export()` to call factory method and use the result
- Example: `return createDocument(content).getContent();`

**Concrete Creators**:
- Only override the factory method
- Return the appropriate concrete product
- No other logic needed

</details>

## What's Next?

Once you complete this exercise and all tests pass, move on to **Exercise 2: Real-World Simulation** where you'll build a production-ready multi-channel notification system.
