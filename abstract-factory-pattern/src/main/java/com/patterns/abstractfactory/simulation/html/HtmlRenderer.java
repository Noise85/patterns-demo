package com.patterns.abstractfactory.simulation.html;

import com.patterns.abstractfactory.simulation.DocumentRenderer;

/**
 * Concrete product: HTML document renderer.
 */
public class HtmlRenderer implements DocumentRenderer {

    @Override
    public String renderContent(String content) {
        // TODO: Implement HTML rendering
        // Wrap content in HTML document structure
        // Example: "<!DOCTYPE html>\n<html><body>\n<content>\n</body></html>"
        throw new UnsupportedOperationException("TODO: Implement HTML content rendering");
    }
}
