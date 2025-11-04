package com.example.EmployeeTrackingSystem.controller;

import com.example.EmployeeTrackingSystem.Repository.Employeerepository;
import com.example.EmployeeTrackingSystem.config.JwtUtil;
import com.example.EmployeeTrackingSystem.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    private Employeerepository employeeRepo;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Employee loginReq) {
        Employee emp = employeeRepo.findByEmail(loginReq.getEmail());
        if (emp != null && emp.getPassword().equals(loginReq.getPassword())) {
            String token = jwtUtil.generateToken(emp.getEmail());
            return ResponseEntity.ok(Map.of("token", token, "role", emp.getRole(), "name", emp.getName()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
