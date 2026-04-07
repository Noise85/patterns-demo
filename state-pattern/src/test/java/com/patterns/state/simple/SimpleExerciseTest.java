package com.patterns.state.simple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for enum-based State implementation.
 * This demonstrates the alternative approach using enums instead of separate state classes.
 */
@DisplayName("State Pattern - Simple Enum-Based Implementation")
class SimpleExerciseTest {
    
    private Document document;
    
    @BeforeEach
    void setUp() {
        document = new Document("Test Article");
    }
    
    @Test
    @DisplayName("Should start in DRAFT state")
    void testInitialState() {
        assertThat(document.getState()).isEqualTo(DocumentState.DRAFT);
        assertThat(document.getCurrentStateName()).isEqualTo("DRAFT");
    }
    
    @Test
    @DisplayName("Should allow editing in DRAFT state")
    void testEditInDraft() {
        document.edit();
        
        assertThat(document.getEditCount()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("Should increment edit count on multiple edits")
    void testMultipleEdits() {
        document.edit();
        document.edit();
        document.edit();
        
        assertThat(document.getEditCount()).isEqualTo(3);
    }
    
    @Test
    @DisplayName("Should transition from DRAFT to REVIEW on submit")
    void testDraftToReview() {
        document.submit();
        
        assertThat(document.getState()).isEqualTo(DocumentState.REVIEW);
    }
    
    @Test
    @DisplayName("Should not allow edit in REVIEW state")
    void testCannotEditInReview() {
        document.submit();
        
        assertThatThrownBy(() -> document.edit())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Cannot edit");
    }
    
    @Test
    @DisplayName("Should not allow submit in REVIEW state")
    void testCannotSubmitInReview() {
        document.submit();
        
        assertThatThrownBy(() -> document.submit())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Cannot submit");
    }
    
    @Test
    @DisplayName("Should transition from REVIEW to PUBLISHED on approve")
    void testReviewToPublished() {
        document.submit();
        document.approve();
        
        assertThat(document.getState()).isEqualTo(DocumentState.PUBLISHED);
    }
    
    @Test
    @DisplayName("Should transition from REVIEW to DRAFT on reject")
    void testReviewToDraft() {
        document.submit();
        document.reject();
        
        assertThat(document.getState()).isEqualTo(DocumentState.DRAFT);
    }
    
    @Test
    @DisplayName("Should allow editing after rejection")
    void testEditAfterRejection() {
        document.edit();
        document.submit();
        document.reject();
        
        document.edit();
        
        assertThat(document.getEditCount()).isEqualTo(2);
    }
    
    @Test
    @DisplayName("Should not allow approve from DRAFT state")
    void testCannotApproveFromDraft() {
        assertThatThrownBy(() -> document.approve())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Cannot approve");
    }
    
    @Test
    @DisplayName("Should not allow reject from DRAFT state")
    void testCannotRejectFromDraft() {
        assertThatThrownBy(() -> document.reject())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Cannot reject");
    }
    
    @Test
    @DisplayName("Should transition from PUBLISHED to ARCHIVED on archive")
    void testPublishedToArchived() {
        document.submit();
        document.approve();
        document.archive();
        
        assertThat(document.getState()).isEqualTo(DocumentState.ARCHIVED);
    }
    
    @Test
    @DisplayName("Should not allow edit in PUBLISHED state")
    void testCannotEditInPublished() {
        document.submit();
        document.approve();
        
        assertThatThrownBy(() -> document.edit())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Cannot edit");
    }
    
    @Test
    @DisplayName("Should not allow archive from DRAFT state")
    void testCannotArchiveFromDraft() {
        assertThatThrownBy(() -> document.archive())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Cannot archive");
    }
    
    @Test
    @DisplayName("Should not allow archive from REVIEW state")
    void testCannotArchiveFromReview() {
        document.submit();
        
        assertThatThrownBy(() -> document.archive())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Cannot archive");
    }
    
    @Test
    @DisplayName("Should not allow any operations in ARCHIVED state")
    void testNoOperationsInArchived() {
        document.submit();
        document.approve();
        document.archive();
        
        assertThatThrownBy(() -> document.edit())
            .isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> document.submit())
            .isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> document.approve())
            .isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> document.reject())
            .isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> document.archive())
            .isInstanceOf(IllegalStateException.class);
    }
    
    @Test
    @DisplayName("Should complete full workflow")
    void testCompleteWorkflow() {
        // DRAFT
        assertThat(document.getState()).isEqualTo(DocumentState.DRAFT);
        document.edit();
        
        // REVIEW
        document.submit();
        assertThat(document.getState()).isEqualTo(DocumentState.REVIEW);
        
        // PUBLISHED
        document.approve();
        assertThat(document.getState()).isEqualTo(DocumentState.PUBLISHED);
        
        // ARCHIVED
        document.archive();
        assertThat(document.getState()).isEqualTo(DocumentState.ARCHIVED);
    }
    
    @Test
    @DisplayName("Should handle rejection workflow")
    void testRejectionWorkflow() {
        document.edit();
        document.submit();
        assertThat(document.getState()).isEqualTo(DocumentState.REVIEW);
        
        document.reject();
        assertThat(document.getState()).isEqualTo(DocumentState.DRAFT);
        
        document.edit();
        document.submit();
        assertThat(document.getState()).isEqualTo(DocumentState.REVIEW);
        
        document.approve();
        assertThat(document.getState()).isEqualTo(DocumentState.PUBLISHED);
    }
    
    @Test
    @DisplayName("Should preserve edit count across state transitions")
    void testEditCountPersistence() {
        document.edit();
        document.edit();
        document.submit();
        document.reject();
        document.edit();
        
        assertThat(document.getEditCount()).isEqualTo(3);
    }
    
    @Test
    @DisplayName("Should verify document title")
    void testDocumentTitle() {
        assertThat(document.getTitle()).isEqualTo("Test Article");
    }
    
    @Test
    @DisplayName("Should allow state comparison using enum equality")
    void testEnumEquality() {
        assertThat(document.getState() == DocumentState.DRAFT).isTrue();
        
        document.submit();
        assertThat(document.getState() == DocumentState.REVIEW).isTrue();
        assertThat(document.getState() == DocumentState.DRAFT).isFalse();
    }
    
    @Test
    @DisplayName("Should support switch expressions on state")
    void testSwitchExpression() {
        String description = switch (document.getState()) {
            case DRAFT -> "Being edited";
            case REVIEW -> "Under review";
            case PUBLISHED -> "Live";
            case ARCHIVED -> "Archived";
        };
        
        assertThat(description).isEqualTo("Being edited");
        
        document.submit();
        
        String afterSubmit = switch (document.getState()) {
            case DRAFT -> "Being edited";
            case REVIEW -> "Under review";
            case PUBLISHED -> "Live";
            case ARCHIVED -> "Archived";
        };
        
        assertThat(afterSubmit).isEqualTo("Under review");
    }
}
