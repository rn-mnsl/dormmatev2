package com.dormmatev2.dormmatev2.model;

// add here all the necessary imports 
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/*
    This represents the maintenance request within the dormitory system
    Each maintenance request is made by a tenant. 
    Each maintenance request is associated to a unit. 
    This demonstrates encapsulation as we are creating private variables and declaring setters and getters to acces these variables. 
*/

@Entity
@Table(name = "maintenance_requests")
public class MaintenanceRequest {

    // This creates a auto-generated unique ID for the requestID 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    // This joins the table of maintenance request and tenants 
    @ManyToOne
    @JoinColumn(name = "tenant_id")
    @JsonIgnoreProperties({"payments", "maintenanceRequests", "unit"})
    private User tenant;

    // This joins the table of the maintenance request and the units 
    @ManyToOne
    @JoinColumn(name = "unit_id")
    @JsonIgnoreProperties({"tenants", "maintenanceRequests"})
    private Unit unit;
    
    // Required description for the maintenance request 
    @Column(nullable = false)
    private String description;

    // Required date of when the maintenance request is posted 
    @Column(nullable = false, updatable = false)
    private LocalDateTime requestDate = LocalDateTime.now();

    // Required status of the request 
    @Column(nullable = false)
    private String status = "open"; // "open", "in progress", "resolved"

    // string to store the link of the proof of damage 
    @Column(length = 1000)
    private String proofOfDamage;

    // Getters and setters
    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public User getTenant() {
        return tenant;
    }

    public void setTenant(User tenant) {
        this.tenant = tenant;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProofOfDamage() {
        return proofOfDamage;
    }

    public void setProofOfDamage(String proofOfDamage) {
        this.proofOfDamage = proofOfDamage;
    }
}