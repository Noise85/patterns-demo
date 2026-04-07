# Exercise 1: Pattern in Isolation

## Title
Document Cloning with Deep Copy

## Learning Objectives

- Implement object cloning with proper deep copy
- Handle nested object cloning
- Understand shallow vs deep copy
- Ensure clone independence
- Work with mutable nested structures

## Scenario

You're building a document editor. Users need to duplicate documents with all their content (paragraphs, images, formatting). Simple assignment creates shared references, causing unwanted coupling. You need proper cloning with deep copy semantics.

## Functional Requirements

### 1. Document Class

- Fields: `title` (String), `sections` (List<Section>)
- Method: `clone()` returns independent copy
- Cloned documents must be independent (modifying one doesn't affect the other)

### 2. Section Class

- Fields: `heading` (String), `content` (String), `metadata` (Map<String, String>)
- Method: `clone()` returns independent copy
- Handle deep copy of metadata map

### 3. Cloning Behavior

- String fields: safe (immutable)
- Collections: must be deep copied
- Nested objects: must be cloned recursively

## Non-Functional Expectations

- Clones are independent (no shared mutable state)
- Defensive copying for all mutable fields
- Clear clone() method implementation

## Constraints

- Implement custom clone() method (return type is the class itself)
- Don't use Object.clone() directly (use copy constructor pattern)
- Ensure all mutable fields are deep copied

## Starter Code Location

`src/main/java/com/patterns/prototype/isolation/`

## Acceptance Criteria

✅ All tests in `IsolationExerciseTest.java` pass

## Hints

<details>
<summary>Click to reveal hints</summary>

- Create new ArrayList/HashMap for collections (defensive copy)
- For nested objects, call their clone() method
- Use copy constructor pattern: `new Section(section.heading, section.content, ...)`
- Map deep copy: `new HashMap<>(original)`
- List deep copy with element cloning: `original.stream().map(Section::clone).toList()`
</details>
