package com.dormmatev2.dormmatev2.model;

// Add all here the necessary imports 
import java.util.List;

// Used to prevent circular reference in generating JSON 
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Jakarta Persistence API - manages relational data in java applications and persist data between object and database 
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/*
    This represents the units within the dormitory system. 
    Each unit can have multiple tenants and multiple maintenance requests. 
    This demonstrates encapsulation as we are creating private variables and declarting setters and getters to acces these variables. 
*/

@Entity
@Table(name = "units")
public class Unit {

    // This creates a auto-generated unique ID for the unitID 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unitId;

    // Required unit name 
    @Column(nullable = false)
    private String unitName;

    // Optional description field for the unit 
    @Column
    private String description;

    // one-to-many relationship of the unit to tenants
    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("unit")
    private List<User> tenants;

    // one-to-many relationship of the units to maintenance requests sent by the tenant
    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"unit", "tenant"})
    private List<MaintenanceRequest> maintenanceRequests;

    // Getters and setters for accessing the updating the unit properties 

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