package com.zeenatInterior.interiordesign.controller;

import com.zeenatInterior.interiordesign.dto.QueryDto;
import com.zeenatInterior.interiordesign.entity.Query;
import com.zeenatInterior.interiordesign.service.QueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/queries")
@RequiredArgsConstructor
public class QueryController {

    private final QueryService queryService;

    @PostMapping
    public ResponseEntity<Query> submit(@Valid @RequestBody QueryDto dto) {
        return ResponseEntity.ok(queryService.submitQuery(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Query>> listAll() {
        return ResponseEntity.ok(queryService.getAll());
    }

    // New endpoint for deleting queries
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuery(@PathVariable Long id) {
        try {
            queryService.deleteQuery(id);
            return ResponseEntity.ok(Map.of("message", "Query deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Failed to delete query: " + e.getMessage())
            );
        }
    }

    // New endpoint for admin to get queries with token validation
    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllQueriesForAdmin(@RequestHeader(value = "Authorization", required = false) String token) {
        try {
            // Simple token validation
            if (token == null || !isValidAdminToken(token)) {
                return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
            }

            List<Query> queries = queryService.getAll();
            return ResponseEntity.ok(queries);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Failed to fetch queries: " + e.getMessage())
            );
        }
    }

    private boolean isValidAdminToken(String token) {
        try {
            String actualToken = token.replace("Bearer ", "");
            // Decode and validate token
            String decoded = new String(Base64.getDecoder().decode(actualToken));
            String[] parts = decoded.split(":");
            return parts.length >= 3 && "admin".equals(parts[2]);
        } catch (Exception e) {
            return false;
        }
    }
}