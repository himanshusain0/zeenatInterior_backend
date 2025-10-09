package com.zeenatInterior.interiordesign.repository;

// repository/AboutRepository.java

import com.zeenatInterior.interiordesign.entity.About;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AboutRepository extends JpaRepository<About, Long> {

    @Query("SELECT a FROM About a ORDER BY a.id DESC LIMIT 1")
    Optional<About> findTopByOrderByIdDesc();
}
