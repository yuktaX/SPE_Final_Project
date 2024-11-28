package com.example.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api") // Base URL prefix for the endpoints in this controller
public class HealthController {

    @GetMapping("/health") // Endpoint to check server health
    public String healthCheck() {
        return "Backend is up and running!";
    }

    @GetMapping("/status") // Additional status endpoint (optional)
    public String statusCheck() {
        return "All systems operational.";
    }
}
