package com.example.EmployeeTrackingSystem.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")   // DB table name
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long employeeId;   // which employee created the task

    @Column(nullable = false)
    private String title;

    private String description;

    private LocalDate date;    // task date (auto set when created)

    private boolean completed = false;

    private int progress = 0;  // 0 to 100 %

    public Tasks() {}

    // ---------- Getters & Setters ----------
    public Long getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        if (progress == 100) {
            this.completed = true;
        }
    }
}
