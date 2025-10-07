package com.zeenatInterior.interiordesign.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ServiceDto {
    @NotBlank
    private String title;
    private String description;
    @NotNull @Positive
    private Double price;
    private String thumbnailUrl;
    private Boolean active = true;
}
