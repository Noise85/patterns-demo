package com.patterns.abstractfactory.isolation;

/**
 * Concrete product: Dark-themed button.
 */
public class DarkButton implements Button {
    private final String label;

    public DarkButton(String label) {
        this.label = label;
    }

    @Override
    public String render() {
        // TODO: Implement dark button rendering
        // Should return: "[Dark] Button: <label>"
        throw new UnsupportedOperationException("TODO: Implement dark button rendering");
    }
}
