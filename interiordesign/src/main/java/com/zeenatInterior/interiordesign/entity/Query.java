package com.zeenatInterior.interiordesign.entity;

//package com.yourcompany.interiordesign.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "queries")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Query {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String email;

    private String phone;

    @Column(nullable=false, length = 4000)
    private String message;

    @Column(nullable=false)
    private String status = "NEW";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // optional

    @Column(updatable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }
}