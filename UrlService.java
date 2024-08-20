package com.example.urlshortener.service;

import com.example.urlshortener.model.UrlMapping;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UrlService {

    private Map<String, UrlMapping> urlStore = new HashMap<>();

    public UrlMapping createShortUrl(String longUrl, String customAlias, long ttlSeconds) {
        String alias = (customAlias != null && !customAlias.isEmpty()) ? customAlias : generateAlias(longUrl);
        UrlMapping urlMapping = new UrlMapping(alias, longUrl, LocalDateTime.now(), ttlSeconds);
        urlStore.put(alias, urlMapping);
        return urlMapping;
    }

    public UrlMapping getUrlMapping(String alias) {
        return urlStore.get(alias);
    }

    public UrlMapping updateUrlMapping(String alias, String newAlias, long newTtlSeconds) {
        UrlMapping existingMapping = urlStore.get(alias);
        if (existingMapping != null) {
            existingMapping.setAlias(newAlias != null ? newAlias : existingMapping.getAlias());
            existingMapping.setTtlSeconds(newTtlSeconds > 0 ? newTtlSeconds : existingMapping.getTtlSeconds());
            urlStore.put(existingMapping.getAlias(), existingMapping);
        }
        return existingMapping;
    }

    public void deleteUrlMapping(String alias) {
        urlStore.remove(alias);
    }

    private String generateAlias(String longUrl) {
        return Integer.toHexString(longUrl.hashCode());
    }
}
