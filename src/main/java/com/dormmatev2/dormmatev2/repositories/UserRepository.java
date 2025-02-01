package com.dormmatev2.dormmatev2.repositories;

// Import necessary classes and interfaces
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dormmatev2.dormmatev2.model.User;

// @Repository annotation indicates that this interface is a Spring Data Repository.

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}

// This interface extends JpaRepository, which provides CRUD (Create, Read, Update, Delete) operations. 

// The JpaRepository interface takes two parameters:
// The entity type (User) that this repository will manage.
// The type of the primary key (Long) of the entity.

// It is extending the JPARepository to inherit methods like, (save, findbyId, findAll) and many more methods. 

// we are also creating two new methods the findByUsername and findByEmail 