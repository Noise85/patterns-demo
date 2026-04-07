package com.patterns.abstractfactory.simulation;

/**
 * Product interface for document rendering.
 * Converts content to format-specific structure.
 */
public interface DocumentRenderer {
    /**
     * Renders content in the specific document format.
     *
     * @param content The raw content to render
     * @return Format-specific rendered content
     */
    String renderContent(String content);
}
