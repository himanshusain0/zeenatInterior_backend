package com.zeenatInterior.interiordesign.controller;

import com.zeenatInterior.interiordesign.entity.*;
import com.zeenatInterior.interiordesign.service.ContentService;
import com.zeenatInterior.interiordesign.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/content")
@CrossOrigin(origins = "*")
public class ContentController {

    @Autowired(required = false) // required = false taaki simple homeContent() bhi bina service ke chale
    private ContentService contentService;

    // ==================== HOME CONTENT (Original - No Changes) ====================

    @GetMapping("/home")
    public ResponseEntity<?> homeContent() {
        return ResponseEntity.ok(Map.of(
                "heroTitle", "Design your dream space",
                "heroSubtitle", "Custom interior design services tailored for you",
                "cta", "Get a free consultation"
        ));
    }

    // ==================== LOGO ENDPOINTS ====================

    @PostMapping("/admin/logo")
    public ResponseEntity<?> uploadLogo(@RequestParam("logo") MultipartFile file) {
        try {
            String logoUrl = contentService.uploadLogo(file);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Logo uploaded successfully");
            response.put("logoUrl", logoUrl);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", "Error uploading logo: " + e.getMessage())
            );
        }
    }

    @GetMapping("/admin/logo")
    public ResponseEntity<?> getLogo() {
        try {
            Logo logo = contentService.getCurrentLogo();

            if (logo == null) {
                return ResponseEntity.ok(Map.of("success", false, "message", "No logo found"));
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("logoUrl", logo.getLogoUrl());
            response.put("originalName", logo.getOriginalName());
            response.put("uploadedAt", logo.getUploadedAt());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", "Error getting logo: " + e.getMessage())
            );
        }
    }

    // ==================== PORTFOLIO ENDPOINTS ====================

    @GetMapping("/admin/portfolio")
    public ResponseEntity<?> getAllProjects() {
        try {
            List<Project> projects = contentService.getAllProjects();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("count", projects.size());
            response.put("projects", projects);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", "Error getting projects: " + e.getMessage())
            );
        }
    }

    @PostMapping("/admin/portfolio")
    public ResponseEntity<?> addProject(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam(value = "client", required = false) String client,
            @RequestParam(value = "projectDate", required = false) String projectDate,
            @RequestParam("image") MultipartFile imageFile) {
        try {
            ProjectRequest projectRequest = new ProjectRequest();
            projectRequest.setTitle(title);
            projectRequest.setDescription(description);
            projectRequest.setCategory(category);
            projectRequest.setClient(client);

            Project project = contentService.addProject(projectRequest, imageFile);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Project added successfully");
            response.put("project", project);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", "Error adding project: " + e.getMessage())
            );
        }
    }

    @PutMapping("/admin/portfolio/{id}")
    public ResponseEntity<?> updateProject(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam(value = "client", required = false) String client,
            @RequestParam(value = "projectDate", required = false) String projectDate,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        try {
            ProjectRequest projectRequest = new ProjectRequest();
            projectRequest.setTitle(title);
            projectRequest.setDescription(description);
            projectRequest.setCategory(category);
            projectRequest.setClient(client);

            Project project = contentService.updateProject(id, projectRequest, imageFile);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Project updated successfully");
            response.put("project", project);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", "Error updating project: " + e.getMessage())
            );
        }
    }

    @DeleteMapping("/admin/portfolio/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        try {
            contentService.deleteProject(id);

            return ResponseEntity.ok(
                    Map.of("success", true, "message", "Project deleted successfully")
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", "Error deleting project: " + e.getMessage())
            );
        }
    }

    // ==================== ABOUT ENDPOINTS ====================

    @GetMapping("/admin/about")
    public ResponseEntity<?> getAboutContent() {
        try {
            About about = contentService.getAboutContent();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("about", about);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", "Error getting about content: " + e.getMessage())
            );
        }
    }

    @PostMapping("/admin/about")
    public ResponseEntity<?> updateAboutContent(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("mission") String mission,
            @RequestParam("vision") String vision,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        try {
            AboutRequest aboutRequest = new AboutRequest();
            aboutRequest.setTitle(title);
            aboutRequest.setDescription(description);
            aboutRequest.setMission(mission);
            aboutRequest.setVision(vision);

            About about = contentService.updateAboutContent(aboutRequest, imageFile);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "About content updated successfully");
            response.put("about", about);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", "Error updating about content: " + e.getMessage())
            );
        }
    }

    // ==================== CONTACT ENDPOINTS ====================

    @GetMapping("/admin/contact")
    public ResponseEntity<?> getContactContent() {
        try {
            Contact contact = contentService.getContactContent();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("contact", contact);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", "Error getting contact content: " + e.getMessage())
            );
        }
    }

    @PostMapping("/admin/contact")
    public ResponseEntity<?> updateContactContent(@RequestBody ContactRequest contactRequest) {
        try {
            Contact contact = contentService.updateContactContent(contactRequest);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Contact content updated successfully");
            response.put("contact", contact);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", "Error updating contact content: " + e.getMessage())
            );
        }
    }
}
