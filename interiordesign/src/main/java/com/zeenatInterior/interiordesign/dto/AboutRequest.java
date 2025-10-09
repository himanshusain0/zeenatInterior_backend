package com.zeenatInterior.interiordesign.dto;


public class AboutRequest {
    private String title;
    private String description;
    private String mission;
    private String vision;

    // Constructors, Getters and Setters
    public AboutRequest() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getMission() { return mission; }
    public void setMission(String mission) { this.mission = mission; }

    public String getVision() { return vision; }
    public void setVision(String vision) { this.vision = vision; }
}
