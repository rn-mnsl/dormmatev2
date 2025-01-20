package com.dormmatev2.dormmatev2.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "units")
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
    private List<User> tenants;


    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"unit", "tenant"})
    private List<MaintenanceRequest> maintenanceRequests;

    // Getters and setters (You'll generate these in the next step)
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

        public List<User> getTenants() {
            return tenants;
        }

        public void setTenants(List<User> tenants) {
            this.tenants = tenants;
        }

         public List<MaintenanceRequest> getMaintenanceRequests() {
             return maintenanceRequests;
         }

        public void setMaintenanceRequests(List<MaintenanceRequest> maintenanceRequests) {
            this.maintenanceRequests = maintenanceRequests;
        }
}