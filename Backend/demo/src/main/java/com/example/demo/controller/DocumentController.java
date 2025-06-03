package com.example.demo.controller;

import com.example.demo.model.Document;
import com.example.demo.model.Auteur;
import com.example.demo.model.Theme;
import com.example.demo.model.Administrateur;
import com.example.demo.service.DocumentService;
import com.example.demo.service.AuteurService;
import com.example.demo.service.ThemeService;
import com.example.demo.service.AdministrateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private AuteurService auteurService;

    @Autowired
    private ThemeService themeService;

    @Autowired
    private AdministrateurService administrateurService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping
    public ResponseEntity<?> createDocument(
            @RequestParam("titre") String titre,
            @RequestParam("auteur") String auteurJson,
            @RequestParam(value = "motsCles", required = false) String motsCles,
            @RequestParam("resume") String resume,
            @RequestParam("themeId") Long themeId,
            @RequestParam("adminId") Long adminId,
            @RequestParam("file") MultipartFile file) {
        try {
            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + fileExtension;

            // Save file to disk
            Path filePath = uploadPath.resolve(newFilename);
            Files.copy(file.getInputStream(), filePath);

            // Parse author JSON and save the author first
            Auteur auteur = objectMapper.readValue(auteurJson, Auteur.class);
            Auteur savedAuteur = auteurService.createAuteur(auteur);

            // Get theme and admin
            Theme theme = themeService.getThemeById(themeId);
            if (theme == null) {
                return ResponseEntity.badRequest().body("Theme not found");
            }

            Administrateur admin = administrateurService.getAdministrateurById(adminId);
            if (admin == null) {
                return ResponseEntity.badRequest().body("Administrator not found");
            }

            // Create and save document
            Document document = new Document();
            document.setTitre(titre);
            document.setResume(resume);
            document.setAuteur(savedAuteur); // Use the saved author
            document.setTheme(theme);
            document.setAdministrateur(admin);
            document.setMotsCles(motsCles);
            document.setDatePublication(new Date());
            document.setTypeFichier(fileExtension);
            document.setFileName(originalFilename);
            document.setFilePath(filePath.toString());
            document.setFileSize(file.getSize());

            Document savedDocument = documentService.createDocument(document);
            return ResponseEntity.ok(savedDocument);

        } catch (Exception e) {
            // Log the full stack trace
            e.printStackTrace();

            // Return detailed error message
            String errorMessage = "Error creating document: " + e.getMessage();
            if (e.getCause() != null) {
                errorMessage += " Caused by: " + e.getCause().getMessage();
            }
            return ResponseEntity.internalServerError().body(errorMessage);
        }
    }

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        return ResponseEntity.ok(documentService.getAllDocuments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        Document document = documentService.getDocumentById(id);
        if (document != null) {
            return ResponseEntity.ok(document);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        Document document = documentService.getDocumentById(id);
        if (document != null) {
            // Delete file from disk
            try {
                Files.deleteIfExists(Paths.get(document.getFilePath()));
            } catch (IOException e) {
                // Log error but continue with database deletion
                e.printStackTrace();
            }
            documentService.deleteDocument(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id) {
        try {
            Document document = documentService.getDocumentById(id);
            if (document == null) {
                return ResponseEntity.notFound().build();
            }

            // Increment download counter
            document.incrementNombreTelechargements();
            documentService.updateDocument(document);

            Path filePath = Paths.get(document.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            // Determine content type
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFileName() + "\"")
                    .body(resource);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDocument(
            @PathVariable Long id,
            @RequestParam("titre") String titre,
            @RequestParam(value = "motsCles", required = false) String motsCles,
            @RequestParam("resume") String resume,
            @RequestParam("themeId") Long themeId) {
        try {
            Document existingDocument = documentService.getDocumentById(id);
            if (existingDocument == null) {
                return ResponseEntity.notFound().build();
            }

            // Get theme
            Theme theme = themeService.getThemeById(themeId);
            if (theme == null) {
                return ResponseEntity.badRequest().body("Theme not found");
            }

            // Update document fields
            existingDocument.setTitre(titre);
            existingDocument.setResume(resume);
            existingDocument.setMotsCles(motsCles);
            existingDocument.setTheme(theme);

            Document updatedDocument = documentService.updateDocument(existingDocument);
            return ResponseEntity.ok(updatedDocument);

        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "Error updating document: " + e.getMessage();
            if (e.getCause() != null) {
                errorMessage += " Caused by: " + e.getCause().getMessage();
            }
            return ResponseEntity.internalServerError().body(errorMessage);
        }
    }
}
