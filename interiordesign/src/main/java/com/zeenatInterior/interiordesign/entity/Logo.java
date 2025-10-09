package com.zeenatInterior.interiordesign.entity;
// entity/Logo.java


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logos")
public class Logo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String logoUrl;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();

    // Constructors
    public Logo() {}

    public Logo(String logoUrl, String originalName) {
        this.logoUrl = logoUrl;
        this.originalName = originalName;
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