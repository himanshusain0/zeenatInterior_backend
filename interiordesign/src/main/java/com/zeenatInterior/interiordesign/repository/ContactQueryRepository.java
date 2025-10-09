package com.zeenatInterior.interiordesign.repository;

import com.zeenatInterior.interiordesign.entity.ContactQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContactQueryRepository extends JpaRepository<ContactQuery, Long> {
    List<ContactQuery> findAllByOrderByCreatedAtDesc();
}