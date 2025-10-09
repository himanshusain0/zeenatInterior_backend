package com.zeenatInterior.interiordesign.service;

import com.zeenatInterior.interiordesign.dto.QueryDto;
import com.zeenatInterior.interiordesign.entity.Query;
import com.zeenatInterior.interiordesign.repository.QueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryService {

    private final QueryRepository repo;

    public Query submitQuery(QueryDto dto) {
        Query q = Query.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .message(dto.getMessage())
                .status("NEW")
                .build();
        return repo.save(q);
    }

    public void deleteQuery(Long id) {
        if (!repo.existsById(id)) { // ✅ 'repo' use karein, 'queryRepository' nahi
            throw new RuntimeException("Query not found with id: " + id);
        }
        repo.deleteById(id); // ✅ 'repo' use karein
    }

    public List<Query> getAll() {
        return repo.findAll();
    }
}