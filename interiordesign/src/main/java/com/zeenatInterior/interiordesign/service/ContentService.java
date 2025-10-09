package com.zeenatInterior.interiordesign.service;

// service/ContentService.java


import com.zeenatInterior.interiordesign.entity.*;
import com.zeenatInterior.interiordesign.repository.*;
import com.zeenatInterior.interiordesign.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContentService {

    @Autowired
    private LogoRepository logoRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AboutRepository aboutRepository;

    @Autowired
    private ContactRepository contactRepository;

    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

    public ContentService() {
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create upload directory", ex);
        }
    }

    // ==================== LOGO METHODS ====================

    public String uploadLogo(MultipartFile file) throws IOException {
        // Delete existing logos
        logoRepository.deleteAllLogos();

        // Generate unique filename
        String fileName = "logo-" + UUID.randomUUID() + "." + getFileExtension(file.getOriginalFilename());

        // Save file
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        // Save to database
        Logo logo = new Logo("/uploads/" + fileName, file.getOriginalFilename());
        logoRepository.save(logo);

        return "/uploads/" + fileName;
    }

    public Logo getCurrentLogo() {
        return logoRepository.findTopByOrderByUploadedAtDesc()
                .orElse(null);
    }

    // ==================== PROJECT METHODS ====================

    public List<Project> getAllProjects() {
        return projectRepository.findByStatusOrderByCreatedAtDesc("active");
    }

    public Project addProject(ProjectRequest projectRequest, MultipartFile imageFile) throws IOException {
        // Save image
        String imageUrl = saveFile(imageFile, "project");

        Project project = new Project();
        project.setTitle(projectRequest.getTitle());
        project.setDescription(projectRequest.getDescription());
        project.setCategory(projectRequest.getCategory());
        project.setClient(projectRequest.getClient());
        project.setProjectDate(projectRequest.getProjectDate());
        project.setImage(imageUrl);

        return projectRepository.save(project);
    }

    public Project updateProject(Long id, ProjectRequest projectRequest, MultipartFile imageFile) throws IOException {
        Optional<Project> existingProject = projectRepository.findById(id);
        if (existingProject.isEmpty()) {
            throw new RuntimeException("Project not found");
        }

        Project project = existingProject.get();
        project.setTitle(projectRequest.getTitle());
        project.setDescription(projectRequest.getDescription());
        project.setCategory(projectRequest.getCategory());
        project.setClient(projectRequest.getClient());
        project.setProjectDate(projectRequest.getProjectDate());
        project.setUpdatedAt(LocalDateTime.now());

        // Update image if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = saveFile(imageFile, "project");
            project.setImage(imageUrl);
        }

        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    // ==================== ABOUT METHODS ====================

    public About getAboutContent() {
        return aboutRepository.findTopByOrderByIdDesc()
                .orElseGet(() -> {
                    // Create default about content if not exists
                    About defaultAbout = new About();
                    defaultAbout.setTitle("About Us");
                    defaultAbout.setDescription("We are a leading interior design company...");
                    defaultAbout.setMission("Our mission is to create beautiful spaces...");
                    defaultAbout.setVision("Our vision is to be the most trusted interior design brand...");
                    return aboutRepository.save(defaultAbout);
                });
    }

    public About updateAboutContent(AboutRequest aboutRequest, MultipartFile imageFile) throws IOException {
        About about = getAboutContent();

        about.setTitle(aboutRequest.getTitle());
        about.setDescription(aboutRequest.getDescription());
        about.setMission(aboutRequest.getMission());
        about.setVision(aboutRequest.getVision());
        about.setUpdatedAt(LocalDateTime.now());

        // Update image if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = saveFile(imageFile, "about");
            about.setImage(imageUrl);
        }

        return aboutRepository.save(about);
    }

    // ==================== CONTACT METHODS ====================

    public Contact getContactContent() {
        return contactRepository.findTopByOrderByIdDesc()
                .orElseGet(() -> {
                    // Create default contact content if not exists
                    Contact defaultContact = new Contact();
                    defaultContact.setEmail("info@zeenatinterior.com");
                    defaultContact.setPhone("+91 9876543210");
                    defaultContact.setAddress("123 Design Street, Mumbai, India");
                    return contactRepository.save(defaultContact);
                });
    }

    public Contact updateContactContent(ContactRequest contactRequest) {
        Contact contact = getContactContent();

        contact.setEmail(contactRequest.getEmail());
        contact.setPhone(contactRequest.getPhone());
        contact.setAddress(contactRequest.getAddress());
        contact.setUpdatedAt(LocalDateTime.now());

        // Update additional fields
        if (contactRequest.getAdditionalFields() != null) {
            contact.getAdditionalFields().clear();
            contactRequest.getAdditionalFields().forEach(fieldRequest -> {
                ContactField field = new ContactField();
                field.setFieldLabel(fieldRequest.getFieldLabel());
                field.setFieldType(fieldRequest.getFieldType());
                field.setIsRequired(fieldRequest.getIsRequired());
                contact.getAdditionalFields().add(field);
            });
        }

        return contactRepository.save(contact);
    }

    // ==================== UTILITY METHODS ====================

    private String saveFile(MultipartFile file, String prefix) throws IOException {
        String fileName = prefix + "-" + UUID.randomUUID() + "." + getFileExtension(file.getOriginalFilename());
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + fileName;
    }

    private String getFileExtension(String fileName) {
        return fileName != null ? fileName.substring(fileName.lastIndexOf(".") + 1) : "";
    }
}