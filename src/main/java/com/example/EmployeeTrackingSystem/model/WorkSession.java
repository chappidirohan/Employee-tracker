package com.example.EmployeeTrackingSystem.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "work_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    private LocalDate date = LocalDate.now();

    private LocalDateTime clockIn;
    private LocalDateTime clockOut;

    private Double hoursWorked;
}
