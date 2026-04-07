package com.patterns.abstractfactory.isolation;

/**
 * Concrete factory: Creates light-themed UI components.
 * All products created by this factory belong to the Light theme family.
 */
public class LightThemeFactory implements UIFactory {

    @Override
    public Button createButton(String label) {
        // TODO: Implement factory method to create light button
        throw new UnsupportedOperationException("TODO: Create and return LightButton instance");
    }

    @Override
    public Checkbox createCheckbox(String label) {
        // TODO: Implement factory method to create light checkbox
        throw new UnsupportedOperationException("TODO: Create and return LightCheckbox instance");
    }
}
