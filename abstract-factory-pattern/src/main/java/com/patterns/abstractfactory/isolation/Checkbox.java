package com.patterns.abstractfactory.isolation;

/**
 * Product interface for checkboxes.
 * Part of the UI component family.
 */
public interface Checkbox {
    /**
     * Renders the checkbox with theme-specific styling.
     *
     * @return Visual representation of the checkbox
     */
    String render();
}
