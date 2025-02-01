package com.dormmatev2.dormmatev2.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;

/**
 * Controller class for handling file-related HTTP requests.
 * This class provides an endpoint to serve files (e.g., images, documents) stored on the server.
 */
@RestController
public class FileController {

    private final Path fileStorageLocation; // Path to the directory where files are stored

    /**
     * Constructor for FileController.
     * Initializes the file storage location to the "uploads" directory.
     * The path is normalized to ensure it is absolute and consistent.
     */
    public FileController() {
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize(); // Adjust this to your uploadPath
    }

    /**
     * Endpoint to serve a file by its filename.
     */
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            // Resolve the file path by combining the storage location with the filename
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            // Check if the file exists and is readable
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG) 
                        
                        // Adjust based on file type (e.g., MediaType.IMAGE_JPEG, MediaType.APPLICATION_PDF)

                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"") 
                        // Set the filename in the response header

                        .body(resource); // Return the file as the response body
            } else {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}