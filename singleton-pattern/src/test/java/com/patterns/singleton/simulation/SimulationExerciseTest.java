package com.patterns.singleton.simulation;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Exercise 2: Connection Pool Singleton
 */
class SimulationExerciseTest {

    @Test
    void getInstance_returnsSameInstance() {
        ConnectionPool pool1 = ConnectionPool.getInstance();
        ConnectionPool pool2 = ConnectionPool.getInstance();

        assertThat(pool1).isSameAs(pool2);
    }

    @Test
    void getInstance_isThreadSafe() throws InterruptedException {
        int threadCount = 50;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        List<ConnectionPool> instances = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    instances.add(ConnectionPool.getInstance());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();

        // All threads should get the same instance
        ConnectionPool first = instances.get(0);
        assertThat(instances).allMatch(pool -> pool == first);
    }

    @Test
    void pool_hasCorrectSize() {
        ConnectionPool pool = ConnectionPool.getInstance();

        assertThat(pool.getPoolSize()).isEqualTo(5);
    }

    @Test
    void getConnection_returnsValidConnection() throws InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();

        Connection conn = pool.getConnection();

        assertThat(conn).isNotNull();
        assertThat(conn.getId()).isNotNull();
        assertThat(conn.isValid()).isTrue();

        pool.releaseConnection(conn);  // Clean up
    }

    @Test
    void getConnection_decreasesAvailableCount() throws InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        int initialAvailable = pool.getAvailableCount();

        Connection conn = pool.getConnection();

        assertThat(pool.getAvailableCount()).isEqualTo(initialAvailable - 1);
        assertThat(pool.getInUseCount()).isEqualTo(1);

        pool.releaseConnection(conn);  // Clean up
    }

    @Test
    void releaseConnection_increasesAvailableCount() throws InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();

        Connection conn = pool.getConnection();
        int availableAfterGet = pool.getAvailableCount();

        pool.releaseConnection(conn);

        assertThat(pool.getAvailableCount()).isEqualTo(availableAfterGet + 1);
    }

    @Test
    void getConnection_withTimeout_returnsNull_whenPoolExhausted() throws InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        List<Connection> connections = new ArrayList<>();

        // Exhaust the pool
        for (int i = 0; i < pool.getPoolSize(); i++) {
            connections.add(pool.getConnection());
        }

        // Try to get one more with short timeout
        Connection conn = pool.getConnection(100);  // 100ms timeout

        assertThat(conn).isNull();

        // Clean up
        connections.forEach(pool::releaseConnection);
    }

    @Test
    void getConnection_blocks_untilConnectionReleased() throws InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        List<Connection> connections = new ArrayList<>();

        // Exhaust the pool
        for (int i = 0; i < pool.getPoolSize(); i++) {
            connections.add(pool.getConnection());
        }

        assertThat(pool.getAvailableCount()).isEqualTo(0);

        // Thread that will release a connection after delay
        Thread releaser = new Thread(() -> {
            try {
                Thread.sleep(200);  // Delay before releasing
                pool.releaseConnection(connections.get(0));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        releaser.start();

        // This should block until releaser thread returns a connection
        long startTime = System.currentTimeMillis();
        Connection conn = pool.getConnection();
        long duration = System.currentTimeMillis() - startTime;

        assertThat(conn).isNotNull();
        assertThat(duration).isGreaterThan(150);  // Blocked for ~200ms

        // Clean up
        pool.releaseConnection(conn);
        connections.subList(1, connections.size()).forEach(pool::releaseConnection);
    }

    @Test
    void concurrentAccess_handlesMultipleThreads() throws InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        int threadCount = 20;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    Connection conn = pool.getConnection();
                    assertThat(conn).isNotNull();
                    Thread.sleep(50);  // Simulate work
                    pool.releaseConnection(conn);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(10, TimeUnit.SECONDS);
        executor.shutdown();

        // After all threads complete, all connections should be back in pool
        assertThat(pool.getAvailableCount()).isEqualTo(pool.getPoolSize());
    }

    @Test
    void poolMetrics_trackCorrectly() throws InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();

        assertThat(pool.getAvailableCount()).isEqualTo(5);
        assertThat(pool.getInUseCount()).isEqualTo(0);

        Connection conn1 = pool.getConnection();
        assertThat(pool.getAvailableCount()).isEqualTo(4);
        assertThat(pool.getInUseCount()).isEqualTo(1);

        Connection conn2 = pool.getConnection();
        assertThat(pool.getAvailableCount()).isEqualTo(3);
        assertThat(pool.getInUseCount()).isEqualTo(2);

        pool.releaseConnection(conn1);
        assertThat(pool.getAvailableCount()).isEqualTo(4);
        assertThat(pool.getInUseCount()).isEqualTo(1);

        pool.releaseConnection(conn2);
        assertThat(pool.getAvailableCount()).isEqualTo(5);
        assertThat(pool.getInUseCount()).isEqualTo(0);
    }

    @Test
    void releaseConnection_ignoresInvalidConnection() throws InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();

        Connection conn = pool.getConnection();
        conn.close();  // Invalidate the connection

        int availableBefore = pool.getAvailableCount();
        pool.releaseConnection(conn);
        int availableAfter = pool.getAvailableCount();

        // Pool should not accept invalid connection
        assertThat(availableAfter).isEqualTo(availableBefore);
    }

    @Test
    void connections_haveUniqueIds() throws InterruptedException {
        ConnectionPool pool = ConnectionPool.getInstance();
        List<Connection> connections = new ArrayList<>();

        for (int i = 0; i < pool.getPoolSize(); i++) {
            connections.add(pool.getConnection());
        }

        // Extract IDs
        List<String> ids = connections.stream()
                .map(Connection::getId)
                .toList();

        // All IDs should be unique
        assertThat(ids).doesNotHaveDuplicates();

        // Clean up
        connections.forEach(pool::releaseConnection);
    }
}
