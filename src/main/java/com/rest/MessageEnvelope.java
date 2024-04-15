
package com.rest;

import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Map;

public class MessageEnvelope<T> {
    private String path;
    private HttpMethod method;
    private String contentType;
    private T payload;

    private String accept;
    private List<Map.Entry<String, String>> queryParams;

    // Private constructor to be used by the builder
    private MessageEnvelope(Builder<T> builder) {
        this.path = builder.path;
        this.method = builder.method;
        this.contentType = builder.contentType != null ? builder.contentType : MediaType.APPLICATION_JSON;
        this.accept = builder.accept;
        this.payload = builder.payload;
        this.queryParams = builder.queryParams;
    }

    public String getPath() {
        return path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getContentType() {
        return contentType;
    }

    public String getAccept() {
        return accept;
    }

    public T getPayload() {
        return payload;
    }

    public List<Map.Entry<String, String>> getQueryParams() {
        return queryParams;
    }

    // Builder class
    public static class Builder<T> {
        private String path;
        private HttpMethod method;
        private String contentType;
        private String accept;
        private T payload;
        private List<Map.Entry<String, String>> queryParams;

        // Constructor with mandatory fields
        public Builder(String path, HttpMethod method) {
            this.path = path;
            this.method = method;
        }

        public Builder<T> contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder<T> accept(String accept) {
            this.accept = accept;
            return this;
        }

        public Builder<T> payload(T payload) {
            this.payload = payload;
            return this;
        }

        public Builder<T> queryParams(List<Map.Entry<String, String>> queryParams) {
            this.queryParams = queryParams;
            return this;
        }

        public MessageEnvelope<T> build() {
            return new MessageEnvelope<>(this);
        }
    }
}
