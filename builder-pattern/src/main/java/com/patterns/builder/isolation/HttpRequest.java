package com.patterns.builder.isolation;

import java.util.HashMap;
import java.util.Map;

/**
 * Immutable HTTP request constructed using the Builder pattern.
 */
public final class HttpRequest {
    private final String method;
    private final String url;
    private final Map<String, String> headers;
    private final Map<String, String> queryParams;
    private final String body;
    private final int timeoutMs;

    private HttpRequest(Builder builder) {
        this.method = builder.method;
        this.url = builder.url;
        this.headers = Map.copyOf(builder.headers);
        this.queryParams = Map.copyOf(builder.queryParams);
        this.body = builder.body;
        this.timeoutMs = builder.timeoutMs;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public String getBody() {
        return body;
    }

    public int getTimeoutMs() {
        return timeoutMs;
    }

    /**
     * Builder for constructing HttpRequest objects.
     */
    public static class Builder {
        private String method = "GET";
        private String url;
        private Map<String, String> headers = new HashMap<>();
        private Map<String, String> queryParams = new HashMap<>();
        private String body;
        private int timeoutMs = 30000;

        public Builder method(String method) {
            // TODO: Set the HTTP method and return this for chaining
            throw new UnsupportedOperationException("TODO: Implement method()");
        }

        public Builder url(String url) {
            // TODO: Set the URL and return this for chaining
            throw new UnsupportedOperationException("TODO: Implement url()");
        }

        public Builder header(String name, String value) {
            // TODO: Add a header and return this for chaining
            throw new UnsupportedOperationException("TODO: Implement header()");
        }

        public Builder queryParam(String name, String value) {
            // TODO: Add a query parameter and return this for chaining
            throw new UnsupportedOperationException("TODO: Implement queryParam()");
        }

        public Builder body(String body) {
            // TODO: Set the request body and return this for chaining
            throw new UnsupportedOperationException("TODO: Implement body()");
        }

        public Builder timeout(int timeoutMs) {
            // TODO: Set the timeout and return this for chaining
            throw new UnsupportedOperationException("TODO: Implement timeout()");
        }

        public HttpRequest build() {
            // TODO: Validate required fields (url must be present and start with http:// or https://)
            // TODO: Create and return immutable HttpRequest
            throw new UnsupportedOperationException("TODO: Implement build() with validation");
        }
    }
}
