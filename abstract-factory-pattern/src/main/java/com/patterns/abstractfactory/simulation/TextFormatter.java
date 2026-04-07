package com.patterns.abstractfactory.simulation;

/**
 * Product interface for text formatting.
 * Applies format-specific text styling.
 */
public interface TextFormatter {
    /**
     * Formats text with the specified style.
     *
     * @param text  The text to format
     * @param style The style to apply (e.g., "bold", "italic", "heading")
     * @return Formatted text in the specific format
     */
    String formatText(String text, String style);
}
