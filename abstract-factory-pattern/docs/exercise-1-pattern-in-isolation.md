# Exercise 1: Pattern in Isolation

## Title
UI Theme System with Light and Dark Modes

## Learning Objectives

- Understand product families concept
- Define abstract factory interface for creating product families
- Implement concrete factories for each family variant
- Create families of related products that work together
- Ensure client code uses only interfaces

## Scenario

You're building a themeable UI component library. The application supports two themes: Light and Dark. Each theme provides a cohesive set of UI components (Button and Checkbox) that share visual styling. Components from the same theme family must be used together to maintain visual consistency.

## Functional Requirements

1. **Product Interfaces**:
   - `Button` - Methods: `render()` returns visual representation
   - `Checkbox` - Methods: `render()` returns visual representation

2. **Concrete Products**:
   - `LightButton`, `DarkButton`
   - `LightCheckbox`, `DarkCheckbox`

3. **Abstract Factory Interface** (`UIFactory`):
   - `Button createButton(String label)`
   - `Checkbox createCheckbox(String label)`

4. **Concrete Factories**:
   - `LightThemeFactory` - creates light components
   - `DarkThemeFactory` - creates dark components

## Non-Functional Expectations

- Products from same family share styling attributes
- Client code uses factory interface, not concrete factories
- Light theme: components return text with "[Light]" prefix
- Dark theme: components return text with "[Dark]" prefix

## Constraints

- All factories must implement UIFactory interface
- All products must implement their respective interfaces
- No conditional logic based on theme type in client code

## Starter Code Location

`src/main/java/com/patterns/abstractfactory/isolation/`

## Acceptance Criteria

✅ All tests in `IsolationExerciseTest.java` pass

## Hints

<details>
<summary>Click to reveal hints</summary>

- Abstract Factory has multiple `createXXX()` methods, one per product type
- Each concrete factory returns products from the same family
- Products should be simple, focus on the factory pattern
</details>
