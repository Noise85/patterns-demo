package com.patterns.abstractfactory.isolation;

/**
 * Concrete product: Light-themed checkbox.
 */
public class LightCheckbox implements Checkbox {
    private final String label;

    public LightCheckbox(String label) {
        this.label = label;
    }

    @Override
    public String render() {
        // TODO: Implement light checkbox rendering
        // Should return: "[Light] Checkbox: <label>"
        throw new UnsupportedOperationException("TODO: Implement light checkbox rendering");
    }
}
