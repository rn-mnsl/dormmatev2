package com.dormmatev2.dormmatev2.repositories;

import com.dormmatev2.dormmatev2.model.Payment;
import com.dormmatev2.dormmatev2.model.Tenant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByTenant(Tenant tenant);
}