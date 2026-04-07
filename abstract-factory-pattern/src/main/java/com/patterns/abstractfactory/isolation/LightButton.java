package com.patterns.abstractfactory.isolation;

/**
 * Concrete product: Light-themed button.
 */
public class LightButton implements Button {
    private final String label;

    public LightButton(String label) {
        this.label = label;
    }

    @Override
    public String render() {
        // TODO: Implement light button rendering
        // Should return: "[Light] Button: <label>"
        throw new UnsupportedOperationException("TODO: Implement light button rendering");
    }
}
