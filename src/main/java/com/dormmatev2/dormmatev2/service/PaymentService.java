package com.dormmatev2.dormmatev2.service;

// Import necessary classes and interface

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dormmatev2.dormmatev2.model.Payment;
import com.dormmatev2.dormmatev2.model.User;
import com.dormmatev2.dormmatev2.repositories.PaymentRepository;
import com.dormmatev2.dormmatev2.repositories.UserRepository;

// This indicates that this is a service 
@Service
public class PaymentService {

    @Value("${upload.path}") // Configure this in application.properties
    private String uploadPath;

    // inject the payment repository to this class. 
    @Autowired
    private PaymentRepository paymentRepository;

    // inject the user repository to this class for fetching the tenant 
    @Autowired
    private UserRepository userRepository; 


    // method to save the payment to the database. 
    public Payment savePayment(Payment payment, Long tenantId) {

         // Fetch the associated Tenant
          User tenant = userRepository.findById(tenantId)
              .orElseThrow(() -> new IllegalArgumentException("Tenant not found with id: " + tenantId));
            payment.setTenant(tenant);
          
            // save the payment to the database 
        return paymentRepository.save(payment);
    }

    // method to find all the payments in the database 
    public List<Payment> findAllPayments() {

        // fetch and display all of the units in the database 
        return paymentRepository.findAll();
    }

    // method to find the payment using the id 
    public Optional<Payment> findPaymentById(Long id) {

        // fetch and display the payment using the id (useful for tenant side rendering)
       return paymentRepository.findById(id);
    }

       public List<Payment> getPaymentsByTenantId(Long tenantId) {
        return paymentRepository.findByTenant_UserId(tenantId);
    }

    // method to delete the payment of the user. 
    public void deletePayment(Long id) {

        // method to delete the payment 
        paymentRepository.deleteById(id);
    }

    // method to update the payment. 
    public Payment updatePayment(Long id, Payment paymentDetails) throws Exception {
        // Find the existing payment by ID
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new Exception("Payment not found with id: " + id));
    
        if (paymentDetails.getStatus() != null && !paymentDetails.getStatus().isEmpty()) {
            existingPayment.setStatus(paymentDetails.getStatus().toUpperCase());
        }
        // Save and return the updated payment
        return paymentRepository.save(existingPayment);
    }

    // method to create the payment with file upload feature. 
    public Payment createPayment(MultipartFile file, Long tenantId, Double amount, String paymentDate, String dueDate) throws Exception {
        // Create uploads directory if it doesn't exist
        Path uploadDir = Paths.get(uploadPath).toAbsolutePath().normalize();
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // Generate unique filename
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = uploadDir.resolve(filename);

        // Save file to the upload directory
        Files.copy(file.getInputStream(), filePath);

        // Create payment record
        Payment payment = new Payment();
        User tenant = userRepository.findById(tenantId)
            .orElseThrow(() -> new Exception("Tenant not found"));

        payment.setTenant(tenant);
        payment.setAmount(amount);
        payment.setPaymentDate(LocalDate.parse(paymentDate));
        payment.setDueDate(LocalDate.parse(dueDate));

        // Store the full URL instead of just the filename
        String fileUrl = "http://localhost:8080/files/" + filename; // Use the endpoint you created
        payment.setProofOfPayment(fileUrl);

        payment.setStatus("PENDING");

        // save the payment of the user. 
        return paymentRepository.save(payment);
    }

}