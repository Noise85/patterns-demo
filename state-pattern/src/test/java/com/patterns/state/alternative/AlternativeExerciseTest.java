package com.patterns.state.alternative;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for alternative State pattern implementation using default interface methods.
 * Verifies that the Template Method pattern at interface level works correctly.
 */
@DisplayName("State Pattern - Alternative Implementation (Default Interface Methods)")
class AlternativeExerciseTest {
    
    private Document document;
    
    @BeforeEach
    void setUp() {
        document = new Document("Test Article");
    }
    
    @Test
    @DisplayName("Should start in Draft state")
    void testInitialState() {
        assertThat(document.getCurrentStateName()).isEqualTo("Draft");
    }
    
    @Test
    @DisplayName("Should allow editing in Draft state")
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
    @DisplayName("Should transition from Draft to Review on submit")
    void testDraftToReview() {
        document.submit();
        
        assertThat(document.getCurrentStateName()).isEqualTo("Review");
    }
    
    @Test
    @DisplayName("Should not allow edit in Review state (uses default method)")
    void testCannotEditInReview() {
        document.submit();
        
        assertThatThrownBy(() -> document.edit())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Cannot edit document in Review state");
    }
    
    @Test
    @DisplayName("Should not allow submit in Review state (uses default method)")
    void testCannotSubmitInReview() {
        document.submit();
        
        assertThatThrownBy(() -> document.submit())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Cannot submit document in Review state");
    }
    
    @Test
    @DisplayName("Should transition from Review to Published on approve")
    void testReviewToPublished() {
        document.submit();
        document.approve();
        
        assertThat(document.getCurrentStateName()).isEqualTo("Published");
    }
    
    @Test
    @DisplayName("Should transition from Review to Draft on reject")
    void testReviewToDraft() {
        document.submit();
        document.reject();
        
        assertThat(document.getCurrentStateName()).isEqualTo("Draft");
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
    @DisplayName("Should not allow approve from Draft (uses default method)")
    void testCannotApproveFromDraft() {
        assertThatThrownBy(() -> document.approve())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Cannot approve document in Draft state");
    }
    
    @Test
    @DisplayName("Should not allow reject from Draft (uses default method)")
    void testCannotRejectFromDraft() {
        assertThatThrownBy(() -> document.reject())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Cannot reject document in Draft state");
    }
    
    @Test
    @DisplayName("Should transition from Published to Archived on archive")
    void testPublishedToArchived() {
        document.submit();
        document.approve();
        document.archive();
        
        assertThat(document.getCurrentStateName()).isEqualTo("Archived");
    }
    
    @Test
    @DisplayName("Should not allow edit in Published state (uses default method)")
    void testCannotEditInPublished() {
        document.submit();
        document.approve();
        
        assertThatThrownBy(() -> document.edit())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Cannot edit document in Published state");
    }
    
    @Test
    @DisplayName("Should not allow archive from Draft (uses default method)")
    void testCannotArchiveFromDraft() {
        assertThatThrownBy(() -> document.archive())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Cannot archive document in Draft state");
    }
    
    @Test
    @DisplayName("Should not allow archive from Review (uses default method)")
    void testCannotArchiveFromReview() {
        document.submit();
        
        assertThatThrownBy(() -> document.archive())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Cannot archive document in Review state");
    }
    
    @Test
    @DisplayName("Should not allow any operations in Archived state (all use defaults)")
    void testNoOperationsInArchived() {
        document.submit();
        document.approve();
        document.archive();
        
        assertThatThrownBy(() -> document.edit())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Cannot edit document in Archived state");
            
        assertThatThrownBy(() -> document.submit())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Cannot submit document in Archived state");
            
        assertThatThrownBy(() -> document.approve())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Cannot approve document in Archived state");
            
        assertThatThrownBy(() -> document.reject())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Cannot reject document in Archived state");
            
        assertThatThrownBy(() -> document.archive())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Cannot archive document in Archived state");
    }
    
    @Test
    @DisplayName("Should complete full workflow")
    void testCompleteWorkflow() {
        // Draft
        assertThat(document.getCurrentStateName()).isEqualTo("Draft");
        document.edit();
        
        // Review
        document.submit();
        assertThat(document.getCurrentStateName()).isEqualTo("Review");
        
        // Published
        document.approve();
        assertThat(document.getCurrentStateName()).isEqualTo("Published");
        
        // Archived
        document.archive();
        assertThat(document.getCurrentStateName()).isEqualTo("Archived");
    }
    
    @Test
    @DisplayName("Should handle rejection workflow")
    void testRejectionWorkflow() {
        document.edit();
        document.submit();
        assertThat(document.getCurrentStateName()).isEqualTo("Review");
        
        document.reject();
        assertThat(document.getCurrentStateName()).isEqualTo("Draft");
        
        document.edit();
        document.submit();
        assertThat(document.getCurrentStateName()).isEqualTo("Review");
        
        document.approve();
        assertThat(document.getCurrentStateName()).isEqualTo("Published");
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
}
