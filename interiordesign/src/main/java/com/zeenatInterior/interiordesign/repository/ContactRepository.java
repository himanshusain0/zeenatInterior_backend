package com.zeenatInterior.interiordesign.repository;

// repository/ContactRepository.java


import com.zeenatInterior.interiordesign.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("SELECT c FROM Contact c ORDER BY c.id DESC LIMIT 1")
    Optional<Contact> findTopByOrderByIdDesc();
}