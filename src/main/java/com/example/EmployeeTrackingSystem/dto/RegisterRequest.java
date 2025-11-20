package com.example.EmployeeTrackingSystem.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String role;   // single string from frontend
    private String status; // Active / Inactive
}