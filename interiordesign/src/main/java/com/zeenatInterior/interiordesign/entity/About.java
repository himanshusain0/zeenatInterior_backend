package com.zeenatInterior.interiordesign.entity;

// entity/About.java

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "about_content")
public class About {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title = "About Us";

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String mission;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String vision;

    private String image;

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Constructors
    public About() {}

    public About(String title, String description, String mission, String vision) {
        this.title = title;
        this.description = description;
        this.mission = mission;
        this.vision = vision;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getMission() { return mission; }
    public void setMission(String mission) { this.mission = mission; }

    public String getVision() { return vision; }
    public void setVision(String vision) { this.vision = vision; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}