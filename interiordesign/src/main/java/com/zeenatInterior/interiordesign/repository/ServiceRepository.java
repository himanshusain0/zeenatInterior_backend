package com.zeenatInterior.interiordesign.repository;


import com.zeenatInterior.interiordesign.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}