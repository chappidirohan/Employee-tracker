package com.example.EmployeeTrackingSystem.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import com.example.EmployeeTrackingSystem.model.Employee;

// <EntityClassName, TypeOfPrimaryKey>
public interface Employeerepository extends JpaRepository<Employee, Long> {

    // Custom finder methods
    Employee findByEmail(String email);
}
