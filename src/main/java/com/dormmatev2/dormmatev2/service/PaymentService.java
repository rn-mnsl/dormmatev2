package com.dormmatev2.dormmatev2.service;

import com.dormmatev2.dormmatev2.model.Payment;
import com.dormmatev2.dormmatev2.model.Tenant;
import com.dormmatev2.dormmatev2.repositories.PaymentRepository;
import com.dormmatev2.dormmatev2.repositories.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TenantRepository tenantRepository; // Required for associating payments with tenants

    public Payment savePayment(Payment payment, Long tenantId) {
        // Fetch the associated Tenant
        Tenant tenant = tenantRepository.findById(tenantId)
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

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
    public List<Payment> getPaymentsByTenantId(Long tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with id: " + tenantId));
        return paymentRepository.findByTenant(tenant);
    }

    // Add other methods as needed for your application
}