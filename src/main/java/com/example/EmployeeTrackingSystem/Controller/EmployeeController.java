package com.example.EmployeeTrackingSystem.Controller;

// <<<<<<< manasa/feature
// public class EmployeeController {
// }
// =======
// import com.example.EmployeeTrackingSystem.model.employee;
// import com.example.EmployeeTrackingSystem.Repository.EmployeeRepository;
// import com.example.EmployeeTrackingSystem.Service.EmployeeService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.*;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.*;

// import java.util.Map;
// import java.util.Optional;

// @RestController
// @RequestMapping("/api/employees")
// @CrossOrigin(origins = "http://localhost:5174")
// public class EmployeeController {

//     @Autowired
//     private EmployeeRepository employeeRepository;

//     @Autowired
//     private EmployeeService employeeService;

//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     @GetMapping
//     public Page<employee> getAllEmployees(
//             @RequestParam(defaultValue = "0") int page,
//             @RequestParam(defaultValue = "5") int size,
//             @RequestParam(required = false) String search
//     ) {
//         Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

//         if (search != null && !search.isEmpty()) {
//             return employeeRepository.findByNameContainingIgnoreCaseOrRoleContainingIgnoreCase(
//                     search, search, pageable
//             );
//         }
//         return employeeRepository.findAll(pageable);
//     }

//     @GetMapping("/{id}")
//     public Optional<employee> getEmployeeById(@PathVariable Long id) {
//         return employeeRepository.findById(id);
//     }

//     @PostMapping
//     public employee addEmployee(@RequestBody employee emp) {
//         if (emp.getStatus() == null || emp.getStatus().isEmpty()) {
//             emp.setStatus("Active");
//         }

//         String rawPassword = emp.getPassword();
//         if (rawPassword == null || rawPassword.trim().isEmpty()) {
//             rawPassword = "Abcd123";   // default password
//         }
//         emp.setPassword(passwordEncoder.encode(rawPassword)); // store hashed password

//         return employeeRepository.save(emp);
//     }

//     @PutMapping("/update/{id}")
//     public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody employee payload) {

//         Optional<employee> emp = employeeRepository.findById(id);
//         if (emp.isEmpty()) {
//             return ResponseEntity.badRequest().body("Employee not found");
//         }

//         employee e = emp.get();

//         e.setName(payload.getName());
//         e.setEmail(payload.getEmail());
//         e.setRole(payload.getRole());
//         e.setStatus(payload.getStatus());

//         employeeRepository.save(e);

//         return ResponseEntity.ok("Employee updated successfully");
//     }

//     @DeleteMapping("/{id}")
//     public String deleteEmployee(@PathVariable Long id) {
//         employeeRepository.deleteById(id);
//         return "Employee deleted successfully";
//     }

//     @GetMapping("/profile/{username}")
//     public employee getProfile(@PathVariable String username) {
//         return employeeService.getProfile(username);
//     }

//     @PutMapping("/change-password/{id}")
//     public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody Map<String, String> req) {

//         Optional<employee> empOpt = employeeRepository.findById(id);
//         if (empOpt.isEmpty()) {
//             return ResponseEntity.badRequest().body("Employee not found");
//         }

//         employee e = empOpt.get();

//         String oldPassword = req.get("oldPassword");
//         String newPassword = req.get("newPassword");

//         if (oldPassword == null || newPassword == null) {
//             return ResponseEntity.badRequest().body("Invalid request");
//         }

//         String stored = e.getPassword();

//         // Debugging logs
//         System.out.println("Received old password: " + oldPassword);
//         System.out.println("Stored password: " + stored);

//         boolean isCorrect = false;

//         // If stored is encrypted (bcrypt)
//         if (stored != null && stored.startsWith("$2a$")) {
//             isCorrect = passwordEncoder.matches(oldPassword, stored);
//         } else {
//             // Fallback: stored is plaintext
//             isCorrect = stored.equals(oldPassword);
//         }

//         System.out.println("Password correct? " + isCorrect);

//         if (!isCorrect) {
//             return ResponseEntity.badRequest().body("Old password incorrect");
//         }

//         // Save new encrypted password
//         e.setPassword(passwordEncoder.encode(newPassword));
//         employeeRepository.save(e);

//         return ResponseEntity.ok("Password changed successfully");
//     }
// }

// >>>>>>> bhumi/feature
