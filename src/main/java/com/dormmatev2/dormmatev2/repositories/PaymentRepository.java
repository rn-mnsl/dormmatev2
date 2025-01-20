package com.dormmatev2.dormmatev2.repositories;

import com.dormmatev2.dormmatev2.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
  List<Payment> findByTenant_UserId(Long userId);
}