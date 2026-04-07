package com.patterns.abstractfactory.simulation;

import java.util.Map;

/**
 * Client that uses the Abstract Factory to export documents.
 * Works with factory and product interfaces only, not concrete implementations.
 */
public class DocumentExporter {
    private final DocumentFactory factory;

    public DocumentExporter(DocumentFactory factory) {
        this.factory = factory;
    }

    /**
     * Exports a document using the configured factory.
     *
     * @param content Raw content to export
     * @param title   Document title for styling
     * @param styles  Additional style properties
     * @return Complete formatted document
     */
    public String export(String content, String title, Map<String, String> styles) {
        // TODO: Implement document export workflow
        // 1. Get formatter from factory and format the title as "heading"
        // 2. Get renderer from factory and render the content
        // 3. Combine formatted title and rendered content
        // 4. Get style applier from factory and apply styles to the combined result
        // 5. Return the final styled document
        throw new UnsupportedOperationException("TODO: Implement document export using factory");
    }
}
