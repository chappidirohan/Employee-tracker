package com.example.EmployeeTrackingSystem.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.EmployeeTrackingSystem.model.Attendance;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEmployeeId(Long employeeId);
}
