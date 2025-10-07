package com.zeenatInterior.interiordesign.service;



import com.zeenatInterior.interiordesign.entity.User;
import com.zeenatInterior.interiordesign.entity.UserImage;
import com.zeenatInterior.interiordesign.repository.UserImageRepository;
import com.zeenatInterior.interiordesign.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final UserRepository userRepo;
    private final UserImageRepository imageRepo;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    public UserImage store(MultipartFile file, String email) {
        if (file.isEmpty()) throw new IllegalArgumentException("File is empty");

        User user = userRepo.findByEmail(email).orElseThrow();

        try {
            Path dir = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(dir);

            String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String safeExt = (ext != null && !ext.isBlank()) ? "." + ext : "";
            String newName = UUID.randomUUID() + safeExt;

            Path target = dir.resolve(newName);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            String url = "/uploads/" + newName;

            UserImage img = UserImage.builder()
                    .filename(newName)
                    .url(url)
                    .size(file.getSize())
                    .contentType(file.getContentType())
                    .user(user)
                    .build();

            return imageRepo.save(img);
        } catch (IOException e) {
            throw new RuntimeException("Could not store file", e);
        }
    }

    public void delete(Long imageId, String email) {
        UserImage img = imageRepo.findById(imageId).orElseThrow(() -> new IllegalArgumentException("Image not found"));

        User requester = userRepo.findByEmail(email).orElseThrow();
        boolean isOwner = img.getUser().getId().equals(requester.getId());
        boolean isAdmin = requester.getRole().name().equals("ADMIN");

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("Not allowed");
        }

        try {
            Path path = Paths.get(uploadDir).resolve(img.getFilename()).toAbsolutePath().normalize();
            Files.deleteIfExists(path);
        } catch (Exception ignored) {}
        imageRepo.delete(img);
    }
}