package com.example.urlshortener.model;

import java.time.LocalDateTime;

public class UrlMapping {
    private String alias;
    private String longUrl;
    private LocalDateTime createdAt;
    private long ttlSeconds;

    public UrlMapping(String alias, String longUrl, LocalDateTime createdAt, long ttlSeconds) {
        this.alias = alias;
        this.longUrl = longUrl;
        this.createdAt = createdAt;
        this.ttlSeconds = ttlSeconds;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public long getTtlSeconds() {
        return ttlSeconds;
    }

    public void setTtlSeconds(long ttlSeconds) {
        this.ttlSeconds = ttlSeconds;
    }

    public boolean hasExpired() {
        return LocalDateTime.now().isAfter(createdAt.plusSeconds(ttlSeconds));
    }
}
