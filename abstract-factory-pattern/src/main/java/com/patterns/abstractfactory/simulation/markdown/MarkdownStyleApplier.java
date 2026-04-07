package com.patterns.abstractfactory.simulation.markdown;

import com.patterns.abstractfactory.simulation.StyleApplier;

import java.util.Map;

/**
 * Concrete product: Markdown style applier.
 */
public class MarkdownStyleApplier implements StyleApplier {

    @Override
    public String applyStyles(String content, Map<String, String> styles) {
        // TODO: Implement Markdown style application
        // Add header/footer if present in styles
        // Example: "# " + header + "\n\n" + content + "\n\n---\n" + footer
        throw new UnsupportedOperationException("TODO: Implement Markdown style application");
    }
}
