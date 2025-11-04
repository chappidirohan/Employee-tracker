package com.example.EmployeeTrackingSystem.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String status; // Present / Absent

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}

