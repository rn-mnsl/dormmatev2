package com.dormmatev2.dormmatev2.service;

// Import necessary classes and interface
import com.dormmatev2.dormmatev2.model.Announcement;
import com.dormmatev2.dormmatev2.model.User;
import com.dormmatev2.dormmatev2.repositories.AnnouncementRepository;
import com.dormmatev2.dormmatev2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// This indicates that this is a service 
@Service
public class AnnouncementService {

    // this annotation injects the announcementrepository to this class 
    // This allows us to use the repository to interact with the database.
    @Autowired
    private AnnouncementRepository announcementRepository;
    
    // this injects the UserRepository to this class. 
    // allows to use the user details when creating or updating announcements. 
    @Autowired
      private UserRepository userRepository;

      // method to save a new announcement and associate it to a user. 
    public Announcement saveAnnouncement(Announcement announcement, Long userId) {
         
        // Fetch the UserUD who is posting the announcement
         User user = userRepository.findById(userId)
                 .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        // Link the announcement to the User that posted it. 
        announcement.setPostedBy(user); 

        // save the announcement to the database using the repository
        return announcementRepository.save(announcement);
    }

    // fetch all the announcements from the database 
    public List<Announcement> findAllAnnouncements() {

        // use the repository findAll() method to retrieve all announcements
       return announcementRepository.findAll();
    }

    // find a specific annonucement using it's ID 
    public Optional<Announcement> findAnnouncementById(Long id) {

        // use the repository findById to find the announcement. The result is because an announcement may not exist. 
        return announcementRepository.findById(id);
    }


    // method to delete an existing announcement. Used by the admin. 
    public void deleteAnnouncement(Long id) {

        // use the repository deletebyID to remove the announcement from the database 
        announcementRepository.deleteById(id);
    }

    // method to upate an existing announcement 
  public Announcement updateAnnouncement(Long id, Announcement announcementDetails) {
        // fetch the existing announcement using the ID. If it is not found, throw an exception handling. 
        Announcement existingAnnouncement = announcementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found with id: " + id));
        
        // update the announcement if a new title is provided in the announcement details. 
        if (announcementDetails.getTitle() != null && !announcementDetails.getTitle().isEmpty()) {
            existingAnnouncement.setTitle(announcementDetails.getTitle());
        }

        // update the announcement's content if the new content is provide in the announcement details. 
       if (announcementDetails.getContent() != null && !announcementDetails.getContent().isEmpty()) {
          existingAnnouncement.setContent(announcementDetails.getContent());
         }
         
         // save the updated announcement back to the database. 
        return announcementRepository.save(existingAnnouncement);
    }
}