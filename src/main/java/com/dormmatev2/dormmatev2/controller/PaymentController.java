package com.dormmatev2.dormmatev2.controller;


// Add all the necessary imports 
import com.dormmatev2.dormmatev2.model.Payment;
import com.dormmatev2.dormmatev2.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments") // Base endpoint for payment-related operations
public class PaymentController {

    @Autowired
    private PaymentService paymentService; // Service for payment operations

    // Endpoint to create a new payment
    @PostMapping 
    public ResponseEntity<Payment> createPayment(@RequestParam Long tenantId, @RequestBody Payment payment) {
        try {
            Payment savedPayment = paymentService.savePayment(payment, tenantId); // Save payment with tenant ID
            return new ResponseEntity<>(savedPayment, HttpStatus.CREATED); 
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); 
        }
    }

    // Endpoint to fetch all payments

    @GetMapping 
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.findAllPayments(); // Retrieve all payments
        return new ResponseEntity<>(payments, HttpStatus.OK); 
    }

    // Endpoint to fetch a payment by ID

    @GetMapping("/{id}") 
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Optional<Payment> payment = paymentService.findPaymentById(id); // Find payment by ID
        return payment.map(value -> new ResponseEntity<>(value, HttpStatus.OK)) 
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); 
    }

    // Endpoint to fetch payments by tenant ID

    @GetMapping("/tenant/{tenantId}") 
    public ResponseEntity<List<Payment>> getPaymentsByTenantId(@PathVariable Long tenantId) {
        try {
            List<Payment> payments = paymentService.getPaymentsByTenantId(tenantId); // Retrieve payments for tenant
            return new ResponseEntity<>(payments, HttpStatus.OK); 
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); 
        }
    }

    // Endpoint to delete a payment by ID

    @DeleteMapping("/{id}") 
    public ResponseEntity<HttpStatus> deletePayment(@PathVariable Long id) {
        try {
            paymentService.deletePayment(id); // Delete payment by ID
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return success (204)
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Handle server error
        }
    }

    // Endpoint to upload a payment with proof of payment

    @PostMapping("/upload") 
    public ResponseEntity<?> uploadPayment(
            @RequestParam("proofOfPayment") MultipartFile file, // Proof of payment file
            @RequestParam("tenantId") Long tenantId, // Tenant ID for the payment
            @RequestParam("amount") Double amount, // Payment amount
            @RequestParam("paymentDate") String paymentDate, // Date of payment
            @RequestParam("dueDate") String dueDate) { // Due date for payment
        try {
            Payment payment = paymentService.createPayment(file, tenantId, amount, paymentDate, dueDate); // Create payment
            return ResponseEntity.ok(payment); // Return created payment
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Handle errors
        }
    }

    // Endpoint to update a payment by ID

    @PutMapping("/{id}") 
    public ResponseEntity<Payment> updatePayment(
            @PathVariable Long id, // Payment ID to update
            @RequestBody Payment payment) throws Exception { // Updated payment details
        try {
            Payment updatedPayment = paymentService.updatePayment(id, payment); // Update payment
            return new ResponseEntity<>(updatedPayment, HttpStatus.OK); // Return updated payment
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Handle invalid ID
        }
    }
}