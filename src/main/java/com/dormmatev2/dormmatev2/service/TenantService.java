package com.dormmatev2.dormmatev2.service;

import com.dormmatev2.dormmatev2.model.Tenant;
import com.dormmatev2.dormmatev2.model.Unit;
import com.dormmatev2.dormmatev2.repositories.TenantRepository;
import com.dormmatev2.dormmatev2.repositories.UnitRepository;
import com.dormmatev2.dormmatev2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private UserRepository userRepository;


    public Tenant saveTenant(Tenant tenant,  Long unitId) {
        // Fetch the Unit entity
         Unit unit = unitRepository.findById(unitId)
                 .orElseThrow(() -> new IllegalArgumentException("Unit not found with id: " + unitId));
         tenant.setUnit(unit);
 
         return tenantRepository.save(tenant);
     
    }
    public List<Tenant> findAllTenants() {
        return tenantRepository.findAll();
    }

    public Optional<Tenant> findTenantById(Long id) {
        return tenantRepository.findById(id);
    }

    public void deleteTenant(Long id) {
        tenantRepository.deleteById(id);
    }

    public Tenant updateTenant(Long id, Tenant tenantDetails) {
        Tenant existingTenant = tenantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with id: " + id));

        // Update fields only if they are provided in the request
        if (tenantDetails.getName() != null && !tenantDetails.getName().isEmpty()) {
            existingTenant.setName(tenantDetails.getName());
        }

        if (tenantDetails.getContactNumber() != null && !tenantDetails.getContactNumber().isEmpty()) {
            existingTenant.setContactNumber(tenantDetails.getContactNumber());
        }

        if (tenantDetails.getMoveInDate() != null) {
            existingTenant.setMoveInDate(tenantDetails.getMoveInDate());
        }

        // You might need to handle updating the associated User and Unit separately,
        // depending on your requirements.

        return tenantRepository.save(existingTenant);
    }
      public Optional<Tenant> findTenantByUsername(String username) {
        return tenantRepository.findByUser_Username(username);
    }
    
    // Add other methods as needed for your application
}