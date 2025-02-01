package com.dormmatev2.dormmatev2.model;

// add here all the necessary imports 
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/*
    This represents the announcements with the dormitory system. 

    Each announcement is posted by a user (which in this case is only the admin)

    This demonstrates encapsulation as we are creating private variables and declarting setters and getters to acces these variables. 
*/
@Entity
@Table(name = "announcements")
public class Announcement {

    // This creates a auto-generated unique ID for the announcementID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long announcementId;

    // This maps the announcements as they are posted by the user
    @ManyToOne
    @JoinColumn(name = "posted_by")
    @JsonIgnoreProperties({"announcements", "unit", "payments", "maintenanceRequests"})
    private User postedBy; // Link to the User who posted it

    // required title for the announcement 
    @Column(nullable = false)
    private String title;

    // required content of the announcement 
    @Column(nullable = false, length = 1000)
    private String content;

    // required date that the announcement was posted 
    @Column(nullable = false, updatable = false)
    private LocalDateTime postDate = LocalDateTime.now();

    // Getters and setters
   public Long getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Long announcementId) {
        this.announcementId = announcementId;
    }

    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDateTime postDate) {
        this.postDate = postDate;
    }
}