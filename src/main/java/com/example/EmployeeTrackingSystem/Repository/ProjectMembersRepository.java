package com.example.EmployeeTrackingSystem.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.EmployeeTrackingSystem.model.projectMembers;

public interface ProjectMembersRepository extends JpaRepository<projectMembers, Long> {
}
