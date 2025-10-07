package com.zeenatInterior.interiordesign.entity;




import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "user_images")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String filename;

    @Column(nullable=false)
    private String url; // served via /uploads/...

    private Long size;

    private String contentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable=false)
    private User user;

    @Column(updatable = false)
    private Instant uploadedAt;

    @PrePersist
    public void prePersist() {
        this.uploadedAt = Instant.now();
    }
}