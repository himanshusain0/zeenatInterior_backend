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

    public List<Query> getAll() {
        return repo.findAll();
    }
}