package com.zeenatInterior.interiordesign.service;

import com.zeenatInterior.interiordesign.entity.Admin;
import com.zeenatInterior.interiordesign.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // Simple in-memory storage for demo - aap database mein store kar sakte hain
    private Map<String, Object> contentStorage = new HashMap<>();

    public void updateContent(String section, Object data) {
        contentStorage.put(section, data);
        // Yaha aap database mein save kar sakte hain
    }

    public Object getContent(String section) {
        return contentStorage.get(section);
    }

    public Admin updateAdminProfile(String name, String email) {
        // Assuming there's only one admin for now
        Admin admin = adminRepository.findAll().get(0);
        admin.setName(name);
        admin.setEmail(email);
        return adminRepository.save(admin);
    }
}