package com.zeenatInterior.interiordesign.controller;

import com.zeenatInterior.interiordesign.dto.QueryDto;
import com.zeenatInterior.interiordesign.entity.Query;
import com.zeenatInterior.interiordesign.service.QueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}