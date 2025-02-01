package com.dormmatev2.dormmatev2.model;

// Add all here the necessary imports 

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Table;

/*
    This represents the users within the dormitory system. 

    Each user is associated with one unit and a unit can have multiple tenants.  

    One user can have multiple payments. 

    One user can also have multiple maintenance requests. 

    This demonstrates encapsulation as we are creating private variables and declarting setters and getters to acces these variables. 
*/

@Entity
@Table(name="users")
public class User {

    // This creates a auto-generated unique ID for the userID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    // required username and must be unique 
    @Column(unique = true, nullable = false)
    private String username;

    // required password 
    @Column(nullable = false)
    private String password; // hashed passwords 

    // required email and must be unique 
    @Column(unique = true, nullable = false)
    private String email;

    // required role either admin / tenant 
    @Column(nullable = false)
    private String role; // "admin" or "tenant"

    // created name attribute
    @Column
    private String name;

    // created name attribute
    @Column
    private String contactNumber;

    // created name attribute
    @Column
    private LocalDate moveInDate;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    @JsonIgnoreProperties("tenants")
    private Unit unit;


    @OneToMany(mappedBy = "postedBy", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("postedBy")
    private List<Announcement> announcements;

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"tenant", "payments", "maintenanceRequests", "unit"})
    private List<Payment> payments;

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"tenant", "unit"})
    private List<MaintenanceRequest> maintenanceRequests;

     //Getters and Setters
   public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public LocalDate getMoveInDate() {
        return moveInDate;
    }

    public void setMoveInDate(LocalDate moveInDate) {
        this.moveInDate = moveInDate;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<MaintenanceRequest> getMaintenanceRequests() {
        return maintenanceRequests;
    }

    public void setMaintenanceRequests(List<MaintenanceRequest> maintenanceRequests) {
        this.maintenanceRequests = maintenanceRequests;
    }
}