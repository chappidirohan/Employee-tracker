package com.example.EmployeeTrackingSystem.Controller;

import com.example.EmployeeTrackingSystem.model.employee;
import com.example.EmployeeTrackingSystem.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:5174")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*--------------------------------------------------
     GET ALL EMPLOYEES + SEARCH
     --------------------------------------------------*/
    @GetMapping
    public Page<employee> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String search
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        if (search != null && !search.isEmpty()) {
            return employeeRepository.findByNameContainingIgnoreCaseOrRoleContainingIgnoreCase(
                    search, search, pageable
            );
        }

        return employeeRepository.findAll(pageable);
    }

    /*--------------------------------------------------
     ADD EMPLOYEE
     --------------------------------------------------*/
    @PostMapping
    public employee addEmployee(@RequestBody employee emp) {

        // Default: Active status
        if (emp.getStatus() == null || emp.getStatus().isEmpty()) {
            emp.setStatus("Active");
        }

        // Default password
        String rawPassword = emp.getPassword() == null ? "Abcd123" : emp.getPassword();
        emp.setPassword(passwordEncoder.encode(rawPassword));

        return employeeRepository.save(emp);
    }

    /*--------------------------------------------------
     UPDATE EMPLOYEE (NOW RETURNS UPDATED EMP OBJECT)
     --------------------------------------------------*/
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable Long id,
            @RequestBody employee payload
    ) {

        Optional<employee> empOpt = employeeRepository.findById(id);
        if (empOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee not found");
        }

        employee e = empOpt.get();

        // Update fields
        e.setName(payload.getName());
        e.setEmail(payload.getEmail());
        e.setStatus(payload.getStatus() == null ? "Active" : payload.getStatus());
        e.setDesignation(payload.getDesignation());    // ⭐ FIX: designation included

        // ROLE is not displayed on UI but still stored
        if (payload.getRole() != null) {
            e.setRole(payload.getRole());
        }

        employee updated = employeeRepository.save(e);

        // ⭐ IMPORTANT: Return UPDATED EMPLOYEE OBJECT
        return ResponseEntity.ok(updated);
    }

    /*--------------------------------------------------
     DELETE EMPLOYEE
     --------------------------------------------------*/
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {

        if (!employeeRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee not found");
        }

        employeeRepository.deleteById(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }
}
