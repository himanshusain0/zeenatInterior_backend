package com.zeenatInterior.interiordesign.repository;
// repository/LogoRepository.java


import com.zeenatInterior.interiordesign.entity.Logo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LogoRepository extends JpaRepository<Logo, Long> {

    @Query("SELECT l FROM Logo l ORDER BY l.uploadedAt DESC LIMIT 1")
    Optional<Logo> findTopByOrderByUploadedAtDesc();

    @Modifying
    @Query("DELETE FROM Logo")
    void deleteAllLogos();
}