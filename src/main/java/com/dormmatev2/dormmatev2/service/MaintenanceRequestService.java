package com.dormmatev2.dormmatev2.service;

import com.dormmatev2.dormmatev2.model.MaintenanceRequest;
import com.dormmatev2.dormmatev2.model.Tenant;
// import com.dormmatev2.dormmatev2.model.Unit;
import com.dormmatev2.dormmatev2.repositories.MaintenanceRequestRepository;
import com.dormmatev2.dormmatev2.repositories.TenantRepository;
// import com.dormmatev2.dormmatev2.repositories.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceRequestService {

    @Autowired
    private MaintenanceRequestRepository maintenanceRequestRepository;

    @Autowired
    private TenantRepository tenantRepository; // For associating requests with tenants

    public MaintenanceRequest saveMaintenanceRequest(MaintenanceRequest maintenanceRequest, Long tenantId, Long unitId) {
        // Fetch the associated Tenant
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with id: " + tenantId));
        maintenanceRequest.setTenant(tenant);

        // Fetch the associated Unit
        // Unit unit = unitRepository.findById(unitId)
        //         .orElseThrow(() -> new IllegalArgumentException("Unit not found with id: " + unitId));
        // maintenanceRequest.setUnit(unit);

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

        // Update fields only if they are provided in the request
        if (requestDetails.getDescription() != null && !requestDetails.getDescription().isEmpty()) {
            existingRequest.setDescription(requestDetails.getDescription());
        }

        if (requestDetails.getStatus() != null && !requestDetails.getStatus().isEmpty()) {
            existingRequest.setStatus(requestDetails.getStatus());
        }

        // You might not want to allow changing the tenant or unit associated with a request,
        // so I'm not including that in the update logic.

        return maintenanceRequestRepository.save(existingRequest);
    }

    // Add other methods as needed for your application
}