package com.dormmatev2.dormmatev2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unitId;

    @Column(nullable = false)
    private String unitName;

    @Column
    private String description;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("unit")
    private List<Tenant> tenants;

    // @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    // @JsonIgnoreProperties("unit")
    // private List<MaintenanceRequest> maintenanceRequests;

    // Getters and setters

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants) {
        this.tenants = tenants;
    }

    // public List<MaintenanceRequest> getMaintenanceRequests() {
    //     return maintenanceRequests;
    // }

    // public void setMaintenanceRequests(List<MaintenanceRequest> maintenanceRequests) {
    //     this.maintenanceRequests = maintenanceRequests;
    // }
}