# Solution Notes

## Exercise 1: UI Theme System

### Architecture

```
UIFactory (interface)
  + createButton(label): Button
  + createCheckbox(label): Checkbox
        ↑
        │
  ┌─────┴─────┐
  │           │
LightThemeFactory  DarkThemeFactory
  creates:           creates:
  - LightButton      - DarkButton
  - LightCheckbox    - DarkCheckbox
```

### Key Points

- Factory interface declares one method per product type
- Each concrete factory creates products from ONE family
- Products from same family share consistent styling
- Client receives factory, uses it to create all UI components

### Implementation Tips

**Products**: Simple render() methods returning styled text
**Factories**: Implement interface, instantiate concrete products
**Client**: Works only with interfaces, never concrete classes

## Exercise 2: Document Export System

### Architecture

```
DocumentFactory
  + createRenderer(): DocumentRenderer
  + createFormatter(): TextFormatter
  + createStyleApplier(): StyleApplier
        ↑
        │
  ┌─────┼─────┐
  │     │     │
PdfFactory  HtmlFactory  MarkdownFactory
```

### Key Points

- Each factory creates complete toolchain for one format
- Components from same factory work together seamlessly
- Exporter uses factory to get all components
- Adding new format = one new factory + three new products

### Common Pitfalls

❌ Mixing components from different factories
❌ Factory methods requiring format parameter
❌ Client code checking factory type
✅ One factory per product family
✅ Factory creates all products for that family
✅ Client uses factory interface only
