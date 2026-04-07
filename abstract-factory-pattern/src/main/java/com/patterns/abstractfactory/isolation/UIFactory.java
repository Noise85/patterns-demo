package com.patterns.abstractfactory.isolation;

/**
 * Abstract Factory interface for creating UI component families.
 * Declares creation methods for each product type in the family.
 */
public interface UIFactory {
    /**
     * Creates a button with the specified label.
     *
     * @param label The text to display on the button
     * @return A button styled according to this factory's theme
     */
    Button createButton(String label);

    /**
     * Creates a checkbox with the specified label.
     *
     * @param label The text to display next to the checkbox
     * @return A checkbox styled according to this factory's theme
     */
    Checkbox createCheckbox(String label);
}
