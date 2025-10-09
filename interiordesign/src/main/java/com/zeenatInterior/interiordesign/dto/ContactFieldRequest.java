package com.zeenatInterior.interiordesign.dto;

public class ContactFieldRequest {
    private String fieldLabel;
    private String fieldType;
    private Boolean isRequired;

    // Constructors, Getters and Setters
    public ContactFieldRequest() {}

    public String getFieldLabel() { return fieldLabel; }
    public void setFieldLabel(String fieldLabel) { this.fieldLabel = fieldLabel; }

    public String getFieldType() { return fieldType; }
    public void setFieldType(String fieldType) { this.fieldType = fieldType; }

    public Boolean getIsRequired() { return isRequired; }
    public void setIsRequired(Boolean isRequired) { this.isRequired = isRequired; }
}