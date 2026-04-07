package com.patterns.factorymethod.isolation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Exercise 1: Document Export Pattern in Isolation
 */
@DisplayName("Exercise 1: Factory Method - Document Export")
class IsolationExerciseTest {
    
    @Test
    @DisplayName("PdfDocument should format content with PDF prefix")
    void testPdfDocument() {
        Document doc = new PdfDocument("Hello World");
        
        assertThat(doc.getContent()).isEqualTo("PDF: Hello World");
        assertThat(doc.getFormat()).isEqualTo("application/pdf");
    }
    
    @Test
    @DisplayName("HtmlDocument should wrap content in HTML tags")
    void testHtmlDocument() {
        Document doc = new HtmlDocument("Hello World");
        
        assertThat(doc.getContent()).isEqualTo("<html><body>Hello World</body></html>");
        assertThat(doc.getFormat()).isEqualTo("text/html");
    }
    
    @Test
    @DisplayName("MarkdownDocument should return content as-is")
    void testMarkdownDocument() {
        Document doc = new MarkdownDocument("Hello World");
        
        assertThat(doc.getContent()).isEqualTo("Hello World");
        assertThat(doc.getFormat()).isEqualTo("text/markdown");
    }
    
    @Test
    @DisplayName("PdfExporter should create PDF documents")
    void testPdfExporter() {
        DocumentExporter exporter = new PdfExporter();
        String result = exporter.export("Test Content");
        
        assertThat(result).isEqualTo("PDF: Test Content");
    }
    
    @Test
    @DisplayName("HtmlExporter should create HTML documents")
    void testHtmlExporter() {
        DocumentExporter exporter = new HtmlExporter();
        String result = exporter.export("Test Content");
        
        assertThat(result).isEqualTo("<html><body>Test Content</body></html>");
    }
    
    @Test
    @DisplayName("MarkdownExporter should create Markdown documents")
    void testMarkdownExporter() {
        DocumentExporter exporter = new MarkdownExporter();
        String result = exporter.export("Test Content");
        
        assertThat(result).isEqualTo("Test Content");
    }
    
    @Test
    @DisplayName("Different exporters should produce different output for same content")
    void testDifferentExportersProduceDifferentOutput() {
        String content = "Same Content";
        
        DocumentExporter pdfEx = new PdfExporter();
        DocumentExporter htmlEx = new HtmlExporter();
        DocumentExporter mdEx = new MarkdownExporter();
        
        String pdf = pdfEx.export(content);
        String html = htmlEx.export(content);
        String md = mdEx.export(content);
        
       assertThat(pdf).isNotEqualTo(html);
        assertThat(html).isNotEqualTo(md);
        assertThat(pdf).isNotEqualTo(md);
    }
}
