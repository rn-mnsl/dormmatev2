package com.dormmatev2.dormmatev2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class MaintenanceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    @JsonIgnoreProperties({"payments", "maintenanceRequests"})
    private Tenant tenant;

    // @ManyToOne
    // @JoinColumn(name = "unit_id")
    // @JsonIgnoreProperties({"tenants", "maintenanceRequests"})
    // private Unit unit;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime requestDate = LocalDateTime.now();

    @Column(nullable = false)
    private String status; // "open", "in progress", "resolved"

    @Column(nullable = false) 
    private String unit; // enter the unit here 
    // Getters and setters

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    // public Unit getUnit() {
    //     return unit;
    // }

    // public void setUnit(Unit unit) {
    //     this.unit = unit;
    // }

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

    public void setUnit(String unit){
        this.unit = unit; 
    }

    public String getUnit(){
        return unit;
    }
}