package com.example.EmployeeTrackingSystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String role;

    private String email;

    // ⭐ Default status is ALWAYS Active
    private String status = "Active";

    // ⭐ New field
    private String designation;

    private String password;



    public employee(String name, String role, String email, String status, String password, String designation) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.status = status != null ? status : "Active"; // default safety
        this.password = password;
        this.designation = designation;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
}
