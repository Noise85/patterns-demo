package com.patterns.proxy.isolation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Proxy pattern isolation exercise (virtual proxy for database connections).
 */
class IsolationExerciseTest {
    
    @Test
    @DisplayName("RealDatabaseConnection executes queries")
    void testRealConnectionQuery() {
        RealDatabaseConnection conn = new RealDatabaseConnection(
            "jdbc:postgresql://localhost/test", 
            "user", 
            "pass"
        );
        
        String result = conn.executeQuery("SELECT * FROM users");
        
        assertThat(result).contains("Result for").contains("SELECT * FROM users");
        assertThat(conn.isConnected()).isTrue();
    }
    
    @Test
    @DisplayName("RealDatabaseConnection executes batch queries")
    void testRealConnectionBatch() {
        RealDatabaseConnection conn = new RealDatabaseConnection(
            "jdbc:postgresql://localhost/test",
            "user",
            "pass"
        );
        
        List<String> queries = List.of("SELECT * FROM users", "SELECT * FROM orders");
        List<String> results = conn.executeBatch(queries);
        
        assertThat(results).hasSize(2);
        assertThat(results.get(0)).contains("SELECT * FROM users");
        assertThat(results.get(1)).contains("SELECT * FROM orders");
    }
    
    @Test
    @DisplayName("RealDatabaseConnection can be closed")
    void testRealConnectionClose() {
        RealDatabaseConnection conn = new RealDatabaseConnection(
            "jdbc:postgresql://localhost/test",
            "user",
            "pass"
        );
        
        assertThat(conn.isConnected()).isTrue();
        
        conn.close();
        
        assertThat(conn.isConnected()).isFalse();
    }
    
    @Test
    @DisplayName("Proxy constructor is lightweight - does not create real connection")
    void testProxyLazyInitialization() {
        DatabaseConnectionProxy proxy = new DatabaseConnectionProxy(
            "jdbc:postgresql://localhost/test",
            "user",
            "pass"
        );
        
        // Real connection should NOT be created yet
        assertThat(proxy.isRealConnectionCreated()).isFalse();
        assertThat(proxy.isConnected()).isFalse();
        assertThat(proxy.getCreationTimestamp()).isEqualTo(0);
    }
    
    @Test
    @DisplayName("Proxy creates real connection on first query")
    void testProxyCreatesConnectionOnFirstUse() {
        DatabaseConnectionProxy proxy = new DatabaseConnectionProxy(
            "jdbc:postgresql://localhost/test",
            "user",
            "pass"
        );
        
        assertThat(proxy.isRealConnectionCreated()).isFalse();
        
        // First query triggers creation
        String result = proxy.executeQuery("SELECT * FROM users");
        
        assertThat(proxy.isRealConnectionCreated()).isTrue();
        assertThat(proxy.isConnected()).isTrue();
        assertThat(proxy.getCreationTimestamp()).isGreaterThan(0);
        assertThat(result).contains("Result for");
    }
    
    @Test
    @DisplayName("Proxy reuses same real connection for subsequent queries")
    void testProxyReusesSameConnection() {
        DatabaseConnectionProxy proxy = new DatabaseConnectionProxy(
            "jdbc:postgresql://localhost/test",
            "user",
            "pass"
        );
        
        proxy.executeQuery("SELECT * FROM users");
        long firstCreationTime = proxy.getCreationTimestamp();
        
        // Subsequent queries should reuse connection
        proxy.executeQuery("SELECT * FROM orders");
        proxy.executeQuery("SELECT * FROM products");
        
        // Creation timestamp should not change (same connection)
        assertThat(proxy.getCreationTimestamp()).isEqualTo(firstCreationTime);
        assertThat(proxy.getOperationCount()).isEqualTo(3);
    }
    
    @Test
    @DisplayName("Proxy tracks operation count")
    void testProxyOperationCount() {
        DatabaseConnectionProxy proxy = new DatabaseConnectionProxy(
            "jdbc:postgresql://localhost/test",
            "user",
            "pass"
        );
        
        assertThat(proxy.getOperationCount()).isEqualTo(0);
        
        proxy.executeQuery("SELECT 1");
        assertThat(proxy.getOperationCount()).isEqualTo(1);
        
        proxy.executeQuery("SELECT 2");
        assertThat(proxy.getOperationCount()).isEqualTo(2);
        
        proxy.executeQuery("SELECT 3");
        assertThat(proxy.getOperationCount()).isEqualTo(3);
    }
    
    @Test
    @DisplayName("Proxy batch operations trigger lazy initialization")
    void testProxyBatchLazyInit() {
        DatabaseConnectionProxy proxy = new DatabaseConnectionProxy(
            "jdbc:postgresql://localhost/test",
            "user",
            "pass"
        );
        
        assertThat(proxy.isRealConnectionCreated()).isFalse();
        
        List<String> queries = List.of("SELECT 1", "SELECT 2");
        List<String> results = proxy.executeBatch(queries);
        
        assertThat(proxy.isRealConnectionCreated()).isTrue();
        assertThat(results).hasSize(2);
    }
    
    @Test
    @DisplayName("Proxy batch operations count correctly")
    void testProxyBatchOperationCount() {
        DatabaseConnectionProxy proxy = new DatabaseConnectionProxy(
            "jdbc:postgresql://localhost/test",
            "user",
            "pass"
        );
        
        List<String> queries = List.of("SELECT 1", "SELECT 2", "SELECT 3");
        proxy.executeBatch(queries);
        
        // Should count each query in the batch
        assertThat(proxy.getOperationCount()).isEqualTo(3);
    }
    
    @Test
    @DisplayName("Proxy delegates properly to real connection")
    void testProxyDelegation() {
        DatabaseConnectionProxy proxy = new DatabaseConnectionProxy(
            "jdbc:postgresql://localhost/test",
            "user",
            "pass"
        );
        
        String result1 = proxy.executeQuery("SELECT * FROM users");
        String result2 = proxy.executeQuery("SELECT * FROM orders");
        
        assertThat(result1).contains("SELECT * FROM users");
        assertThat(result2).contains("SELECT * FROM orders");
    }
    
    @Test
    @DisplayName("Proxy and real connection are interchangeable")
    void testSubstitutability() {
        DatabaseConnection real = new RealDatabaseConnection(
            "jdbc:postgresql://localhost/test",
            "user",
            "pass"
        );
        
        DatabaseConnection proxy = new DatabaseConnectionProxy(
            "jdbc:postgresql://localhost/test",
            "user",
            "pass"
        );
        
        // Both implement same interface
        String realResult = real.executeQuery("SELECT 1");
        String proxyResult = proxy.executeQuery("SELECT 1");
        
        assertThat(realResult).contains("Result for");
        assertThat(proxyResult).contains("Result for");
    }
    
    @Test
    @DisplayName("Proxy close delegates to real connection if created")
    void testProxyCloseDelegation() {
        DatabaseConnectionProxy proxy = new DatabaseConnectionProxy(
            "jdbc:postgresql://localhost/test",
            "user",
            "pass"
        );
        
        proxy.executeQuery("SELECT 1");
        assertThat(proxy.isConnected()).isTrue();
        
        proxy.close();
        
        assertThat(proxy.isConnected()).isFalse();
    }
    
    @Test
    @DisplayName("Proxy close does nothing if real connection not created")
    void testProxyCloseWhenNotCreated() {
        DatabaseConnectionProxy proxy = new DatabaseConnectionProxy(
            "jdbc:postgresql://localhost/test",
            "user",
            "pass"
        );
        
        // Should not throw exception
        assertThatCode(() -> proxy.close()).doesNotThrowAnyException();
        assertThat(proxy.isRealConnectionCreated()).isFalse();
    }
    
    @Test
    @DisplayName("Multiple proxies can coexist independently")
    void testMultipleProxies() {
        DatabaseConnectionProxy proxy1 = new DatabaseConnectionProxy(
            "jdbc:postgresql://localhost/db1",
            "user1",
            "pass1"
        );
        
        DatabaseConnectionProxy proxy2 = new DatabaseConnectionProxy(
            "jdbc:postgresql://localhost/db2",
            "user2",
            "pass2"
        );
        
        proxy1.executeQuery("SELECT 1");
        
        assertThat(proxy1.isRealConnectionCreated()).isTrue();
        assertThat(proxy2.isRealConnectionCreated()).isFalse();
        
        proxy2.executeQuery("SELECT 2");
        
        assertThat(proxy1.getOperationCount()).isEqualTo(1);
        assertThat(proxy2.getOperationCount()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("Proxy demonstrates lazy loading benefit")
    void testLazyLoadingBenefit() {
        // Scenario: Create 10 connections, only use 3
        
        DatabaseConnectionProxy[] proxies = new DatabaseConnectionProxy[10];
        for (int i = 0; i < 10; i++) {
            proxies[i] = new DatabaseConnectionProxy(
                "jdbc:postgresql://localhost/db" + i,
                "user",
                "pass"
            );
        }
        
        // All proxies created, but no real connections yet
        for (DatabaseConnectionProxy proxy : proxies) {
            assertThat(proxy.isRealConnectionCreated()).isFalse();
        }
        
        // Use only 3 connections
        proxies[0].executeQuery("SELECT 1");
        proxies[5].executeQuery("SELECT 2");
        proxies[9].executeQuery("SELECT 3");
        
        // Count how many real connections were created
        long createdCount = 0;
        for (DatabaseConnectionProxy proxy : proxies) {
            if (proxy.isRealConnectionCreated()) {
                createdCount++;
            }
        }
        
        // Only 3 real connections created, not 10!
        assertThat(createdCount).isEqualTo(3);
    }
}
