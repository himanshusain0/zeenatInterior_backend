package com.zeenatInterior.interiordesign.controller;

//package com.yourcompany.interiordesign.controller;

import com.zeenatInterior.interiordesign.dto.ServiceDto;
import com.zeenatInterior.interiordesign.entity.Service;
import com.zeenatInterior.interiordesign.service.ServiceManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceManagementService serviceService;

    @GetMapping
    public ResponseEntity<List<Service>> getAll() {
        return ResponseEntity.ok(serviceService.getAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Service> create(@Valid @RequestBody ServiceDto dto) {
        return ResponseEntity.ok(serviceService.create(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Service> update(@PathVariable Long id, @Valid @RequestBody ServiceDto dto) {
        return ResponseEntity.ok(serviceService.update(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        serviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

