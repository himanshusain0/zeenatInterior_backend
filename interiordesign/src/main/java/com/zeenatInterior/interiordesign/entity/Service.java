package com.zeenatInterior.interiordesign.entity;
//package com.yourcompany.interiordesign.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "services")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Service {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable=false)
    private Double price;

    private String thumbnailUrl;

    @Column(nullable=false)
    private Boolean active = true;
}