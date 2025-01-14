package com.dormmatev2.dormmatev2.service;

import com.dormmatev2.dormmatev2.model.Tenant;
import com.dormmatev2.dormmatev2.model.Unit;
import com.dormmatev2.dormmatev2.model.User;
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
    private UserRepository userRepository;

    @Autowired
    private UnitRepository unitRepository;

    public Tenant saveTenant(Tenant tenant, Long userId, Long unitId) {
        // Check if the user already has a tenant profile
        if (tenantRepository.findByUser_Username(tenant.getUser().getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already has a tenant profile");
        }

        // Fetch the User entity
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        tenant.setUser(user);

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