package com.patterns.abstractfactory.isolation;

/**
 * Product interface for buttons.
 * Part of the UI component family.
 */
public interface Button {
    /**
     * Renders the button with theme-specific styling.
     *
     * @return Visual representation of the button
     */
    String render();
}
