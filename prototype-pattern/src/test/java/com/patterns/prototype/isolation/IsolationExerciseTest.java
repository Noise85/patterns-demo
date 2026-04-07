package com.patterns.prototype.isolation;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Exercise 1: Document Cloning
 */
class IsolationExerciseTest {

    @Test
    void section_clone_createsIndependentCopy() {
        Section original = new Section("Introduction", "Welcome to the guide",
                Map.of("author", "Alice", "version", "1.0"));

        Section clone = original.clone();

        assertThat(clone).isNotSameAs(original);
        assertThat(clone.getHeading()).isEqualTo(original.getHeading());
        assertThat(clone.getContent()).isEqualTo(original.getContent());
        assertThat(clone.getMetadata()).isEqualTo(original.getMetadata());
    }

    @Test
    void section_clone_deepCopiesMetadata() {
        Map<String, String> metadata = Map.of("author", "Bob", "tags", "intro");
        Section original = new Section("Chapter 1", "Content here", metadata);

        Section clone = original.clone();

        // Metadata should be equal but independent
        assertThat(clone.getMetadata()).isEqualTo(original.getMetadata());
        assertThat(clone.getMetadata()).isNotSameAs(original.getMetadata());
    }

    @Test
    void document_clone_createsIndependentCopy() {
        Section section1 = new Section("Intro", "Introduction text", Map.of("page", "1"));
        Section section2 = new Section("Body", "Main content", Map.of("page", "2"));
        Document original = new Document("My Document", List.of(section1, section2));

        Document clone = original.clone();

        assertThat(clone).isNotSameAs(original);
        assertThat(clone.getTitle()).isEqualTo(original.getTitle());
        assertThat(clone.getSections()).hasSize(original.getSections().size());
    }

    @Test
    void document_clone_deepCopiesSections() {
        Section section = new Section("Test", "Content", Map.of("key", "value"));
        Document original = new Document("Doc", List.of(section));

        Document clone = original.clone();

        // Sections should be equal but not the same instance
        assertThat(clone.getSections().get(0)).isNotSameAs(original.getSections().get(0));
        assertThat(clone.getSections().get(0).getHeading())
                .isEqualTo(original.getSections().get(0).getHeading());
    }

    @Test
    void modifyingClone_doesNotAffectOriginal_sections() {
        Section originalSection = new Section("Original", "Original content", Map.of("v", "1"));
        Document original = new Document("Original Doc", List.of(originalSection));

        Document clone = original.clone();
        
        // Get defensive copy of sections to modify
        var clonedSections = clone.getSections();
        Section modifiedSection = new Section("Modified", "Modified content", Map.of("v", "2"));
        
        // Verify original is unchanged
        assertThat(original.getSections()).hasSize(1);
        assertThat(original.getSections().get(0).getHeading()).isEqualTo("Original");
    }

    @Test
    void clone_withMultipleSections() {
        Section s1 = new Section("S1", "Content1", Map.of("id", "1"));
        Section s2 = new Section("S2", "Content2", Map.of("id", "2"));
        Section s3 = new Section("S3", "Content3", Map.of("id", "3"));
        
        Document original = new Document("Multi-Section Doc", List.of(s1, s2, s3));
        Document clone = original.clone();

        assertThat(clone.getSections()).hasSize(3);
        
        // Each section should be independently cloned
        for (int i = 0; i < 3; i++) {
            assertThat(clone.getSections().get(i))
                    .isNotSameAs(original.getSections().get(i));
            assertThat(clone.getSections().get(i).getHeading())
                    .isEqualTo(original.getSections().get(i).getHeading());
        }
    }

    @Test
    void clone_withEmptyMetadata() {
        Section section = new Section("Test", "Content", Map.of());
        
        Section clone = section.clone();

        assertThat(clone.getMetadata()).isEmpty();
        assertThat(clone.getMetadata()).isNotSameAs(section.getMetadata());
    }

    @Test
    void clone_preservesAllData() {
        Map<String, String> metadata = Map.of(
                "author", "John Doe",
                "date", "2026-04-07",
                "version", "2.1",
                "status", "published"
        );
        Section section = new Section("Complex Section", "Very long content...", metadata);
        
        Section clone = section.clone();

        assertThat(clone.getHeading()).isEqualTo("Complex Section");
        assertThat(clone.getContent()).isEqualTo("Very long content...");
        assertThat(clone.getMetadata())
                .hasSize(4)
                .containsEntry("author", "John Doe")
                .containsEntry("date", "2026-04-07")
                .containsEntry("version", "2.1")
                .containsEntry("status", "published");
    }

    @Test
    void multipleClones_areIndependent() {
        Section original = new Section("Original", "Content", Map.of("key", "value"));
        
        Section clone1 = original.clone();
        Section clone2 = original.clone();
        
        assertThat(clone1).isNotSameAs(clone2);
        assertThat(clone1).isNotSameAs(original);
        assertThat(clone2).isNotSameAs(original);
    }
}
