package com.patterns.abstractfactory.simulation.pdf;

import com.patterns.abstractfactory.simulation.TextFormatter;

/**
 * Concrete product: PDF text formatter.
 */
public class PdfFormatter implements TextFormatter {

    @Override
    public String formatText(String text, String style) {
        // TODO: Implement PDF text formatting
        // Apply PDF-specific formatting based on style
        // Example for "bold": "<b-pdf>" + text + "</b-pdf>"
        // Example for "italic": "<i-pdf>" + text + "</i-pdf>"
        throw new UnsupportedOperationException("TODO: Implement PDF text formatting");
    }
}
