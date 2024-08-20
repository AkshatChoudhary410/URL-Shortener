package com.example.urlshortener.controller;

import com.example.urlshortener.model.UrlMapping;
import com.example.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/url")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<UrlMapping> shortenUrl(@RequestParam String longUrl,
                                                 @RequestParam(required = false) String customAlias,
                                                 @RequestParam(required = false, defaultValue = "120") long ttlSeconds) {
        UrlMapping urlMapping = urlService.createShortUrl(longUrl, customAlias, ttlSeconds);
        return new ResponseEntity<>(urlMapping, HttpStatus.CREATED);
    }

    @GetMapping("/{alias}")
    public ResponseEntity<Void> redirect(@PathVariable String alias) {
        UrlMapping urlMapping = urlService.getUrlMapping(alias);
        if (urlMapping != null && !urlMapping.hasExpired()) {
            return ResponseEntity.status(HttpStatus.FOUND).location(java.net.URI.create(urlMapping.getLongUrl())).build();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/analytics/{alias}")
    public ResponseEntity<UrlMapping> getAnalytics(@PathVariable String alias) {
        UrlMapping urlMapping = urlService.getUrlMapping(alias);
        if (urlMapping != null) {
            return new ResponseEntity<>(urlMapping, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{alias}")
    public ResponseEntity<UrlMapping> updateUrl(@PathVariable String alias,
                                                @RequestParam(required = false) String newAlias,
                                                @RequestParam(required = false, defaultValue = "120") long newTtlSeconds) {
        UrlMapping updatedMapping = urlService.updateUrlMapping(alias, newAlias, newTtlSeconds);
        if (updatedMapping != null) {
            return new ResponseEntity<>(updatedMapping, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{alias}")
    public ResponseEntity<Void> deleteUrl(@PathVariable String alias) {
        urlService.deleteUrlMapping(alias);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
