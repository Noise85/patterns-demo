package com.patterns.abstractfactory.simulation.pdf;

import com.patterns.abstractfactory.simulation.DocumentFactory;
import com.patterns.abstractfactory.simulation.DocumentRenderer;
import com.patterns.abstractfactory.simulation.TextFormatter;
import com.patterns.abstractfactory.simulation.StyleApplier;

/**
 * Concrete factory: Creates PDF document components.
 * All products belong to the PDF export family.
 */
public class PdfDocumentFactory implements DocumentFactory {

    @Override
    public DocumentRenderer createRenderer() {
        // TODO: Create and return PDF renderer
        throw new UnsupportedOperationException("TODO: Create PdfRenderer instance");
    }

    @Override
    public TextFormatter createFormatter() {
        // TODO: Create and return PDF formatter
        throw new UnsupportedOperationException("TODO: Create PdfFormatter instance");
    }

    @Override
    public StyleApplier createStyleApplier() {
        // TODO: Create and return PDF style applier
        throw new UnsupportedOperationException("TODO: Create PdfStyleApplier instance");
    }
}
