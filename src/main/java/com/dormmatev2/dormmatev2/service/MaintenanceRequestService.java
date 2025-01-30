package com.dormmatev2.dormmatev2.service;

import com.dormmatev2.dormmatev2.model.MaintenanceRequest;
import com.dormmatev2.dormmatev2.model.Unit;
import com.dormmatev2.dormmatev2.model.User;
import com.dormmatev2.dormmatev2.repositories.MaintenanceRequestRepository;
import com.dormmatev2.dormmatev2.repositories.UnitRepository;
import com.dormmatev2.dormmatev2.repositories.UserRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MaintenanceRequestService {

    @Value("${upload.path}") // Configure this in application.properties
    String uploadPath;

    @Autowired
    private MaintenanceRequestRepository maintenanceRequestRepository;
    @Autowired
    private UnitRepository unitRepository;
     @Autowired
    private UserRepository userRepository; // For fetching tenant


  public MaintenanceRequest saveMaintenanceRequest(MaintenanceRequest maintenanceRequest, Long tenantId, Long unitId) {
        // Fetch the associated Tenant
        User tenant = userRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with id: " + tenantId));
        maintenanceRequest.setTenant(tenant);

        // Fetch the associated Unit
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new IllegalArgumentException("Unit not found with id: " + unitId));
       maintenanceRequest.setUnit(unit);


        return maintenanceRequestRepository.save(maintenanceRequest);
    }


  public List<MaintenanceRequest> findAllMaintenanceRequests() {
        return maintenanceRequestRepository.findAll();
    }

    public Optional<MaintenanceRequest> findMaintenanceRequestById(Long id) {
        return maintenanceRequestRepository.findById(id);
    }

    public void deleteMaintenanceRequest(Long id) {
       maintenanceRequestRepository.deleteById(id);
    }

     public MaintenanceRequest updateMaintenanceRequest(Long id, MaintenanceRequest requestDetails) {
        MaintenanceRequest existingRequest = maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Maintenance request not found with id: " + id));

         if (requestDetails.getDescription() != null && !requestDetails.getDescription().isEmpty()) {
           existingRequest.setDescription(requestDetails.getDescription());
         }

        if (requestDetails.getStatus() != null && !requestDetails.getStatus().isEmpty()) {
          existingRequest.setStatus(requestDetails.getStatus());
        }

    return maintenanceRequestRepository.save(existingRequest);
    }
   
    // Add other methods as needed for your application
    public List<MaintenanceRequest> findMaintenanceRequestsByTenantId(Long tenantId) {
      return maintenanceRequestRepository.findByTenant_UserId(tenantId);
    }

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