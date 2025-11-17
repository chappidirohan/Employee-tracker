package com.example.EmployeeTrackingSystem.Repository;

import com.example.EmployeeTrackingSystem.model.projects;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<projects, Long> {

    // Correct method based on projectMembers.name
    List<projects> findByMembers_Name(String name);
}
