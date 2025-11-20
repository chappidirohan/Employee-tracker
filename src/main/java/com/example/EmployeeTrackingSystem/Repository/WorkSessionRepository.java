package com.example.EmployeeTrackingSystem.repository;

import com.example.EmployeeTrackingSystem.model.WorkSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository

public interface WorkSessionRepository extends JpaRepository<WorkSession, Long> {

    List<WorkSession> findByUsername(String username);

    Optional<WorkSession> findByUsernameAndClockOutIsNull(String username);

    List<WorkSession> findByUsernameAndDate(String username, LocalDate date);
}

