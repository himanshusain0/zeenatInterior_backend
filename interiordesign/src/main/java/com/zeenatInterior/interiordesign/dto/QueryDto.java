package com.zeenatInterior.interiordesign.dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class QueryDto {
    @NotBlank
    private String name;

    @Email @NotBlank
    private String email;

    private String phone;

    @NotBlank
    private String message;
}