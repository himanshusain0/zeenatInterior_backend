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

    // ==================== CONTENT MANAGEMENT ====================

    @PutMapping("/content")
    public ResponseEntity<?> updateContent(@RequestBody Map<String, Object> contentData,
                                           @RequestHeader("Authorization") String token) {
        try {
            if (!isValidAdminToken(token)) {
                return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
            }

            String section = (String) contentData.get("section");
            Object data = contentData.get("data");

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

    // ==================== CONTACT QUERIES ====================

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

    // ==================== SERVICES MANAGEMENT ====================

    @PostMapping("/services")
    public ResponseEntity<?> addService(@RequestBody Map<String, String> serviceData,
                                        @RequestHeader("Authorization") String token) {
        try {
            if (!isValidAdminToken(token)) {
                return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
            }

            String title = serviceData.get("title");
            String description = serviceData.get("description");
            String icon = serviceData.get("icon");

            Map<String, Object> service = new HashMap<>();
            service.put("title", title);
            service.put("description", description);
            service.put("icon", icon);

            adminService.addService(service);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Service added successfully"
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Failed to add service: " + e.getMessage()
            ));
        }
    }

    @DeleteMapping("/services/{id}")
    public ResponseEntity<?> deleteService(@PathVariable String id,
                                           @RequestHeader("Authorization") String token) {
        try {
            if (!isValidAdminToken(token)) {
                return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
            }

            adminService.deleteService(id);
            return ResponseEntity.ok(Map.of("message", "Service deleted successfully"));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Failed to delete service: " + e.getMessage()
            ));
        }
    }

    @GetMapping("/services")
    public ResponseEntity<?> getAllServices() {
        try {
            List<Map<String, Object>> services = adminService.getAllServices();
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Failed to get services: " + e.getMessage()
            ));
        }
    }

    // ==================== TOKEN VALIDATION ====================

    private boolean isValidAdminToken(String token) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return false;
            }

            String actualToken = token.replace("Bearer ", "");
            String decoded = new String(Base64.getDecoder().decode(actualToken));
            String[] parts = decoded.split(":");

            return parts.length >= 3 && "admin".equals(parts[2]);

        } catch (Exception e) {
            return false;
        }
    }
}