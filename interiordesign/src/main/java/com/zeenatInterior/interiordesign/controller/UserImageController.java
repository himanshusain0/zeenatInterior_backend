package com.zeenatInterior.interiordesign.controller;



import com.zeenatInterior.interiordesign.entity.UserImage;
import com.zeenatInterior.interiordesign.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user-images")
@RequiredArgsConstructor
public class UserImageController {

    private final FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<UserImage> upload(@RequestParam("file") MultipartFile file, Authentication auth) {
        return ResponseEntity.ok(fileStorageService.store(file, auth.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication auth) {
        fileStorageService.delete(id, auth.getName());
        return ResponseEntity.noContent().build();
    }
}