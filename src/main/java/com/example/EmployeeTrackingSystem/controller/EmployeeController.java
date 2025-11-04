package com.example.EmployeeTrackingSystem.controller;

import com.example.EmployeeTrackingSystem.Repository.Employeerepository;
import com.example.EmployeeTrackingSystem.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin
public class EmployeeController {
    @Autowired
    private Employeerepository repo;

    @GetMapping
    public List<Employee> all() { return repo.findAll(); }

    @PostMapping
    public Employee add(@RequestBody Employee emp) { return repo.save(emp); }

    @PutMapping("/{id}")
    public Employee update(@PathVariable Long id, @RequestBody Employee emp) {
        emp.setId(id);
        return repo.save(emp);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repo.deleteById(id); }
}
