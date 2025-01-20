package com.dormmatev2.dormmatev2.service;

import com.dormmatev2.dormmatev2.model.MaintenanceRequest;
import com.dormmatev2.dormmatev2.model.Unit;
import com.dormmatev2.dormmatev2.model.User;
import com.dormmatev2.dormmatev2.repositories.MaintenanceRequestRepository;
import com.dormmatev2.dormmatev2.repositories.UnitRepository;
import com.dormmatev2.dormmatev2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceRequestService {

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
}