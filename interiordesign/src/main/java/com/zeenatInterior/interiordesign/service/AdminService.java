package com.zeenatInterior.interiordesign.service;

import com.zeenatInterior.interiordesign.entity.Admin;
import com.zeenatInterior.interiordesign.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // In-memory storage for demo - aap database entities bana sakte hain
    private Map<String, Object> contentStorage = new HashMap<>();
    private List<Map<String, Object>> services = new ArrayList<>();
    private long serviceIdCounter = 1;

    public void updateContent(String section, Object data) {
        contentStorage.put(section, data);
        // Yaha aap database mein save kar sakte hain
    }

    public Object getContent(String section) {
        return contentStorage.get(section);
    }

    public void addService(Map<String, Object> serviceData) {
        serviceData.put("id", String.valueOf(serviceIdCounter++));
        serviceData.put("createdAt", new Date());
        services.add(serviceData);
    }

    public void deleteService(String id) {
        services.removeIf(service -> id.equals(service.get("id")));
    }

    public List<Map<String, Object>> getAllServices() {
        return services;
    }

    public Admin updateAdminProfile(String name, String email) {
        // Assuming there's only one admin for now
        Admin admin = adminRepository.findAll().get(0);
        admin.setName(name);
        admin.setEmail(email);
        return adminRepository.save(admin);
    }
}