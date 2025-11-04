package com.example.EmployeeTrackingSystem.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.EmployeeTrackingSystem.model.Task;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Custom method to get tasks assigned to a particular employee
    List<Task> findByAssignedToId(Long employeeId);
}

