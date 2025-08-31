package com.example.erw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
public class HomeController {
    @GetMapping("/api/info")
    public Map<String, Object> info() {
        return Map.of(
            "name", "ERW Site Scoring Service",
            "status", "UP",
            "time", Instant.now().toString(),
            "endpoints", new String[]{"/api/sites", "/api/sitescore", "/api/health"}
        );
    }
    
    @GetMapping("/api/health")
    public Map<String, Object> health() {
        return Map.of("status", "UP", "time", Instant.now().toString());
    }
}
