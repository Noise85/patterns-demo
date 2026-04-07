package com.patterns.singleton.isolation;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Exercise 1: Configuration Manager Singleton
 */
class IsolationExerciseTest {

    @Test
    void getInstance_returnsSameInstance() {
        ConfigurationManager instance1 = ConfigurationManager.getInstance();
        ConfigurationManager instance2 = ConfigurationManager.getInstance();

        assertThat(instance1).isSameAs(instance2);
    }

    @Test
    void getInstance_isThreadSafe() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        List<ConfigurationManager> instances = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    instances.add(ConfigurationManager.getInstance());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();

        // All threads should get the same instance
        ConfigurationManager first = instances.get(0);
        assertThat(instances).allMatch(instance -> instance == first);
    }

    @Test
    void getProperty_retrievesDefaultConfiguration() {
        ConfigurationManager config = ConfigurationManager.getInstance();

        assertThat(config.getProperty("app.name")).isEqualTo("Design Patterns Demo");
        assertThat(config.getProperty("app.version")).isEqualTo("1.0.0");
        assertThat(config.getProperty("database.url")).contains("jdbc");
    }

    @Test
    void getProperty_returnsNull_forNonExistentKey() {
        ConfigurationManager config = ConfigurationManager.getInstance();

        assertThat(config.getProperty("nonexistent.key")).isNull();
    }

    @Test
    void getProperty_withDefault_returnsDefault_whenKeyNotFound() {
        ConfigurationManager config = ConfigurationManager.getInstance();

        String value = config.getProperty("missing.key", "default-value");

        assertThat(value).isEqualTo("default-value");
    }

    @Test
    void getProperty_withDefault_returnsValue_whenKeyExists() {
        ConfigurationManager config = ConfigurationManager.getInstance();

        String value = config.getProperty("app.name", "fallback");

        assertThat(value).isEqualTo("Design Patterns Demo");
    }

    @Test
    void setProperty_addsNewProperty() {
        ConfigurationManager config = ConfigurationManager.getInstance();

        config.setProperty("custom.key", "custom-value");

        assertThat(config.getProperty("custom.key")).isEqualTo("custom-value");
    }

    @Test
    void setProperty_updatesExistingProperty() {
        ConfigurationManager config = ConfigurationManager.getInstance();

        config.setProperty("app.version", "2.0.0");

        assertThat(config.getProperty("app.version")).isEqualTo("2.0.0");
    }

    @Test
    void getAllProperties_returnsAllConfiguration() {
        ConfigurationManager config = ConfigurationManager.getInstance();

        Map<String, String> allProps = config.getAllProperties();

        assertThat(allProps).isNotEmpty()
                .containsKeys("app.name", "app.version", "database.url");
    }

    @Test
    void getAllProperties_returnsDefensiveCopy() {
        ConfigurationManager config = ConfigurationManager.getInstance();

        Map<String, String> props1 = config.getAllProperties();
        Map<String, String> props2 = config.getAllProperties();

        // Should be equal but not the same instance
        assertThat(props1).isEqualTo(props2);
        assertThat(props1).isNotSameAs(props2);
    }

    @Test
    void multipleThreads_shareState() throws InterruptedException {
        ConfigurationManager config = ConfigurationManager.getInstance();
        config.setProperty("shared.key", "initial");

        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            final int index = i;
            executor.submit(() -> {
                try {
                    ConfigurationManager threadConfig = ConfigurationManager.getInstance();
                    if (index == 5) {
                        threadConfig.setProperty("shared.key", "updated");
                    }
                    Thread.sleep(10);  // Small delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();

        // All threads share the same state
        assertThat(config.getProperty("shared.key")).isEqualTo("updated");
    }
}
