package com.zeenatInterior.interiordesign.service;

import com.zeenatInterior.interiordesign.dto.ServiceDto;
import com.zeenatInterior.interiordesign.entity.Service;
import com.zeenatInterior.interiordesign.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

@org.springframework.stereotype.Service   // ✅ Full path likha to alias ki zarurat nahi
@RequiredArgsConstructor
public class ServiceManagementService {

    private final ServiceRepository repo;

    public List<Service> getAll() {
        return repo.findAll();
    }

    public Service create(ServiceDto dto) {
        Service s = Service.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .thumbnailUrl(dto.getThumbnailUrl())
                .active(dto.getActive() != null ? dto.getActive() : true)
                .build();
        return repo.save(s);
    }

    public Service update(Long id, ServiceDto dto) {
        Service s = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Service not found"));
        s.setTitle(dto.getTitle());
        s.setDescription(dto.getDescription());
        s.setPrice(dto.getPrice());
        s.setThumbnailUrl(dto.getThumbnailUrl());
        s.setActive(dto.getActive() != null ? dto.getActive() : s.getActive());
        return repo.save(s);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
