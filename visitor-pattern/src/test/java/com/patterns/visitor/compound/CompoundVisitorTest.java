package com.patterns.visitor.compound;

import com.patterns.visitor.isolation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for compound visitor patterns - FilteringVisitor and CompositeVisitor.
 */
@DisplayName("Visitor Pattern - Compound (Filtering & Composition)")
class CompoundVisitorTest {
    
    private Directory root;
    private Directory docs;
    private Directory images;
    
    @BeforeEach
    void setUp() {
        // Build file system structure
        root = new Directory("root");
        
        // Documents folder
        docs = new Directory("documents");
        docs.addElement(new File("report2023", 1024, "pdf"));
        docs.addElement(new File("report2024", 2048, "pdf"));
        docs.addElement(new File("summary", 512, "txt"));
        docs.addElement(new File("annual_report", 4096, "pdf"));
        root.addElement(docs);
        
        // Images folder
        images = new Directory("images");
        images.addElement(new File("photo1", 8192, "jpg"));
        images.addElement(new File("report_graph", 1536, "png"));
        images.addElement(new File("logo", 256, "png"));
        root.addElement(images);
        
        // Root level files
        root.addElement(new File("readme", 128, "txt"));
        root.addElement(new File("changelog", 64, "txt"));
    }
    
    // FilteringVisitor - Basic Filtering Tests
    
    @Test
    @DisplayName("FilteringVisitor: Should filter by name pattern only")
    void testFilterByNamePattern() {
        SizeCalculatorVisitor sizeVisitor = new SizeCalculatorVisitor();
        FilteringVisitor filter = new FilteringVisitor("report", sizeVisitor);
        
        root.accept(filter);
        
        // Should match: report2023.pdf, report2024.pdf, annual_report.pdf, report_graph.png
        assertThat(filter.getMatchCount()).isEqualTo(4);
        assertThat(filter.getMatchedFiles())
            .extracting(File::getName)
            .containsExactlyInAnyOrder("report2023", "report2024", "annual_report", "report_graph");
    }
    
    @Test
    @DisplayName("FilteringVisitor: Should filter by extension only")
    void testFilterByExtension() {
        SizeCalculatorVisitor sizeVisitor = new SizeCalculatorVisitor();
        FilteringVisitor filter = new FilteringVisitor(null, "pdf", sizeVisitor);
        
        root.accept(filter);
        
        // Should match all PDF files
        assertThat(filter.getMatchCount()).isEqualTo(3);
        assertThat(filter.getMatchedFiles())
            .extracting(File::getName)
            .containsExactlyInAnyOrder("report2023", "report2024", "annual_report");
    }
    
    @Test
    @DisplayName("FilteringVisitor: Should filter by both name and extension")
    void testFilterByBoth() {
        SizeCalculatorVisitor sizeVisitor = new SizeCalculatorVisitor();
        FilteringVisitor filter = new FilteringVisitor("report", "pdf", sizeVisitor);
        
        root.accept(filter);
        
        // Should match only PDF files containing "report"
        assertThat(filter.getMatchCount()).isEqualTo(3);
        assertThat(filter.getMatchedFiles())
            .extracting(File::getName)
            .containsExactlyInAnyOrder("report2023", "report2024", "annual_report");
    }
    
    @Test
    @DisplayName("FilteringVisitor: Should match all files when no criteria")
    void testFilterMatchAll() {
        SizeCalculatorVisitor sizeVisitor = new SizeCalculatorVisitor();
        FilteringVisitor filter = new FilteringVisitor(null, null, sizeVisitor);
        
        root.accept(filter);
        
        // Should match all 9 files
        assertThat(filter.getMatchCount()).isEqualTo(9);
    }
    
    @Test
    @DisplayName("FilteringVisitor: Should return empty when no matches")
    void testFilterNoMatches() {
        SizeCalculatorVisitor sizeVisitor = new SizeCalculatorVisitor();
        FilteringVisitor filter = new FilteringVisitor("nonexistent", "xyz", sizeVisitor);
        
        root.accept(filter);
        
        assertThat(filter.getMatchCount()).isZero();
        assertThat(filter.getMatchedFiles()).isEmpty();
    }
    
    // FilteringVisitor - Size Calculation Tests
    
    @Test
    @DisplayName("FilteringVisitor: Should calculate size only for matched files")
    void testFilteredSizeCalculation() {
        SizeCalculatorVisitor sizeVisitor = new SizeCalculatorVisitor();
        FilteringVisitor filter = new FilteringVisitor("report", "pdf", sizeVisitor);
        
        root.accept(filter);
        
        // report2023 (1024) + report2024 (2048) + annual_report (4096) = 7168
        assertThat(sizeVisitor.getTotalSize()).isEqualTo(7168);
    }
    
    @Test
    @DisplayName("FilteringVisitor: Should calculate size for PNG files")
    void testPngFilesSize() {
        SizeCalculatorVisitor sizeVisitor = new SizeCalculatorVisitor();
        FilteringVisitor filter = new FilteringVisitor(null, "png", sizeVisitor);
        
        root.accept(filter);
        
        // report_graph (1536) + logo (256) = 1792
        assertThat(sizeVisitor.getTotalSize()).isEqualTo(1792);
        assertThat(filter.getMatchCount()).isEqualTo(2);
    }
    
    @Test
    @DisplayName("FilteringVisitor: Should not add size for non-matched files")
    void testNonMatchedFilesNotCounted() {
        SizeCalculatorVisitor sizeVisitor = new SizeCalculatorVisitor();
        FilteringVisitor filter = new FilteringVisitor("report", "pdf", sizeVisitor);
        
        root.accept(filter);
        
        // Should NOT include summary.txt, readme.txt, changelog.txt, or images
        long totalSize = sizeVisitor.getTotalSize();
        assertThat(totalSize).isLessThan(10000); // Much less than total of all files
    }
    
    // FilteringVisitor - Search Integration Tests
    
    @Test
    @DisplayName("FilteringVisitor: Should work with FileSearchVisitor")
    void testFilterWithSearch() {
        // Create a search visitor that will be wrapped
        FileSearchVisitor searchVisitor = new FileSearchVisitor("photo");
        FilteringVisitor filter = new FilteringVisitor(null, "jpg", searchVisitor);
        
        root.accept(filter);
        
        // Should find photo1.jpg (matches "photo" and extension "jpg")
        assertThat(filter.getMatchCount()).isEqualTo(1);
        assertThat(filter.getMatchedFiles().get(0).getName()).isEqualTo("photo1");
    }
    
    // FilteringVisitor - Validation Tests
    
    @Test
    @DisplayName("FilteringVisitor: Should reject null target visitor")
    void testNullTargetVisitor() {
        assertThatThrownBy(() -> new FilteringVisitor("pattern", null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Target visitor cannot be null");
    }
    
    @Test
    @DisplayName("FilteringVisitor: Should expose filter criteria")
    void testGetFilterCriteria() {
        SizeCalculatorVisitor sizeVisitor = new SizeCalculatorVisitor();
        FilteringVisitor filter = new FilteringVisitor("report", "pdf", sizeVisitor);
        
        assertThat(filter.getSearchPattern()).isEqualTo("report");
        assertThat(filter.getExtension()).isEqualTo("pdf");
    }
    
    // CompositeVisitor Tests
    
    @Test
    @DisplayName("CompositeVisitor: Should apply multiple visitors")
    void testCompositeMultipleVisitors() {
        SizeCalculatorVisitor sizeCalc = new SizeCalculatorVisitor();
        FileSearchVisitor searchAll = new FileSearchVisitor(null);
        
        CompositeVisitor composite = new CompositeVisitor(sizeCalc, searchAll);
        root.accept(composite);
        
        // Both visitors should have processed all files
        assertThat(searchAll.getResults()).hasSize(9);
        assertThat(sizeCalc.getTotalSize()).isPositive();
    }
    
    @Test
    @DisplayName("CompositeVisitor: Should work with three visitors")
    void testCompositeThreeVisitors() {
        SizeCalculatorVisitor sizeCalc1 = new SizeCalculatorVisitor();
        SizeCalculatorVisitor sizeCalc2 = new SizeCalculatorVisitor();
        SizeCalculatorVisitor sizeCalc3 = new SizeCalculatorVisitor();
        
        CompositeVisitor composite = new CompositeVisitor(sizeCalc1, sizeCalc2, sizeCalc3);
        root.accept(composite);
        
        // All three should have same total
        long expectedTotal = sizeCalc1.getTotalSize();
        assertThat(sizeCalc2.getTotalSize()).isEqualTo(expectedTotal);
        assertThat(sizeCalc3.getTotalSize()).isEqualTo(expectedTotal);
    }
    
    @Test
    @DisplayName("CompositeVisitor: Should reject null visitors")
    void testCompositeNullVisitor() {
        SizeCalculatorVisitor validVisitor = new SizeCalculatorVisitor();
        
        assertThatThrownBy(() -> new CompositeVisitor(validVisitor, null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Visitor cannot be null");
    }
    
    @Test
    @DisplayName("CompositeVisitor: Should reject empty visitor array")
    void testCompositeEmptyArray() {
        assertThatThrownBy(() -> new CompositeVisitor())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("At least one visitor must be provided");
    }
    
    @Test
    @DisplayName("CompositeVisitor: Should expose visitor count")
    void testCompositeGetVisitorCount() {
        SizeCalculatorVisitor v1 = new SizeCalculatorVisitor();
        SizeCalculatorVisitor v2 = new SizeCalculatorVisitor();
        SizeCalculatorVisitor v3 = new SizeCalculatorVisitor();
        
        CompositeVisitor composite = new CompositeVisitor(v1, v2, v3);
        
        assertThat(composite.getVisitorCount()).isEqualTo(3);
        assertThat(composite.getVisitors()).hasSize(3);
    }
    
    // Combined Patterns - FilteringVisitor + CompositeVisitor
    
    @Test
    @DisplayName("Combined: Should filter with composite visitor")
    void testFilteringWithComposite() {
        // Create multiple operations to apply to filtered files
        SizeCalculatorVisitor sizeCalc = new SizeCalculatorVisitor();
        FileSearchVisitor search = new FileSearchVisitor(null);
        
        CompositeVisitor composite = new CompositeVisitor(sizeCalc, search);
        FilteringVisitor filter = new FilteringVisitor(null, "pdf", composite);
        
        root.accept(filter);
        
        // Should only process PDF files
        assertThat(filter.getMatchCount()).isEqualTo(3);
        assertThat(search.getResults()).hasSize(3);
        
        // Size should be PDF files only: 1024 + 2048 + 4096 = 7168
        assertThat(sizeCalc.getTotalSize()).isEqualTo(7168);
    }
    
    @Test
    @DisplayName("Combined: Should handle multiple filters with different operations")
    void testMultipleFiltersIndependently() {
        // Filter 1: PDF reports
        SizeCalculatorVisitor pdfSizer = new SizeCalculatorVisitor();
        FilteringVisitor pdfFilter = new FilteringVisitor("report", "pdf", pdfSizer);
        
        // Filter 2: PNG images
        SizeCalculatorVisitor pngSizer = new SizeCalculatorVisitor();
        FilteringVisitor pngFilter = new FilteringVisitor(null, "png", pngSizer);
        
        // Apply both filters
        root.accept(pdfFilter);
        root.accept(pngFilter);
        
        // Each should have processed different files
        assertThat(pdfFilter.getMatchCount()).isEqualTo(3);
        assertThat(pngFilter.getMatchCount()).isEqualTo(2);
        
        // Sizes should be different
        assertThat(pdfSizer.getTotalSize()).isEqualTo(7168); // PDFs with "report"
        assertThat(pngSizer.getTotalSize()).isEqualTo(1792); // All PNGs
    }
    
    @Test
    @DisplayName("Combined: Should calculate size of TXT files")
    void testTextFilesFiltering() {
        SizeCalculatorVisitor txtSizer = new SizeCalculatorVisitor();
        FilteringVisitor txtFilter = new FilteringVisitor(null, "txt", txtSizer);
        
        root.accept(txtFilter);
        
        // summary (512) + readme (128) + changelog (64) = 704
        assertThat(txtFilter.getMatchCount()).isEqualTo(3);
        assertThat(txtSizer.getTotalSize()).isEqualTo(704);
    }
    
    // Real-World Scenario Tests
    
    @Test
    @DisplayName("Scenario: Find and size all documentation files")
    void testDocumentationFilesScenario() {
        // Find all readme and changelog files (TXT extension)
        SizeCalculatorVisitor sizeCalc = new SizeCalculatorVisitor();
        FilteringVisitor filter = new FilteringVisitor(null, "txt", sizeCalc);
        
        root.accept(filter);
        
        assertThat(filter.getMatchCount()).isEqualTo(3);
        assertThat(filter.getMatchedFiles())
            .extracting(File::getName)
            .containsExactlyInAnyOrder("summary", "readme", "changelog");
        assertThat(sizeCalc.getTotalSize()).isEqualTo(704);
    }
    
    @Test
    @DisplayName("Scenario: Process only large image files")
    void testLargeImageFilesScenario() {
        // Custom visitor that only counts files > 1000 bytes
        SizeCalculatorVisitor sizeCalc = new SizeCalculatorVisitor();
        FileSearchVisitor search = new FileSearchVisitor(null);
        
        CompositeVisitor composite = new CompositeVisitor(sizeCalc, search);
        FilteringVisitor filter = new FilteringVisitor(null, "jpg", composite);
        
        root.accept(filter);
        
        // photo1.jpg (8192 bytes) - large enough
        assertThat(filter.getMatchCount()).isEqualTo(1);
        assertThat(sizeCalc.getTotalSize()).isEqualTo(8192);
    }
    
    @Test
    @DisplayName("Scenario: Generate report of all PDFs")
    void testPdfReportScenario() {
        SizeCalculatorVisitor sizeCalc = new SizeCalculatorVisitor();
        FilteringVisitor pdfFilter = new FilteringVisitor(null, "pdf", sizeCalc);
        
        root.accept(pdfFilter);
        
        List<File> pdfs = pdfFilter.getMatchedFiles();
        long totalSize = sizeCalc.getTotalSize();
        
        assertThat(pdfs).hasSize(3);
        assertThat(totalSize).isEqualTo(7168);
        
        // Verify we can generate a report
        String report = String.format(
            "PDF Report:%n" +
            "Files found: %d%n" +
            "Total size: %d bytes%n" +
            "Average size: %d bytes",
            pdfs.size(),
            totalSize,
            totalSize / pdfs.size()
        );
        
        assertThat(report).contains("Files found: 3");
        assertThat(report).contains("Total size: 7168 bytes");
    }
}
