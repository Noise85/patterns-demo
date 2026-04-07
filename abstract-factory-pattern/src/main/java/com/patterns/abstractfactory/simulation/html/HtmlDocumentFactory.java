package com.patterns.abstractfactory.simulation.html;

import com.patterns.abstractfactory.simulation.DocumentFactory;
import com.patterns.abstractfactory.simulation.DocumentRenderer;
import com.patterns.abstractfactory.simulation.TextFormatter;
import com.patterns.abstractfactory.simulation.StyleApplier;

/**
 * Concrete factory: Creates HTML document components.
 * All products belong to the HTML export family.
 */
public class HtmlDocumentFactory implements DocumentFactory {

    @Override
    public DocumentRenderer createRenderer() {
        // TODO: Create and return HTML renderer
        throw new UnsupportedOperationException("TODO: Create HtmlRenderer instance");
    }

    @Override
    public TextFormatter createFormatter() {
        // TODO: Create and return HTML formatter
        throw new UnsupportedOperationException("TODO: Create HtmlFormatter instance");
    }

    @Override
    public StyleApplier createStyleApplier() {
        // TODO: Create and return HTML style applier
        throw new UnsupportedOperationException("TODO: Create HtmlStyleApplier instance");
    }
}
