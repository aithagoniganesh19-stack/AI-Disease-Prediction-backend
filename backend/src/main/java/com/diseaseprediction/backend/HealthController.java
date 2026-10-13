package com.diseaseprediction.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public Map<String, String> checkHealth() {
        return Map.of(
                "status", "success",
                "message", "AI Disease Prediction Backend is running!"
        );
    }
}