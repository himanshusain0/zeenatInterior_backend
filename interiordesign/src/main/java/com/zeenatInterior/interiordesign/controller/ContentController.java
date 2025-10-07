package com.zeenatInterior.interiordesign.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    @GetMapping("/home")
    public ResponseEntity<?> homeContent() {
        return ResponseEntity.ok(Map.of(
                "heroTitle", "Design your dream space",
                "heroSubtitle", "Custom interior design services tailored for you",
                "cta", "Get a free consultation"
        ));
    }
}