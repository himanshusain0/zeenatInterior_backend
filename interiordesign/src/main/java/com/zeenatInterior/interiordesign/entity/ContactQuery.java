// ContactQuery.java Entity
package com.zeenatInterior.interiordesign.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "contact_queries")
public class ContactQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String message;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructors
    public ContactQuery() {
        this.createdAt = LocalDateTime.now();
    }

    public ContactQuery(String name, String email, String message) {
        this();
        this.name = name;
        this.email = email;
        this.message = message;
    }

    // Getters and Setters
    // ...
}