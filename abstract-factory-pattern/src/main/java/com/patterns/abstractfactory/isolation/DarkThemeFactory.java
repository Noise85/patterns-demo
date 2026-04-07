package com.patterns.abstractfactory.isolation;

/**
 * Concrete factory: Creates dark-themed UI components.
 * All products created by this factory belong to the Dark theme family.
 */
public class DarkThemeFactory implements UIFactory {

    @Override
    public Button createButton(String label) {
        // TODO: Implement factory method to create dark button
        throw new UnsupportedOperationException("TODO: Create and return DarkButton instance");
    }

    @Override
    public Checkbox createCheckbox(String label) {
        // TODO: Implement factory method to create dark checkbox
        throw new UnsupportedOperationException("TODO: Create and return DarkCheckbox instance");
    }
}
