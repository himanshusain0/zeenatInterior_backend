package com.zeenatInterior.interiordesign.controller;



import com.zeenatInterior.interiordesign.entity.Admin;
import com.zeenatInterior.interiordesign .repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminSetupController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Check if admin setup is needed
    @GetMapping("/check-setup")
    public ResponseEntity<?> checkAdminSetup() {
        try {
            long adminCount = adminRepository.count();
            Map<String, Object> response = new HashMap<>();
            response.put("adminExists", adminCount > 0);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Error checking admin setup: " + e.getMessage())
            );
        }
    }

    // Create first admin account
    @PostMapping("/setup")
    public ResponseEntity<?> setupAdmin(@RequestBody Map<String, String> adminData) {
        try {
            String name = adminData.get("name");
            String email = adminData.get("email");
            String password = adminData.get("password");

            // Check if any admin already exists
            long adminCount = adminRepository.count();
            if (adminCount > 0) {
                return ResponseEntity.badRequest().body(
                        Map.of("error", "Admin account already exists. Please login instead.")
                );
            }

            // Validate data
            if (name == null || name.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    password == null || password.length() < 6) {
                return ResponseEntity.badRequest().body(
                        Map.of("error", "All fields are required and password must be at least 6 characters")
                );
            }

            // Create new admin
            Admin admin = new Admin();
            admin.setName(name.trim());
            admin.setEmail(email.trim().toLowerCase());
            admin.setPassword(passwordEncoder.encode(password));
            admin.setRole("SUPER_ADMIN");

            Admin savedAdmin = adminRepository.save(admin);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Admin account created successfully!");
            response.put("admin", Map.of(
                    "id", savedAdmin.getId(),
                    "name", savedAdmin.getName(),
                    "email", savedAdmin.getEmail(),
                    "role", savedAdmin.getRole()
            ));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Error creating admin: " + e.getMessage())
            );
        }
    }

    // Emergency admin creation endpoint
    @PostMapping("/create-default")
    public ResponseEntity<?> createDefaultAdmin() {
        try {
            // Check if admin already exists
            Optional<Admin> existingAdmin = adminRepository.findByEmail("admin@zeenatinterior.com");
            if (existingAdmin.isPresent()) {
                return ResponseEntity.ok(Map.of("message", "Default admin already exists"));
            }

            // Create default admin
            Admin admin = new Admin();
            admin.setName("Super Admin");
            admin.setEmail("admin@zeenatinterior.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("SUPER_ADMIN");

            adminRepository.save(admin);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Default admin created successfully",
                    "credentials", Map.of(
                            "email", "admin@zeenatinterior.com",
                            "password", "admin123"
                    )
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Error creating default admin: " + e.getMessage())
            );
        }
    }
}
