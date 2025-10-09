package com.zeenatInterior.interiordesign.controller;

import com.zeenatInterior.interiordesign.entity.Admin;
import com.zeenatInterior.interiordesign.entity.Query;
import com.zeenatInterior.interiordesign.service.AdminService;
import com.zeenatInterior.interiordesign.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private QueryService queryService;

    // Content Management Endpoints

    @PutMapping("/update-content")
    public ResponseEntity<?> updateContent(@RequestBody Map<String, Object> contentData,
                                           @RequestHeader("Authorization") String token) {
        try {
            if (!isValidAdminToken(token)) {
                return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
            }

            String section = (String) contentData.get("section");
            Object data = contentData.get("data");

            // Yaha aap content update logic implement karenge
            // Jaise database mein save karna ya file mein update karna
            adminService.updateContent(section, data);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Content updated successfully",
                    "section", section
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Failed to update content: " + e.getMessage()
            ));
        }
    }

    // Navbar Logo Update
    @PutMapping("/navbar/logo")
    public ResponseEntity<?> updateNavbarLogo(@RequestBody Map<String, String> logoData,
                                              @RequestHeader("Authorization") String token) {
        try {
            if (!isValidAdminToken(token)) {
                return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
            }

            String logoUrl = logoData.get("logoUrl");
            adminService.updateContent("navbarLogo", logoUrl);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Navbar logo updated successfully",
                    "logoUrl", logoUrl
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Failed to update logo: " + e.getMessage()
            ));
        }
    }

    // About Us Content Update
    @PutMapping("/about")
    public ResponseEntity<?> updateAboutUs(@RequestBody Map<String, String> aboutData,
                                           @RequestHeader("Authorization") String token) {
        try {
            if (!isValidAdminToken(token)) {
                return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
            }

            String aboutText = aboutData.get("aboutText");
            adminService.updateContent("aboutText", aboutText);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "About us content updated successfully"
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Failed to update about us: " + e.getMessage()
            ));
        }
    }

    // Contact Information Update
    @PutMapping("/contact")
    public ResponseEntity<?> updateContactInfo(@RequestBody Map<String, String> contactData,
                                               @RequestHeader("Authorization") String token) {
        try {
            if (!isValidAdminToken(token)) {
                return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
            }

            adminService.updateContent("contactInfo", contactData);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Contact information updated successfully"
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Failed to update contact info: " + e.getMessage()
            ));
        }
    }

    // Get All Contact Queries (Admin Panel ke liye)
    @GetMapping("/queries")
    public ResponseEntity<?> getAllQueries(@RequestHeader("Authorization") String token) {
        try {
            if (!isValidAdminToken(token)) {
                return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
            }

            List<Query> queries = queryService.getAll();
            return ResponseEntity.ok(queries);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Failed to fetch queries: " + e.getMessage()
            ));
        }
    }

    // Delete Contact Query
    @DeleteMapping("/queries/{id}")
    public ResponseEntity<?> deleteQuery(@PathVariable Long id,
                                         @RequestHeader("Authorization") String token) {
        try {
            if (!isValidAdminToken(token)) {
                return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
            }

            queryService.deleteQuery(id);
            return ResponseEntity.ok(Map.of("message", "Query deleted successfully"));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Failed to delete query: " + e.getMessage()
            ));
        }
    }

    // Get Current Content (Frontend ke liye)
    @GetMapping("/content")
    public ResponseEntity<?> getContent(@RequestParam String section) {
        try {
            Object content = adminService.getContent(section);
            return ResponseEntity.ok(Map.of(
                    "section", section,
                    "data", content
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Failed to get content: " + e.getMessage()
            ));
        }
    }

    // Admin Profile Update
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, String> profileData,
                                           @RequestHeader("Authorization") String token) {
        try {
            if (!isValidAdminToken(token)) {
                return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
            }

            String name = profileData.get("name");
            String email = profileData.get("email");

            Admin updatedAdmin = adminService.updateAdminProfile(name, email);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Profile updated successfully",
                    "admin", updatedAdmin
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Failed to update profile: " + e.getMessage()
            ));
        }
    }

    // Token Validation Method
    private boolean isValidAdminToken(String token) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return false;
            }

            String actualToken = token.replace("Bearer ", "");
            String decoded = new String(Base64.getDecoder().decode(actualToken));
            String[] parts = decoded.split(":");

            // Token structure: email:timestamp:role
            return parts.length >= 3 && "admin".equals(parts[2]);

        } catch (Exception e) {
            return false;
        }
    }
}