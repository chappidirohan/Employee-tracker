package com.example.EmployeeTrackingSystem.Service;

import com.example.EmployeeTrackingSystem.model.employee;
import com.example.EmployeeTrackingSystem.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

@Service
public class EmployeeService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public employee getProfile(String username) {
        // try find by name first; if not, try by email
        employee emp = employeeRepository.findByName(username);
        if (emp == null) emp = employeeRepository.findByEmail(username);
        return emp;
    }

    public boolean updatePassword(Long id, String oldPassword, String newPassword) {
        Optional<employee> empOpt = employeeRepository.findById(id);
        if (empOpt.isPresent()) {
            employee emp = empOpt.get();
            String stored = emp.getPassword();
            if (stored == null) return false; // no password set (admin-created without password)
            // compare using encoder
            if (passwordEncoder.matches(oldPassword, stored)) {
                emp.setPassword(passwordEncoder.encode(newPassword));
                employeeRepository.save(emp);
                return true;
            }
        }
        return false;
    }

    // Used by Spring Security for authentication (login)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // ADMIN fallback (hardcoded admin credentials)
        if ("admin".equals(username)) {
            // hardcoded admin; password encoded with passwordEncoder to match AuthenticationManager checks
            return User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .authorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
                    .build();
        }

        // normal employee lookup: by name or email
        employee emp = employeeRepository.findByName(username);
        if (emp == null) {
            emp = employeeRepository.findByEmail(username);
        }

        if (emp == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // we will expose employee name as principal username (keeps compatibility with existing profile fetch by name)
        String principalUsername = emp.getName() != null ? emp.getName() : emp.getEmail();

        return User.builder()
                .username(principalUsername)
                .password(emp.getPassword() == null ? "" : emp.getPassword())
                .authorities(Collections.emptyList())
                .build();
    }
}
