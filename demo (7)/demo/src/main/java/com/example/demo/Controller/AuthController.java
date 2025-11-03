package com.example.demo.Controller;

import com.example.demo.Jwt.JwtUtils;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.Role;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import com.example.demo.entity.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final com.example.demo.security.CustomUserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authManager,
                          UserRepository userRepo,
                          RoleRepository roleRepo,
                          PasswordEncoder passwordEncoder,
                          JwtUtils jwtUtils,
                          com.example.demo.security.CustomUserDetailsService userDetailsService) {
        this.authManager = authManager;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody RegisterRequest req) {
        if (userRepo.existsByUsername(req.getUsername())) {
            return Map.of("error", "Username already taken");
        }
        if (userRepo.existsByEmail(req.getEmail())) {
            return Map.of("error", "Email already used");
        }

        // find user role
        Role userRole = roleRepo.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("ROLE_USER not set"));

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRoles(Set.of(userRole));
        userRepo.save(user);
        return Map.of("message", "User registered");
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // load user details and generate token
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtils.generateToken(userDetails);

        return new AuthResponse(token, "Bearer", userDetails.getUsername());
    }
}