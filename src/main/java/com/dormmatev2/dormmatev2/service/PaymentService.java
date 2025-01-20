package com.dormmatev2.dormmatev2.service;

import com.dormmatev2.dormmatev2.model.Payment;
import com.dormmatev2.dormmatev2.model.User;
import com.dormmatev2.dormmatev2.repositories.PaymentRepository;
import com.dormmatev2.dormmatev2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository; // For fetching tenant

    public Payment savePayment(Payment payment, Long tenantId) {
         // Fetch the associated Tenant
          User tenant = userRepository.findById(tenantId)
              .orElseThrow(() -> new IllegalArgumentException("Tenant not found with id: " + tenantId));
            payment.setTenant(tenant);
          
        return paymentRepository.save(payment);
    }

    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> findPaymentById(Long id) {
       return paymentRepository.findById(id);
    }
       public List<Payment> getPaymentsByTenantId(Long tenantId) {
        return paymentRepository.findByTenant_UserId(tenantId);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    public Payment addProofOfPayment(Long tenantId, String proofOfPayment){
        Payment existingPayment = paymentRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found with id: " + tenantId));
        existingPayment.setProofOfPayment(proofOfPayment);
        return paymentRepository.save(existingPayment);
    }
    // Add other methods as needed for your application
}