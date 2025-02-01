package com.dormmatev2.dormmatev2.repositories;

// Import necessary classes and interfaces
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dormmatev2.dormmatev2.model.Unit;

// @Repository annotation indicates that this interface is a Spring Data Repository.

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
}

// This interface extends JpaRepository, which provides CRUD (Create, Read, Update, Delete) operations. 

// The JpaRepository interface takes two parameters:
// The entity type (Payments) that this repository will manage.
// The type of the primary key (Unit) of the entity.

// It is extending the JPARepository to inherit methods like, (save, findbyId, findAll) and many more methods. 