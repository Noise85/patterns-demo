package com.patterns.builder.isolation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for Exercise 1: HTTP Request Builder
 */
class IsolationExerciseTest {

    @Test
    void builder_createsRequestWithRequiredFields() {
        HttpRequest request = new HttpRequest.Builder()
                .method("GET")
                .url("https://api.example.com/users")
                .build();

        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(request.getUrl()).isEqualTo("https://api.example.com/users");
    }

    @Test
    void builder_supportsMethodChaining() {
        HttpRequest request = new HttpRequest.Builder()
                .method("POST")
                .url("https://api.example.com/users")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer token123")
                .queryParam("sortBy", "name")
                .body("{\"name\":\"Alice\"}")
                .timeout(5000)
                .build();

        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getUrl()).isEqualTo("https://api.example.com/users");
        assertThat(request.getHeaders()).hasSize(2)
                .containsEntry("Content-Type", "application/json")
                .containsEntry("Authorization", "Bearer token123");
        assertThat(request.getQueryParams()).containsEntry("sortBy", "name");
        assertThat(request.getBody()).isEqualTo("{\"name\":\"Alice\"}");
        assertThat(request.getTimeoutMs()).isEqualTo(5000);
    }

    @Test
    void builder_usesDefaultValues() {
        HttpRequest request = new HttpRequest.Builder()
                .url("http://localhost:8080")
                .build();

        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(request.getTimeoutMs()).isEqualTo(30000);
        assertThat(request.getHeaders()).isEmpty();
        assertThat(request.getQueryParams()).isEmpty();
        assertThat(request.getBody()).isNull();
    }

    @Test
    void builder_createsImmutableRequest() {
        HttpRequest.Builder builder = new HttpRequest.Builder()
                .url("https://api.example.com/test")
                .header("X-Custom", "value1");

        HttpRequest request1 = builder.build();
        
        // Modify builder after first build
        builder.header("X-Custom", "value2");
        HttpRequest request2 = builder.build();

        // First request should be unaffected
        assertThat(request1.getHeaders()).hasSize(1)
                .containsEntry("X-Custom", "value1");
        assertThat(request2.getHeaders()).hasSize(1)
                .containsEntry("X-Custom", "value2");
    }

    @Test
    void builder_multipleHeaders() {
        HttpRequest request = new HttpRequest.Builder()
                .url("https://api.example.com")
                .header("Accept", "application/json")
                .header("User-Agent", "TestClient/1.0")
                .header("X-Request-ID", "abc123")
                .build();

        assertThat(request.getHeaders()).hasSize(3)
                .containsEntry("Accept", "application/json")
                .containsEntry("User-Agent", "TestClient/1.0")
                .containsEntry("X-Request-ID", "abc123");
    }

    @Test
    void builder_multipleQueryParams() {
        HttpRequest request = new HttpRequest.Builder()
                .url("https://api.example.com/search")
                .queryParam("q", "design patterns")
                .queryParam("page", "1")
                .queryParam("limit", "10")
                .build();

        assertThat(request.getQueryParams()).hasSize(3)
                .containsEntry("q", "design patterns")
                .containsEntry("page", "1")
                .containsEntry("limit", "10");
    }

    @Test
    void build_throwsException_whenUrlIsMissing() {
        HttpRequest.Builder builder = new HttpRequest.Builder()
                .method("GET");

        assertThatThrownBy(builder::build)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("URL");
    }

    @Test
    void build_throwsException_whenUrlIsInvalid() {
        HttpRequest.Builder builder = new HttpRequest.Builder()
                .url("not-a-valid-url");

        assertThatThrownBy(builder::build)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("http");
    }

    @Test
    void builder_acceptsHttpsUrl() {
        HttpRequest request = new HttpRequest.Builder()
                .url("https://secure.example.com")
                .build();

        assertThat(request.getUrl()).isEqualTo("https://secure.example.com");
    }

    @Test
    void builder_acceptsHttpUrl() {
        HttpRequest request = new HttpRequest.Builder()
                .url("http://localhost:3000")
                .build();

        assertThat(request.getUrl()).isEqualTo("http://localhost:3000");
    }

    @Test
    void builder_canBeReused() {
        HttpRequest.Builder builder = new HttpRequest.Builder()
                .method("GET")
                .header("Authorization", "Bearer token");

        HttpRequest request1 = builder.url("https://api.example.com/users").build();
        HttpRequest request2 = builder.url("https://api.example.com/posts").build();

        assertThat(request1.getUrl()).isEqualTo("https://api.example.com/users");
        assertThat(request2.getUrl()).isEqualTo("https://api.example.com/posts");
        // Both share the same header from builder
        assertThat(request1.getHeaders()).containsEntry("Authorization", "Bearer token");
        assertThat(request2.getHeaders()).containsEntry("Authorization", "Bearer token");
    }
}
