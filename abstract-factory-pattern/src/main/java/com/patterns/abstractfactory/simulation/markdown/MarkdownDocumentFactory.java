package com.patterns.abstractfactory.simulation.markdown;

import com.patterns.abstractfactory.simulation.DocumentFactory;
import com.patterns.abstractfactory.simulation.DocumentRenderer;
import com.patterns.abstractfactory.simulation.TextFormatter;
import com.patterns.abstractfactory.simulation.StyleApplier;

/**
 * Concrete factory: Creates Markdown document components.
 * All products belong to the Markdown export family.
 */
public class MarkdownDocumentFactory implements DocumentFactory {

    @Override
    public DocumentRenderer createRenderer() {
        // TODO: Create and return Markdown renderer
        throw new UnsupportedOperationException("TODO: Create MarkdownRenderer instance");
    }

    @Override
    public TextFormatter createFormatter() {
        // TODO: Create and return Markdown formatter
        throw new UnsupportedOperationException("TODO: Create MarkdownFormatter instance");
    }

    @Override
    public StyleApplier createStyleApplier() {
        // TODO: Create and return Markdown style applier
        throw new UnsupportedOperationException("TODO: Create MarkdownStyleApplier instance");
    }
}
