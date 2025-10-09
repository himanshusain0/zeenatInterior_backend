package com.zeenatInterior.interiordesign.dto;

import java.time.LocalDate;

public class ProjectRequest {
    private String title;
    private String description;
    private String category;
    private String client;
    private LocalDate projectDate;

    // Constructors, Getters and Setters
    public ProjectRequest() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getClient() { return client; }
    public void setClient(String client) { this.client = client; }

    public LocalDate getProjectDate() { return projectDate; }
    public void setProjectDate(LocalDate projectDate) { this.projectDate = projectDate; }
}