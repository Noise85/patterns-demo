package com.patterns.visitor.isolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Visitor Pattern - Isolation Exercise (File System).
 */
@DisplayName("Visitor Pattern - File System Traversal")
class IsolationExerciseTest {
    
    private Directory root;
    private Directory documents;
    private Directory photos;
    private File report;
    private File notes;
    private File vacation;
    private File family;
    
    @BeforeEach
    void setUp() {
        root = new Directory("root");
        documents = new Directory("documents");
        photos = new Directory("photos");
        
        report = new File("report", 1024, "pdf");
        notes = new File("notes", 512, "txt");
        vacation = new File("vacation", 2048, "jpg");
        family = new File("family", 1536, "jpg");
        
        documents.addElement(report);
        documents.addElement(notes);
        photos.addElement(vacation);
        photos.addElement(family);
        
        root.addElement(documents);
        root.addElement(photos);
    }
    
    @Test
    @DisplayName("Should add element to directory")
    void testAddElement() {
        Directory dir = new Directory("test");
        File file = new File("test", 100, "txt");
        
        dir.addElement(file);
        
        assertThat(dir.getChildCount()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("Should accept visitor for file")
    void testFileAcceptsVisitor() {
        SizeCalculatorVisitor visitor = new SizeCalculatorVisitor();
        
        report.accept(visitor);
        
        assertThat(visitor.getTotalSize()).isEqualTo(1024);
    }
    
    @Test
    @DisplayName("Should accept visitor for directory")
    void testDirectoryAcceptsVisitor() {
        SizeCalculatorVisitor visitor = new SizeCalculatorVisitor();
        
        documents.accept(visitor);
        
        assertThat(visitor.getTotalSize()).isEqualTo(1536); // 1024 + 512
    }
    
    @Test
    @DisplayName("Should calculate total size for nested structure")
    void testSizeCalculatorNested() {
        SizeCalculatorVisitor visitor = new SizeCalculatorVisitor();
        
        root.accept(visitor);
        
        assertThat(visitor.getTotalSize()).isEqualTo(5120); // 1024 + 512 + 2048 + 1536
    }
    
    @Test
    @DisplayName("Should calculate size for empty directory")
    void testSizeCalculatorEmptyDirectory() {
        Directory emptyDir = new Directory("empty");
        SizeCalculatorVisitor visitor = new SizeCalculatorVisitor();
        
        emptyDir.accept(visitor);
        
        assertThat(visitor.getTotalSize()).isZero();
    }
    
    @Test
    @DisplayName("Should search files by name pattern")
    void testFileSearchByName() {
        FileSearchVisitor visitor = new FileSearchVisitor("vacation");
        
        root.accept(visitor);
        
        assertThat(visitor.getResultCount()).isEqualTo(1);
        assertThat(visitor.getResults()).hasSize(1);
        assertThat(visitor.getResults().get(0).getName()).isEqualTo("vacation");
    }
    
    @Test
    @DisplayName("Should search files by extension")
    void testFileSearchByExtension() {
        FileSearchVisitor visitor = new FileSearchVisitor(null, "jpg");
        
        root.accept(visitor);
        
        assertThat(visitor.getResultCount()).isEqualTo(2);
        assertThat(visitor.getResults())
            .extracting(File::getName)
            .containsExactlyInAnyOrder("vacation", "family");
    }
    
    @Test
    @DisplayName("Should find no results when pattern doesn't match")
    void testFileSearchNoResults() {
        FileSearchVisitor visitor = new FileSearchVisitor("nonexistent");
        
        root.accept(visitor);
        
        assertThat(visitor.getResultCount()).isZero();
        assertThat(visitor.getResults()).isEmpty();
    }
    
    @Test
    @DisplayName("Should search in nested directories")
    void testFileSearchNested() {
        FileSearchVisitor visitor = new FileSearchVisitor("report");
        
        root.accept(visitor);
        
        assertThat(visitor.getResultCount()).isEqualTo(1);
        assertThat(visitor.getResults().get(0)).isEqualTo(report);
    }
    
    @Test
    @DisplayName("Should calculate size of single file")
    void testSingleFileSize() {
        SizeCalculatorVisitor visitor = new SizeCalculatorVisitor();
        
        vacation.accept(visitor);
        
        assertThat(visitor.getTotalSize()).isEqualTo(2048);
    }
    
    @Test
    @DisplayName("Should search by both name and extension")
    void testSearchByNameAndExtension() {
        FileSearchVisitor visitor = new FileSearchVisitor("family", "jpg");
        
        root.accept(visitor);
        
        assertThat(visitor.getResultCount()).isEqualTo(1);
        assertThat(visitor.getResults().get(0).getName()).isEqualTo("family");
    }
    
    @Test
    @DisplayName("Should not match when only one criterion matches")
    void testSearchBothCriteriaRequired() {
        FileSearchVisitor visitor = new FileSearchVisitor("family", "txt");
        
        root.accept(visitor);
        
        assertThat(visitor.getResultCount()).isZero();
    }
    
    @Test
    @DisplayName("Should visit multiple levels deep")
    void testMultipleLevelsDeep() {
        Directory level1 = new Directory("level1");
        Directory level2 = new Directory("level2");
        Directory level3 = new Directory("level3");
        File deepFile = new File("deep", 256, "dat");
        
        level3.addElement(deepFile);
        level2.addElement(level3);
        level1.addElement(level2);
        
        SizeCalculatorVisitor visitor = new SizeCalculatorVisitor();
        level1.accept(visitor);
        
        assertThat(visitor.getTotalSize()).isEqualTo(256);
    }
    
    @Test
    @DisplayName("Should handle directory with only subdirectories")
    void testDirectoryOnlySubdirectories() {
        Directory parent = new Directory("parent");
        Directory child1 = new Directory("child1");
        Directory child2 = new Directory("child2");
        
        parent.addElement(child1);
        parent.addElement(child2);
        
        SizeCalculatorVisitor visitor = new SizeCalculatorVisitor();
        parent.accept(visitor);
        
        assertThat(visitor.getTotalSize()).isZero();
    }
    
    @Test
    @DisplayName("Should verify file properties")
    void testFileProperties() {
        assertThat(report.getName()).isEqualTo("report");
        assertThat(report.getSize()).isEqualTo(1024);
        assertThat(report.getExtension()).isEqualTo("pdf");
    }
    
    @Test
    @DisplayName("Should verify directory properties")
    void testDirectoryProperties() {
        assertThat(documents.getName()).isEqualTo("documents");
        assertThat(documents.getChildCount()).isEqualTo(2);
    }
    
    @Test
    @DisplayName("Should return defensive copy of children")
    void testGetChildrenDefensiveCopy() {
        List<FileSystemElement> children = documents.getChildren();
        int originalSize = children.size();
        
        children.clear();
        
        assertThat(documents.getChildCount()).isEqualTo(originalSize);
    }
    
    @Test
    @DisplayName("Should handle mixed files and directories")
    void testMixedFilesAndDirectories() {
        Directory mixed = new Directory("mixed");
        mixed.addElement(new File("file1", 100, "txt"));
        mixed.addElement(new Directory("subdir"));
        mixed.addElement(new File("file2", 200, "pdf"));
        
        SizeCalculatorVisitor visitor = new SizeCalculatorVisitor();
        mixed.accept(visitor);
        
        assertThat(visitor.getTotalSize()).isEqualTo(300);
    }
    
    @Test
    @DisplayName("Should search case-sensitively")
    void testSearchCaseSensitive() {
        FileSearchVisitor visitor = new FileSearchVisitor("VACATION");
        
        root.accept(visitor);
        
        assertThat(visitor.getResultCount()).isZero();
    }
    
    @Test
    @DisplayName("Should find partial name matches")
    void testPartialNameMatch() {
        FileSearchVisitor visitor = new FileSearchVisitor("port");
        
        root.accept(visitor);
        
        assertThat(visitor.getResultCount()).isEqualTo(1);
        assertThat(visitor.getResults().get(0).getName()).isEqualTo("report");
    }
}
