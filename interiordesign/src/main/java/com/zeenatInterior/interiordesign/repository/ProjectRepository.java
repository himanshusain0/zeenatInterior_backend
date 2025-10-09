package com.zeenatInterior.interiordesign.repository;

// repository/ProjectRepository.java


import com.zeenatInterior.interiordesign.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByStatusOrderByCreatedAtDesc(String status);
    List<Project> findAllByOrderByCreatedAtDesc();
}
