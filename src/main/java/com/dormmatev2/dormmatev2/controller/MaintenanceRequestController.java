package com.dormmatev2.dormmatev2.controller;

import com.dormmatev2.dormmatev2.model.MaintenanceRequest;
import com.dormmatev2.dormmatev2.service.MaintenanceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/maintenance-requests")
public class MaintenanceRequestController {

    @Autowired
    private MaintenanceRequestService maintenanceRequestService;

    @PostMapping
    public ResponseEntity<MaintenanceRequest> createMaintenanceRequest(@RequestParam Long tenantId, @RequestParam Long unitId, @RequestBody MaintenanceRequest maintenanceRequest) {
        try {
          MaintenanceRequest savedRequest = maintenanceRequestService.saveMaintenanceRequest(maintenanceRequest, tenantId, unitId);
            return new ResponseEntity<>(savedRequest, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
           return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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
}