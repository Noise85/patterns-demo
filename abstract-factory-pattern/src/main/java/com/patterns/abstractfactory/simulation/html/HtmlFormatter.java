package com.patterns.abstractfactory.simulation.html;

import com.patterns.abstractfactory.simulation.TextFormatter;

/**
 * Concrete product: HTML text formatter.
 */
public class HtmlFormatter implements TextFormatter {

    @Override
    public String formatText(String text, String style) {
        // TODO: Implement HTML text formatting
        // Apply HTML tags based on style
        // Example for "bold": "<strong>" + text + "</strong>"
        // Example for "italic": "<em>" + text + "</em>"
        throw new UnsupportedOperationException("TODO: Implement HTML text formatting");
    }
}
