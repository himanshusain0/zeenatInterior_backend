package com.zeenatInterior.interiordesign.controller;

import com.zeenatInterior.interiordesign.entity.Admin;
import com.zeenatInterior.interiordesign.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminAuthController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody Map<String, String> loginData) {
        try {
            String email = loginData.get("email");
            String password = loginData.get("password");

            // Find admin by email
            Optional<Admin> adminOptional = adminRepository.findByEmail(email);
            if (adminOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(
                        Map.of("error", "Invalid email or password")
                );
            }

            Admin admin = adminOptional.get();

            // Check password
            if (!passwordEncoder.matches(password, admin.getPassword())) {
                return ResponseEntity.badRequest().body(
                        Map.of("error", "Invalid email or password")
                );
            }

            // Generate better token
            String token = generateToken(admin.getEmail());

            // Create response
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("token", token);
            response.put("admin", Map.of(
                    "id", admin.getId(),
                    "name", admin.getName(),
                    "email", admin.getEmail(),
                    "role", admin.getRole()
            ));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Login failed: " + e.getMessage())
            );
        }
    }

    private String generateToken(String email) {
        // Better token generation
        String tokenData = email + ":" + System.currentTimeMillis() + ":" + "admin";
        return Base64.getEncoder().encodeToString(tokenData.getBytes());
    }
}