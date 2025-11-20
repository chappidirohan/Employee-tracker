package com.example.EmployeeTrackingSystem.Controller;

import com.example.EmployeeTrackingSystem.Repository.RoleRepository;

import com.example.EmployeeTrackingSystem.Repository.UserRepository;

import com.example.EmployeeTrackingSystem.dto.AuthRequest;

import com.example.EmployeeTrackingSystem.dto.RegisterRequest;

import com.example.EmployeeTrackingSystem.entity.Role;

import com.example.EmployeeTrackingSystem.entity.User;

import com.example.EmployeeTrackingSystem.Jwt.JwtUtils;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@RestController

@RequestMapping("/api/auth")

@CrossOrigin(origins = "http://localhost:5173")

public class AuthController {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    public AuthController(UserRepository userRepository,

                          RoleRepository roleRepository,

                          PasswordEncoder passwordEncoder,

                          JwtUtils jwtUtils) {

        this.userRepository = userRepository;

        this.roleRepository = roleRepository;

        this.passwordEncoder = passwordEncoder;

        this.jwtUtils = jwtUtils;

    }

    // =====================================================

    // REGISTER (WORKING)

    // =====================================================

    @PostMapping("/register")

    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {

        if (userRepository.existsByUsername(req.getUsername())) {

            return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));

        }

        if (userRepository.existsByEmail(req.getEmail())) {

            return ResponseEntity.badRequest().body(Map.of("error", "Email already exists"));

        }

        // Get or create role

        Role role = roleRepository.findByName(req.getRole())

                .orElseGet(() -> {

                    Role r = new Role();

                    r.setName(req.getRole());

                    return roleRepository.save(r);

                });

        User user = new User();

        user.setUsername(req.getUsername());

        user.setEmail(req.getEmail());

        user.setPassword(passwordEncoder.encode(req.getPassword()));

        user.setStatus(req.getStatus() == null ? "Active" : req.getStatus());

        Set<Role> roles = new HashSet<>();

        roles.add(role);

        user.setRoles(roles);

        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "User registered"));

    }

    // =====================================================

    // LOGIN (ADDING NOW)

    // =====================================================

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {

        // LOGIN USING EMAIL OR USERNAME
        User user = userRepository.findByUsernameOrEmail(req.getUsername(), req.getUsername())
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("message", "User not found"));
        }

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid password"));
        }

        String token = jwtUtils.generateToken(user);

        return ResponseEntity.ok(Map.of("token", token));
    }

    // =====================================================
// GET ALL REGISTERED EMPLOYEES FOR ADMIN PAGE
// =====================================================
    @GetMapping("/registered-employees")
    public ResponseEntity<?> getRegisteredEmployees() {

        List<User> users = userRepository.findAll();

        List<Map<String, Object>> employees = new ArrayList<>();

        for (User u : users) {
            Map<String, Object> emp = new HashMap<>();

            emp.put("id", u.getId());
            emp.put("name", u.getUsername());
            emp.put("email", u.getEmail());

            // DEFAULT STATUS = Active
            emp.put("status", u.getStatus() == null ? "Active" : u.getStatus());

            // ADD DESIGNATION (You added this field in your User entity)
            emp.put("designation", u.getDesignation());

            // REMOVE ROLE from UI (as you requested)
            // role is no longer sent to UI

            employees.add(emp);
        }

        return ResponseEntity.ok(employees);
    }
}

