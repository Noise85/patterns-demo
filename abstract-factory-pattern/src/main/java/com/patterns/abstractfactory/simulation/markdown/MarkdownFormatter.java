package com.patterns.abstractfactory.simulation.markdown;

import com.patterns.abstractfactory.simulation.TextFormatter;

/**
 * Concrete product: Markdown text formatter.
 */
public class MarkdownFormatter implements TextFormatter {

    @Override
    public String formatText(String text, String style) {
        // TODO: Implement Markdown text formatting
        // Apply Markdown syntax based on style
        // Example for "bold": "**" + text + "**"
        // Example for "italic": "*" + text + "*"
        throw new UnsupportedOperationException("TODO: Implement Markdown text formatting");
    }
}
