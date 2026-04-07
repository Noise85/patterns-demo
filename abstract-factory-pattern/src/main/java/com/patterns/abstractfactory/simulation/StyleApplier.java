package com.patterns.abstractfactory.simulation;

import java.util.Map;

/**
 * Product interface for applying document-wide styles.
 * Adds headers, footers, and visual elements.
 */
public interface StyleApplier {
    /**
     * Applies document-wide styles to content.
     *
     * @param content The content to style
     * @param styles  Style properties (e.g., "header", "footer", "margin")
     * @return Content with applied styles
     */
    String applyStyles(String content, Map<String, String> styles);
}
