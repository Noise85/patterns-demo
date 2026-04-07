package com.patterns.abstractfactory.simulation.html;

import com.patterns.abstractfactory.simulation.StyleApplier;

import java.util.Map;

/**
 * Concrete product: HTML style applier.
 */
public class HtmlStyleApplier implements StyleApplier {

    @Override
    public String applyStyles(String content, Map<String, String> styles) {
        // TODO: Implement HTML style application
        // Add header/footer if present in styles
        // Example: "<header>" + header + "</header>\n" + content + "\n<footer>" + footer + "</footer>"
        throw new UnsupportedOperationException("TODO: Implement HTML style application");
    }
}
