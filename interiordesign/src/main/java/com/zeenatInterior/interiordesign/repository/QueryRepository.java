package com.zeenatInterior.interiordesign.repository;


//package com.zeenatInterior.interiordesign.repository;

import com.zeenatInterior.interiordesign.entity.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryRepository extends JpaRepository<Query, Long> {
}