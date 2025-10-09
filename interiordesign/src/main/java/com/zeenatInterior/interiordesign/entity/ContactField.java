package com.zeenatInterior.interiordesign.entity;

import jakarta.persistence.*;

// Contact Field Entity
@Entity
@Table(name = "contact_fields")
public class ContactField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fieldLabel;

    @Column(nullable = false)
    private String fieldType = "text"; // text, email, tel, textarea

    @Column(nullable = false)
    private Boolean isRequired = false;

    private Integer displayOrder = 0;

    // Constructors
    public ContactField() {}

    public ContactField(String fieldLabel, String fieldType, Boolean isRequired) {
        this.fieldLabel = fieldLabel;
        this.fieldType = fieldType;
        this.isRequired = isRequired;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFieldLabel() { return fieldLabel; }
    public void setFieldLabel(String fieldLabel) { this.fieldLabel = fieldLabel; }

    public String getFieldType() { return fieldType; }
    public void setFieldType(String fieldType) { this.fieldType = fieldType; }

    public Boolean getIsRequired() { return isRequired; }
    public void setIsRequired(Boolean isRequired) { this.isRequired = isRequired; }

    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }
}
