package com.example.EmployeeTrackingSystem.Repository;

import com.example.EmployeeTrackingSystem.model.employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;

public interface EmployeeRepository extends JpaRepository<employee, Long> {
    Page<employee> findByNameContainingIgnoreCaseOrRoleContainingIgnoreCase(String name, String role, Pageable pageable);

    employee findByName(String name);
    employee findByEmail(String email);

    // 🔥 LOGIN SUPPORT (email OR username)
    employee findByEmailOrName(String email, String name);
}
