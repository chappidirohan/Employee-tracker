package com.example.EmployeeTrackingSystem.Config;

import com.example.EmployeeTrackingSystem.Repository.RoleRepository;
import com.example.EmployeeTrackingSystem.Repository.UserRepository;
import com.example.EmployeeTrackingSystem.entity.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import com.example.EmployeeTrackingSystem.entity.User;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class Datainitialiser implements CommandLineRunner {

    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public Datainitialiser(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = roleRepo.findByName("ROLE_ADMIN").orElseGet(() -> roleRepo.save(new Role(null, "ROLE_ADMIN")));
        Role userRole = roleRepo.findByName("ROLE_USER").orElseGet(() -> roleRepo.save(new Role(null, "ROLE_USER")));

        // create admin if not exists
        if (userRepo.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123")); // change password
            admin.setRoles(Set.of(adminRole, userRole));
            userRepo.save(admin);
        }

    }
}