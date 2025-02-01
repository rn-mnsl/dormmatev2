package com.dormmatev2.dormmatev2.controller;

// Import all the necessary packages 
import com.dormmatev2.dormmatev2.model.MaintenanceRequest;
import com.dormmatev2.dormmatev2.service.MaintenanceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/maintenance-requests") // Base endpoint for maintenance requests
public class MaintenanceRequestController {

    @Autowired
    private MaintenanceRequestService maintenanceRequestService; // Service for maintenance request operations

    // Endpoint to create a new maintenance request

    @PostMapping 
    public ResponseEntity<MaintenanceRequest> createMaintenanceRequest(
            @RequestParam Long tenantId, // Tenant ID for the request
            @RequestParam Long unitId, // Unit ID for the request
            @RequestParam("description") String description, // Description of the issue
            @RequestParam(value = "proofOfDamage", required = false) MultipartFile proofOfDamage) { // Optional file upload
        try {
            MaintenanceRequest maintenanceRequest = new MaintenanceRequest();
            maintenanceRequest.setDescription(description);
            if (proofOfDamage != null && !proofOfDamage.isEmpty()) { // Handle file upload if provided
                String fileName = UUID.randomUUID().toString() + "_" + proofOfDamage.getOriginalFilename();
                Path filePath = Paths.get("upload/" + fileName);
                Files.write(filePath, proofOfDamage.getBytes());
                maintenanceRequest.setProofOfDamage(fileName);
            } else {
                // No file provided

                maintenanceRequest.setProofOfDamage(null); 
            }
            MaintenanceRequest savedRequest = maintenanceRequestService.saveMaintenanceRequest(maintenanceRequest, tenantId, unitId);
            return new ResponseEntity<>(savedRequest, HttpStatus.CREATED); 
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); 
        } catch (IOException e) {
            throw new RuntimeException(e); 
        }
    }

    // Endpoint to fetch all maintenance requests

    @GetMapping 
    public ResponseEntity<List<MaintenanceRequest>> getAllMaintenanceRequests() {
        List<MaintenanceRequest> requests = maintenanceRequestService.findAllMaintenanceRequests();
        return new ResponseEntity<>(requests, HttpStatus.OK); // Return all requests
    }

    // Endpoint to fetch a maintenance request by ID

    @GetMapping("/{id}") 
    public ResponseEntity<MaintenanceRequest> getMaintenanceRequestById(@PathVariable Long id) {
        Optional<MaintenanceRequest> request = maintenanceRequestService.findMaintenanceRequestById(id);
        return request.map(value -> new ResponseEntity<>(value, HttpStatus.OK)) // Return request if found
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Return 404 if not found
    }

    // Endpoint to update a maintenance request by ID

    @PutMapping("/{id}") 
    public ResponseEntity<MaintenanceRequest> updateMaintenanceRequest(@PathVariable Long id, @RequestBody MaintenanceRequest request) {
        try {
            MaintenanceRequest updatedRequest = maintenanceRequestService.updateMaintenanceRequest(id, request);
            return new ResponseEntity<>(updatedRequest, HttpStatus.OK); // Return updated request
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Handle invalid ID
        }
    }

    // Endpoint to delete a maintenance request by ID

    @DeleteMapping("/{id}") 
    public ResponseEntity<HttpStatus> deleteMaintenanceRequest(@PathVariable Long id) {
        try {
            maintenanceRequestService.deleteMaintenanceRequest(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return success (204)
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Handle server error
        }
    }

    // Endpoint to upload a maintenance request with an image
    @PostMapping("/upload") 
    public ResponseEntity<?> uploadMaintenanceRequest(
            @RequestParam("image") MultipartFile file, // Image file for the request
            @RequestParam("tenantId") Long tenantId, // Tenant ID for the request
            @RequestParam("unitId") Long unitId, // Unit ID for the request
            @RequestParam("description") String description, // Description of the issue
            @RequestParam("requestDate") String requestDate) { // Date of the request
        try {
            MaintenanceRequest maintenanceRequest = maintenanceRequestService
                    .createMaintenanceRequest(file, tenantId, unitId, description, requestDate);
            return ResponseEntity.ok(maintenanceRequest); // Return created request
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Handle errors
        }
    }
}