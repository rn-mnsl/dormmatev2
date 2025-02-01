package com.dormmatev2.dormmatev2.repositories;

// Import necessary classes and interfaces
import com.dormmatev2.dormmatev2.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// @Repository annotation indicates that this interface is a Spring Data Repository.
@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

}

// This interface extends JpaRepository, which provides CRUD (Create, Read, Update, Delete) operations. 

// The JpaRepository interface takes two parameters:
// The entity type (Announcement) that this repository will manage.
// The type of the primary key (Long) of the entity.

// It is extending the JPARepository to inherit methods like, (save, findbyId, findAll) and many more methods. 

// This is repetitive and time-consuming. By using an interface, Spring Data JPA handles all of this for you.