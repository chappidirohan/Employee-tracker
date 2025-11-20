package com.example.EmployeeTrackingSystem.Repository;

import com.example.EmployeeTrackingSystem.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Tasks, Long> {
    List<Tasks> findByEmployeeId(Long employeeId);
}
