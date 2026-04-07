package com.patterns.abstractfactory.simulation;

/**
 * Abstract Factory interface for creating document export components.
 * Each factory produces a complete family of components for one export format.
 */
public interface DocumentFactory {
    /**
     * Creates a renderer for this document format.
     *
     * @return Format-specific renderer
     */
    DocumentRenderer createRenderer();

    /**
     * Creates a text formatter for this document format.
     *
     * @return Format-specific formatter
     */
    TextFormatter createFormatter();

    /**
     * Creates a style applier for this document format.
     *
     * @return Format-specific style applier
     */
    StyleApplier createStyleApplier();
}
