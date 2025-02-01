package com.dormmatev2.dormmatev2.model;

// add here all the necessary imports 
import java.time.LocalDate;

// manages relational data in java applications and persist data between object and database 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// used to prevent circular reference in generating JSON 
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
    This is a model for the payments within the dormitory system. 
    Each payment is associated to one tenant, and a tenant can have multiple payments. 
    This demonstrates encapsulation as we are creating private variables and declarting setters and getters to acces these variables. 
*/
@Entity
@Table(name = "payments")
public class Payment {

    // creates auto-generated paymentID to prevent repeating of primary key 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    // many-to-one relationship of the payments to the tenants 
    @ManyToOne
    @JoinColumn(name = "tenant_id") // this is to join the column of tenant and payments
    @JsonIgnoreProperties({"payments", "maintenanceRequests","unit"})
    private User tenant;

    // required amount paid
    @Column(nullable = false)
    private Double amount;

    // optional payment date 
    @Column
    private LocalDate paymentDate;

    // required due date 
    @Column(nullable = false)
    private LocalDate dueDate;
      
    @Column (nullable = false)
    private int month;

    @Column (nullable = false)
    private int year;

    // required status of the payment 
    @Column(nullable = false)
    private String status; // "pending", "paid", "overdue"

    // store the URL of the proof of payment 
    @Column(length = 1000)
    private String proofOfPayment; // Consider storing file paths or URLs

    // Getters and setters for  all the attributes 

     public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public User getTenant() {
        return tenant;
    }

    public void setTenant(User tenant) {
        this.tenant = tenant;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProofOfPayment() {
        return proofOfPayment;
    }

    public void setProofOfPayment(String proofOfPayment) {
        this.proofOfPayment = proofOfPayment;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}