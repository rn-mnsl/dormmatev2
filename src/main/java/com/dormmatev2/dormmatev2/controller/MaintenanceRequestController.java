package com.dormmatev2.dormmatev2.controller;

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
@RequestMapping("/api/maintenance-requests")
public class MaintenanceRequestController {

    @Autowired
    private MaintenanceRequestService maintenanceRequestService;

    @PostMapping
    public ResponseEntity<MaintenanceRequest> createMaintenanceRequest(
            @RequestParam Long tenantId,
            @RequestParam Long unitId,
            @RequestParam("description") String description,
            @RequestParam(value = "proofOfDamage", required = false) MultipartFile proofOfDamage) {
        try {
            MaintenanceRequest maintenanceRequest = new MaintenanceRequest();
            maintenanceRequest.setDescription(description);
            // Handle the optional file upload
            if (proofOfDamage != null && !proofOfDamage.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + proofOfDamage.getOriginalFilename();
                Path filePath = Paths.get("upload/" + fileName);
                Files.write(filePath, proofOfDamage.getBytes());
                maintenanceRequest.setProofOfDamage(fileName);
            } else {
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

    @GetMapping
    public ResponseEntity<List<MaintenanceRequest>> getAllMaintenanceRequests() {
         List<MaintenanceRequest> requests = maintenanceRequestService.findAllMaintenanceRequests();
         return new ResponseEntity<>(requests, HttpStatus.OK);
     }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceRequest> getMaintenanceRequestById(@PathVariable Long id) {
        Optional<MaintenanceRequest> request = maintenanceRequestService.findMaintenanceRequestById(id);
       return request.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
   }

   @PutMapping("/{id}")
    public ResponseEntity<MaintenanceRequest> updateMaintenanceRequest(@PathVariable Long id, @RequestBody MaintenanceRequest request) {
        try {
          MaintenanceRequest updatedRequest = maintenanceRequestService.updateMaintenanceRequest(id, request);
         return new ResponseEntity<>(updatedRequest, HttpStatus.OK);
       } catch (IllegalArgumentException e) {
           return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
       }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMaintenanceRequest(@PathVariable Long id) {
      try {
            maintenanceRequestService.deleteMaintenanceRequest(id);
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadMaintenanceRequest(
            @RequestParam("image") MultipartFile file,
            @RequestParam("tenantId") Long tenantId,
            @RequestParam("unitId") Long unitId,
            @RequestParam("description") String description,
            @RequestParam("requestDate") String requestDate) {
        try {
            MaintenanceRequest maintenanceRequest = maintenanceRequestService
                .createMaintenanceRequest(file, tenantId, unitId, description, requestDate);
            return ResponseEntity.ok(maintenanceRequest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}