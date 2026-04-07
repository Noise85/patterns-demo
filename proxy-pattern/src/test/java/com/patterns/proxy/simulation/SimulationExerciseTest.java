package com.patterns.proxy.simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Proxy pattern simulation exercise (protection proxy for document management).
 */
class SimulationExerciseTest {
    
    private User viewer;
    private User editor;
    private User admin;
    
    @BeforeEach
    void setUp() {
        viewer = new User("alice", Role.VIEWER);
        editor = new User("bob", Role.EDITOR);
        admin = new User("charlie", Role.ADMIN);
    }
    
    @Test
    @DisplayName("RealDocument stores and retrieves content")
    void testRealDocumentBasics() {
        RealDocument doc = new RealDocument("Test", "Content here", "Author", Role.VIEWER);
        
        assertThat(doc.getContent()).isEqualTo("Content here");
        assertThat(doc.getTitle()).isEqualTo("Test");
        assertThat(doc.getAuthor()).isEqualTo("Author");
        assertThat(doc.isDeleted()).isFalse();
    }
    
    @Test
    @DisplayName("RealDocument allows content editing")
    void testRealDocumentEdit() {
        RealDocument doc = new RealDocument("Test", "Original", "Author", Role.VIEWER);
        
        doc.editContent("Modified");
        
        assertThat(doc.getContent()).isEqualTo("Modified");
    }
    
    @Test
    @DisplayName("RealDocument can be deleted")
    void testRealDocumentDelete() {
        RealDocument doc = new RealDocument("Test", "Content", "Author", Role.VIEWER);
        
        assertThat(doc.isDeleted()).isFalse();
        
        doc.delete();
        
        assertThat(doc.isDeleted()).isTrue();
    }
    
    @Test
    @DisplayName("RealDocument required role can be changed")
    void testRealDocumentRoleChange() {
        RealDocument doc = new RealDocument("Test", "Content", "Author", Role.VIEWER);
        
        assertThat(doc.getRequiredRole()).isEqualTo(Role.VIEWER);
        
        doc.setRequiredRole(Role.ADMIN);
        
        assertThat(doc.getRequiredRole()).isEqualTo(Role.ADMIN);
    }
    
    @Test
    @DisplayName("RealDocument provides formatted metadata")
    void testRealDocumentMetadata() {
        RealDocument doc = new RealDocument("Q4 Report", "Data...", "Finance", Role.EDITOR);
        
        String metadata = doc.getMetadata();
        
        assertThat(metadata)
            .contains("Q4 Report")
            .contains("Finance")
            .contains("EDITOR");
    }
    
    @Test
    @DisplayName("Viewer can read document content")
    void testViewerCanRead() {
        Document doc = DocumentFactory.createSecureDocument(
            "Public Doc",
            "Public content",
            "Author",
            Role.VIEWER,
            viewer
        );
        
        String content = doc.getContent();
        
        assertThat(content).isEqualTo("Public content");
    }
    
    @Test
    @DisplayName("Viewer cannot edit document content")
    void testViewerCannotEdit() {
        Document doc = DocumentFactory.createSecureDocument(
            "Public Doc",
            "Original",
            "Author",
            Role.VIEWER,
            viewer
        );
        
        assertThatThrownBy(() -> doc.editContent("Modified"))
            .isInstanceOf(AccessDeniedException.class)
            .hasMessageContaining("alice")
            .hasMessageContaining("VIEWER")
            .hasMessageContaining("editContent");
    }
    
    @Test
    @DisplayName("Viewer cannot delete document")
    void testViewerCannotDelete() {
        Document doc = DocumentFactory.createSecureDocument(
            "Public Doc",
            "Content",
            "Author",
            Role.VIEWER,
            viewer
        );
        
        assertThatThrownBy(() -> doc.delete())
            .isInstanceOf(AccessDeniedException.class)
            .hasMessageContaining("alice")
            .hasMessageContaining("delete");
    }
    
    @Test
    @DisplayName("Viewer cannot change permissions")
    void testViewerCannotSetPermissions() {
        Document doc = DocumentFactory.createSecureDocument(
            "Public Doc",
            "Content",
            "Author",
            Role.VIEWER,
            viewer
        );
        
        assertThatThrownBy(() -> doc.setRequiredRole(Role.ADMIN))
            .isInstanceOf(AccessDeniedException.class)
            .hasMessageContaining("alice");
    }
    
    @Test
    @DisplayName("Editor can read document")
    void testEditorCanRead() {
        Document doc = DocumentFactory.createSecureDocument(
            "Doc",
            "Content",
            "Author",
            Role.VIEWER,
            editor
        );
        
        String content = doc.getContent();
        
        assertThat(content).isEqualTo("Content");
    }
    
    @Test
    @DisplayName("Editor can edit document")
    void testEditorCanEdit() {
        Document doc = DocumentFactory.createSecureDocument(
            "Doc",
            "Original",
            "Author",
            Role.VIEWER,
            editor
        );
        
        doc.editContent("Modified");
        
        assertThat(doc.getContent()).isEqualTo("Modified");
    }
    
    @Test
    @DisplayName("Editor cannot delete document")
    void testEditorCannotDelete() {
        Document doc = DocumentFactory.createSecureDocument(
            "Doc",
            "Content",
            "Author",
            Role.VIEWER,
            editor
        );
        
        assertThatThrownBy(() -> doc.delete())
            .isInstanceOf(AccessDeniedException.class)
            .hasMessageContaining("bob")
            .hasMessageContaining("EDITOR");
    }
    
    @Test
    @DisplayName("Editor cannot change permissions")
    void testEditorCannotSetPermissions() {
        Document doc = DocumentFactory.createSecureDocument(
            "Doc",
            "Content",
            "Author",
            Role.VIEWER,
            editor
        );
        
        assertThatThrownBy(() -> doc.setRequiredRole(Role.ADMIN))
            .isInstanceOf(AccessDeniedException.class);
    }
    
    @Test
    @DisplayName("Admin can read document")
    void testAdminCanRead() {
        Document doc = DocumentFactory.createSecureDocument(
            "Doc",
            "Content",
            "Author",
            Role.VIEWER,
            admin
        );
        
        String content = doc.getContent();
        
        assertThat(content).isEqualTo("Content");
    }
    
    @Test
    @DisplayName("Admin can edit document")
    void testAdminCanEdit() {
        Document doc = DocumentFactory.createSecureDocument(
            "Doc",
            "Original",
            "Author",
            Role.VIEWER,
            admin
        );
        
        doc.editContent("Modified");
        
        assertThat(doc.getContent()).isEqualTo("Modified");
    }
    
    @Test
    @DisplayName("Admin can delete document")
    void testAdminCanDelete() {
        Document doc = DocumentFactory.createSecureDocument(
            "Doc",
            "Content",
            "Author",
            Role.VIEWER,
            admin
        );
        
        doc.delete();
        
        assertThat(doc.isDeleted()).isTrue();
    }
    
    @Test
    @DisplayName("Admin can change document permissions")
    void testAdminCanSetPermissions() {
        Document doc = DocumentFactory.createSecureDocument(
            "Doc",
            "Content",
            "Author",
            Role.VIEWER,
            admin
        );
        
        doc.setRequiredRole(Role.ADMIN);
        
        // Verify by checking metadata
        assertThat(doc.getMetadata()).contains("ADMIN");
    }
    
    @Test
    @DisplayName("Proxy logs successful operations")
    void testAuditLogSuccessful() {
        Document doc = DocumentFactory.createSecureDocument(
            "Doc",
            "Content",
            "Author",
            Role.VIEWER,
            editor
        );
        
        doc.getContent();
        doc.editContent("New");
        
        DocumentProxy proxy = (DocumentProxy) doc;
        List<AuditEntry> log = proxy.getAuditLog();
        
        assertThat(log).hasSize(2);
        assertThat(log.get(0).successful()).isTrue();
        assertThat(log.get(0).operation()).isEqualTo("getContent");
        assertThat(log.get(0).username()).isEqualTo("bob");
        
        assertThat(log.get(1).successful()).isTrue();
        assertThat(log.get(1).operation()).isEqualTo("editContent");
    }
    
    @Test
    @DisplayName("Proxy logs failed operations")
    void testAuditLogFailed() {
        Document doc = DocumentFactory.createSecureDocument(
            "Doc",
            "Content",
            "Author",
            Role.VIEWER,
            viewer
        );
        
        doc.getContent();  // Should succeed
        
        try {
            doc.editContent("New");  // Should fail
        } catch (AccessDeniedException e) {
            // Expected
        }
        
        DocumentProxy proxy = (DocumentProxy) doc;
        List<AuditEntry> log = proxy.getAuditLog();
        
        assertThat(log).hasSize(2);
        assertThat(log.get(0).successful()).isTrue();
        assertThat(log.get(1).successful()).isFalse();
        assertThat(log.get(1).operation()).isEqualTo("editContent");
    }
    
    @Test
    @DisplayName("Proxy tracks successful operation count")
    void testSuccessfulOperationCount() {
        Document doc = DocumentFactory.createSecureDocument(
            "Doc",
            "Content",
            "Author",
            Role.VIEWER,
            admin
        );
        
        doc.getContent();
        doc.editContent("New");
        doc.getMetadata();
        
        DocumentProxy proxy = (DocumentProxy) doc;
        
        assertThat(proxy.getSuccessfulOperations()).isEqualTo(3);
    }
    
    @Test
    @DisplayName("Proxy tracks failed operation count")
    void testFailedOperationCount() {
        Document doc = DocumentFactory.createSecureDocument(
            "Doc",
            "Content",
            "Author",
            Role.VIEWER,
            viewer
        );
        
        doc.getContent();  // Success
        
        try { doc.editContent("New"); } catch (AccessDeniedException e) {}  // Fail
        try { doc.delete(); } catch (AccessDeniedException e) {}  // Fail
        try { doc.setRequiredRole(Role.ADMIN); } catch (AccessDeniedException e) {}  // Fail
        
        DocumentProxy proxy = (DocumentProxy) doc;
        
        assertThat(proxy.getSuccessfulOperations()).isEqualTo(1);
        assertThat(proxy.getFailedOperations()).isEqualTo(3);
    }
    
    @Test
    @DisplayName("AccessDeniedException contains detailed information")
    void testAccessDeniedExceptionDetails() {
        Document doc = DocumentFactory.createSecureDocument(
            "Doc",
            "Content",
            "Author",
            Role.VIEWER,
            viewer
        );
        
        AccessDeniedException exception = catchThrowableOfType(
            () -> doc.editContent("New"),
            AccessDeniedException.class
        );
        
        assertThat(exception.getUsername()).isEqualTo("alice");
        assertThat(exception.getOperation()).isEqualTo("editContent");
        assertThat(exception.getUserRole()).isEqualTo(Role.VIEWER);
        assertThat(exception.getRequired()).isEqualTo(Permission.EDIT);
    }
    
    @Test
    @DisplayName("Proxy is transparent - same interface as real document")
    void testProxyTransparency() {
        RealDocument realDoc = new RealDocument("Doc", "Content", "Author", Role.VIEWER);
        Document proxyDoc = DocumentFactory.createSecureDocument("Doc", "Content", "Author", Role.VIEWER, admin);
        
        // Both implement Document interface
        assertThat(realDoc).isInstanceOf(Document.class);
        assertThat(proxyDoc).isInstanceOf(Document.class);
        
        // Both have same basic operations
        assertThat(realDoc.getContent()).isEqualTo(proxyDoc.getContent());
    }
    
    @Test
    @DisplayName("Metadata access requires READ permission")
    void testMetadataAccessControl() {
        Document doc = DocumentFactory.createSecureDocument(
            "Doc",
            "Content",
            "Author",
            Role.VIEWER,
            viewer
        );
        
        // Viewer has READ permission
        String metadata = doc.getMetadata();
        
        assertThat(metadata).contains("Doc");
    }
    
    @Test
    @DisplayName("Audit entries have timestamps")
    void testAuditTimestamps() {
        Document doc = DocumentFactory.createSecureDocument(
            "Doc",
            "Content",
            "Author",
            Role.VIEWER,
            editor
        );
        
        doc.getContent();
        
        DocumentProxy proxy = (DocumentProxy) doc;
        List<AuditEntry> log = proxy.getAuditLog();
        
        assertThat(log.get(0).timestamp()).isNotNull();
    }
    
    @Test
    @DisplayName("Multiple operations create multiple audit entries")
    void testMultipleAuditEntries() {
        Document doc = DocumentFactory.createSecureDocument(
            "Doc",
            "Content",
            "Author",
            Role.VIEWER,
            admin
        );
        
        doc.getContent();
        doc.editContent("V1");
        doc.editContent("V2");
        doc.getMetadata();
        doc.setRequiredRole(Role.EDITOR);
        
        DocumentProxy proxy = (DocumentProxy) doc;
        
        assertThat(proxy.getAuditLog()).hasSize(5);
    }
    
    @Test
    @DisplayName("Different users get different proxies with different permissions")
    void testDifferentUserContexts() {
        RealDocument realDoc = new RealDocument("Doc", "Content", "Author", Role.VIEWER);
        
        DocumentProxy viewerProxy = new DocumentProxy(realDoc, viewer);
        DocumentProxy editorProxy = new DocumentProxy(realDoc, editor);
        
        // Viewer can read
        assertThatCode(() -> viewerProxy.getContent()).doesNotThrowAnyException();
        
        // Viewer cannot edit
        assertThatThrownBy(() -> viewerProxy.editContent("New"))
            .isInstanceOf(AccessDeniedException.class);
        
        // Editor can edit
        assertThatCode(() -> editorProxy.editContent("New")).doesNotThrowAnyException();
    }
    
    @Test
    @DisplayName("Proxy protects document state from unauthorized modifications")
    void testDocumentProtection() {
        Document doc = DocumentFactory.createSecureDocument(
            "Doc",
            "Original",
            "Author",
            Role.VIEWER,
            viewer
        );
        
        // Viewer reads original content
        assertThat(doc.getContent()).isEqualTo("Original");
        
        // Viewer attempts to edit (fails)
        try {
            doc.editContent("Hacked!");
        } catch (AccessDeniedException e) {
            // Expected
        }
        
        // Content should still be original (unauthorized edit rejected)
        assertThat(doc.getContent()).isEqualTo("Original");
        
        // Failed operation should be logged
        DocumentProxy proxy = (DocumentProxy) doc;
        assertThat(proxy.getFailedOperations()).isEqualTo(1);
    }
}
