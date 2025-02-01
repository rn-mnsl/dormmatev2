package com.dormmatev2.dormmatev2.repositories;

// Import necessary classes and interfaces
import com.dormmatev2.dormmatev2.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
// @Repository annotation indicates that this interface is a Spring Data Repository.

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
  List<Payment> findByTenant_UserId(Long userId);
}


// This interface extends JpaRepository, which provides CRUD (Create, Read, Update, Delete) operations. 

// The JpaRepository interface takes two parameters:
// The entity type (Payments) that this repository will manage.
// The type of the primary key (Long) of the entity.

// It is extending the JPARepository to inherit methods like, (save, findbyId, findAll) and many more methods. 

//  we are also creating a new method inside the interface to find the tenant_userID to find using the tenant using the userID
// this is done because we know that only the tenants are the one that posts / upload the maintenance request. 