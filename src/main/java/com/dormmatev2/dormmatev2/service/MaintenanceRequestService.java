package com.dormmatev2.dormmatev2.service;

// Import necessary classes and interface
import com.dormmatev2.dormmatev2.model.MaintenanceRequest;
import com.dormmatev2.dormmatev2.model.Unit;
import com.dormmatev2.dormmatev2.model.User;
import com.dormmatev2.dormmatev2.repositories.MaintenanceRequestRepository;
import com.dormmatev2.dormmatev2.repositories.UnitRepository;
import com.dormmatev2.dormmatev2.repositories.UserRepository;


// imports necessary for saving the images in the databases. 
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

// This indicates that this is a service 
@Service
public class MaintenanceRequestService {

    @Value("${upload.path}") // Configure this in application.properties
    String uploadPath;

    // this injects the Maintenancce request repository to this class. 
    @Autowired
    private MaintenanceRequestRepository maintenanceRequestRepository;

    // this injects the unit repository to this class. 
    @Autowired
    private UnitRepository unitRepository;

    // this injects the user  repository to this class. 
    @Autowired
    private UserRepository userRepository; // For fetching tenant

    // method to save a new maintenance request and then associate it with a user which in this case is a tenant and a unit. 
    public MaintenanceRequest saveMaintenanceRequest(MaintenanceRequest maintenanceRequest, Long tenantId, Long unitId) {
        // Fetch the associated Tenant
        User tenant = userRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with id: " + tenantId));
        maintenanceRequest.setTenant(tenant);

        // Fetch the associated Unit
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new IllegalArgumentException("Unit not found with id: " + unitId));
       maintenanceRequest.setUnit(unit);

      // store the maintenance request to the database. 
      return maintenanceRequestRepository.save(maintenanceRequest);
    }

    // method to getch all of the maintenance request present 
    public List<MaintenanceRequest> findAllMaintenanceRequests() {

        // display all of the existing maintenance request. 
        return maintenanceRequestRepository.findAll();
    }

    // method to find the maintenance request by id, to display it with the associate tenant
    public Optional<MaintenanceRequest> findMaintenanceRequestById(Long id) {

        // display the maintenancce request by ID 
        return maintenanceRequestRepository.findById(id);
    }

    // method to delete the maintenance request 
    public void deleteMaintenanceRequest(Long id) {

        // delete the maintenance request from the database 
       maintenanceRequestRepository.deleteById(id);
    }

    // method to update the existing data for the maintenance request 
     public MaintenanceRequest updateMaintenanceRequest(Long id, MaintenanceRequest requestDetails) {
        MaintenanceRequest existingRequest = maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Maintenance request not found with id: " + id));

         if (requestDetails.getDescription() != null && !requestDetails.getDescription().isEmpty()) {
           existingRequest.setDescription(requestDetails.getDescription());
         }

        if (requestDetails.getStatus() != null && !requestDetails.getStatus().isEmpty()) {
          existingRequest.setStatus(requestDetails.getStatus());
        }
      
        // save the updated request to the database 
    return maintenanceRequestRepository.save(existingRequest);
    }

    // find the request using the tenantID
   
    public List<MaintenanceRequest> findMaintenanceRequestsByTenantId(Long tenantId) {
      return maintenanceRequestRepository.findByTenant_UserId(tenantId);
    }

    // create a maintenance request with the proof of damage 
    public MaintenanceRequest createMaintenanceRequest(MultipartFile file, Long tenantId, Long unitId, 
        String description, String requestDate) throws Exception {

    // Create uploads directory if it doesn't exist
    Path uploadDir = Paths.get(uploadPath).toAbsolutePath().normalize();
    if (!Files.exists(uploadDir)) {
        Files.createDirectories(uploadDir);
    }

    // Generate unique filename

    String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
    Path filePath = uploadDir.resolve(filename);

    // Save file to the upload directory
    Files.copy(file.getInputStream(), filePath);

    // Create maintenance request record
    MaintenanceRequest maintenanceRequest = new MaintenanceRequest();
    
    // Fetch and validate tenant
    User tenant = userRepository.findById(tenantId)
        .orElseThrow(() -> new Exception("Tenant not found"));
    
    // Fetch and validate unit
    Unit unit = unitRepository.findById(unitId)
        .orElseThrow(() -> new Exception("Unit not found"));

    maintenanceRequest.setTenant(tenant);
    maintenanceRequest.setUnit(unit);
    maintenanceRequest.setDescription(description);

    // Parse the date string
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    LocalDateTime parsedDate = LocalDateTime.parse(requestDate, formatter);
    maintenanceRequest.setRequestDate(parsedDate);

    
    // Store the full URL for the uploaded image
    String fileUrl = "http://localhost:8080/files/" + filename;
    maintenanceRequest.setProofOfDamage(fileUrl);

    // Set initial status as PENDING
    maintenanceRequest.setStatus("PENDING");

    return maintenanceRequestRepository.save(maintenanceRequest);
}
}