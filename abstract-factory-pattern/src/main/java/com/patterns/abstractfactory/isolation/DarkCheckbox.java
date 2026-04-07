package com.patterns.abstractfactory.isolation;

/**
 * Concrete product: Dark-themed checkbox.
 */
public class DarkCheckbox implements Checkbox {
    private final String label;

    public DarkCheckbox(String label) {
        this.label = label;
    }

    @Override
    public String render() {
        // TODO: Implement dark checkbox rendering
        // Should return: "[Dark] Checkbox: <label>"
        throw new UnsupportedOperationException("TODO: Implement dark checkbox rendering");
    }
}
