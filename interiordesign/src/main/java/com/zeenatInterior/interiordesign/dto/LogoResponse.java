package com.zeenatInterior.interiordesign.dto;


import java.time.LocalDateTime;

public class LogoResponse {
    private Long id;
    private String logoUrl;
    private String originalName;
    private LocalDateTime uploadedAt;

    // Constructors, Getters and Setters
    public LogoResponse() {}

    public LogoResponse(Long id, String logoUrl, String originalName, LocalDateTime uploadedAt) {
        this.id = id;
        this.logoUrl = logoUrl;
        this.originalName = originalName;
        this.uploadedAt = uploadedAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public String getOriginalName() { return originalName; }
    public void setOriginalName(String originalName) { this.originalName = originalName; }

    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
}