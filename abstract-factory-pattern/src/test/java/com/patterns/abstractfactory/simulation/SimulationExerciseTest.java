package com.patterns.abstractfactory.simulation;

import com.patterns.abstractfactory.simulation.html.HtmlDocumentFactory;
import com.patterns.abstractfactory.simulation.markdown.MarkdownDocumentFactory;
import com.patterns.abstractfactory.simulation.pdf.PdfDocumentFactory;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Exercise 2: Document Export System
 * <p>
 * These tests validate production-ready document export with
 * coordinated component families.
 */
class SimulationExerciseTest {

    @Test
    void pdfFactory_createsRenderer() {
        DocumentFactory factory = new PdfDocumentFactory();
        DocumentRenderer renderer = factory.createRenderer();

        String result = renderer.renderContent("Sample content");

        assertThat(result)
                .contains("%%PDF")
                .contains("Sample content")
                .contains("%%EOF");
    }

    @Test
    void pdfFactory_createsFormatter() {
        DocumentFactory factory = new PdfDocumentFactory();
        TextFormatter formatter = factory.createFormatter();

        String boldText = formatter.formatText("Important", "bold");
        String italicText = formatter.formatText("Note", "italic");

        assertThat(boldText).contains("pdf").containsIgnoringCase("Important");
        assertThat(italicText).contains("pdf").containsIgnoringCase("Note");
    }

    @Test
    void pdfFactory_createsStyleApplier() {
        DocumentFactory factory = new PdfDocumentFactory();
        StyleApplier styleApplier = factory.createStyleApplier();

        Map<String, String> styles = Map.of("header", "Report", "footer", "Page 1");
        String result = styleApplier.applyStyles("Content", styles);

        assertThat(result)
                .containsIgnoringCase("pdf")
                .containsIgnoringCase("Report")
                .containsIgnoringCase("Page 1")
                .contains("Content");
    }

    @Test
    void htmlFactory_createsRenderer() {
        DocumentFactory factory = new HtmlDocumentFactory();
        DocumentRenderer renderer = factory.createRenderer();

        String result = renderer.renderContent("Web content");

        assertThat(result)
                .contains("<!DOCTYPE html>")
                .contains("<html>")
                .contains("Web content")
                .contains("</html>");
    }

    @Test
    void htmlFactory_createsFormatter() {
        DocumentFactory factory = new HtmlDocumentFactory();
        TextFormatter formatter = factory.createFormatter();

        String boldText = formatter.formatText("Headline", "bold");
        String italicText = formatter.formatText("Emphasis", "italic");

        assertThat(boldText).matches(".*<(strong|b)>.*Headline.*</(strong|b)>.*");
        assertThat(italicText).matches(".*<(em|i)>.*Emphasis.*</(em|i)>.*");
    }

    @Test
    void htmlFactory_createsStyleApplier() {
        DocumentFactory factory = new HtmlDocumentFactory();
        StyleApplier styleApplier = factory.createStyleApplier();

        Map<String, String> styles = Map.of("header", "Welcome", "footer", "Copyright");
        String result = styleApplier.applyStyles("Main content", styles);

        assertThat(result)
                .contains("header")
                .contains("Welcome")
                .contains("Main content")
                .contains("footer")
                .contains("Copyright");
    }

    @Test
    void markdownFactory_createsRenderer() {
        DocumentFactory factory = new MarkdownDocumentFactory();
        DocumentRenderer renderer = factory.createRenderer();

        String result = renderer.renderContent("Markdown text");

        assertThat(result)
                .contains("---")
                .contains("Markdown text");
    }

    @Test
    void markdownFactory_createsFormatter() {
        DocumentFactory factory = new MarkdownDocumentFactory();
        TextFormatter formatter = factory.createFormatter();

        String boldText = formatter.formatText("Strong", "bold");
        String italicText = formatter.formatText("Emphasis", "italic");

        assertThat(boldText).contains("**Strong**");
        assertThat(italicText).matches(".*\\*Emphasis\\*.*");
    }

    @Test
    void markdownFactory_createsStyleApplier() {
        DocumentFactory factory = new MarkdownDocumentFactory();
        StyleApplier styleApplier = factory.createStyleApplier();

        Map<String, String> styles = Map.of("header", "Title", "footer", "End");
        String result = styleApplier.applyStyles("Body", styles);

        assertThat(result)
                .contains("Title")
                .contains("Body")
                .contains("End");
    }

    @Test
    void documentExporter_exportsPdfDocument() {
        DocumentFactory factory = new PdfDocumentFactory();
        DocumentExporter exporter = new DocumentExporter(factory);

        Map<String, String> styles = Map.of("header", "Annual Report", "footer", "2026");
        String result = exporter.export("Financial data...", "Q1 Results", styles);

        assertThat(result)
                .containsIgnoringCase("pdf")
                .contains("Q1 Results")
                .contains("Financial data")
                .contains("Annual Report")
                .contains("2026");
    }

    @Test
    void documentExporter_exportsHtmlDocument() {
        DocumentFactory factory = new HtmlDocumentFactory();
        DocumentExporter exporter = new DocumentExporter(factory);

        Map<String, String> styles = Map.of("header", "Blog Post", "footer", "Comments");
        String result = exporter.export("Article content...", "Breaking News", styles);

        assertThat(result)
                .contains("html")
                .contains("Breaking News")
                .contains("Article content")
                .contains("Blog Post")
                .contains("Comments");
    }

    @Test
    void documentExporter_exportsMarkdownDocument() {
        DocumentFactory factory = new MarkdownDocumentFactory();
        DocumentExporter exporter = new DocumentExporter(factory);

        Map<String, String> styles = Map.of("header", "README", "footer", "License: MIT");
        String result = exporter.export("Installation instructions...", "Getting Started", styles);

        assertThat(result)
                .contains("Getting Started")
                .contains("Installation instructions")
                .contains("README")
                .contains("MIT");
    }

    @Test
    void documentExporter_canSwitchFactories() {
        Map<String, String> styles = Map.of("header", "Doc", "footer", "End");

        DocumentExporter pdfExporter = new DocumentExporter(new PdfDocumentFactory());
        DocumentExporter htmlExporter = new DocumentExporter(new HtmlDocumentFactory());
        DocumentExporter mdExporter = new DocumentExporter(new MarkdownDocumentFactory());

        String pdfResult = pdfExporter.export("Content", "Title", styles);
        String htmlResult = htmlExporter.export("Content", "Title", styles);
        String mdResult = mdExporter.export("Content", "Title", styles);

        // Each format produces different output structure
        assertThat(pdfResult).containsIgnoringCase("pdf");
        assertThat(htmlResult).contains("html");
        assertThat(mdResult).contains("---");
    }

    @Test
    void allFactories_produceCompleteComponentFamilies() {
        DocumentFactory[] factories = {
                new PdfDocumentFactory(),
                new HtmlDocumentFactory(),
                new MarkdownDocumentFactory()
        };

        for (DocumentFactory factory : factories) {
            // Each factory must provide all three component types
            DocumentRenderer renderer = factory.createRenderer();
            TextFormatter formatter = factory.createFormatter();
            StyleApplier styleApplier = factory.createStyleApplier();

            assertThat(renderer).isNotNull();
            assertThat(formatter).isNotNull();
            assertThat(styleApplier).isNotNull();

            // Components must be functional
            assertThat(renderer.renderContent("test")).isNotBlank();
            assertThat(formatter.formatText("text", "bold")).isNotBlank();
            assertThat(styleApplier.applyStyles("content", Map.of())).isNotBlank();
        }
    }
}
