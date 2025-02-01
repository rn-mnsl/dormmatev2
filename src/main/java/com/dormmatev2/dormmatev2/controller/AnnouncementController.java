package com.dormmatev2.dormmatev2.controller;

import com.dormmatev2.dormmatev2.model.Announcement;
import com.dormmatev2.dormmatev2.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class for handling announcement-related HTTP requests.
 * This class provides RESTful endpoints for creating, retrieving, updating, and deleting announcements.
 */
@RestController
@RequestMapping("/api/announcements") // Base URL for all endpoints in this controller
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService; // Service layer dependency for announcement operations

    /**
     * Endpoint to create a new announcement.
     */
    @PostMapping
    public ResponseEntity<Announcement> createAnnouncement(@RequestParam Long userId, @RequestBody Announcement announcement) {
        try {
            Announcement savedAnnouncement = announcementService.saveAnnouncement(announcement, userId);
            return new ResponseEntity<>(savedAnnouncement, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to retrieve all announcements.
     */
    @GetMapping
    public ResponseEntity<List<Announcement>> getAllAnnouncements() {
        List<Announcement> announcements = announcementService.findAllAnnouncements();
        return new ResponseEntity<>(announcements, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a specific announcement by its ID.
     *
     */
    @GetMapping("/{id}")
    public ResponseEntity<Announcement> getAnnouncementById(@PathVariable Long id) {
        Optional<Announcement> announcement = announcementService.findAnnouncementById(id);
        return announcement.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint to update an existing announcement.
     *
     */
    @PutMapping("/{id}")
    public ResponseEntity<Announcement> updateAnnouncement(@PathVariable Long id, @RequestBody Announcement announcement) {
        try {
            Announcement updatedAnnouncement = announcementService.updateAnnouncement(id, announcement);
            return new ResponseEntity<>(updatedAnnouncement, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to delete an announcement by its ID.
     *
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAnnouncement(@PathVariable Long id) {
        try {
            announcementService.deleteAnnouncement(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}