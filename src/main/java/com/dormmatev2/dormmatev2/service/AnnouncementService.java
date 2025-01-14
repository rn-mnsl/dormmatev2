package com.dormmatev2.dormmatev2.service;

import com.dormmatev2.dormmatev2.model.Announcement;
import com.dormmatev2.dormmatev2.model.User;
import com.dormmatev2.dormmatev2.repositories.AnnouncementRepository;
import com.dormmatev2.dormmatev2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private UserRepository userRepository; // Required for associating announcements with users

    public Announcement saveAnnouncement(Announcement announcement, Long userId) {
        // Fetch the User who is posting the announcement
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        announcement.setPostedBy(user);

        return announcementRepository.save(announcement);
    }

    public List<Announcement> findAllAnnouncements() {
        return announcementRepository.findAll();
    }

    public Optional<Announcement> findAnnouncementById(Long id) {
        return announcementRepository.findById(id);
    }

    public void deleteAnnouncement(Long id) {
        announcementRepository.deleteById(id);
    }

    public Announcement updateAnnouncement(Long id, Announcement announcementDetails) {
        Announcement existingAnnouncement = announcementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found with id: " + id));

        // Update fields only if they are provided in the request
        if (announcementDetails.getTitle() != null && !announcementDetails.getTitle().isEmpty()) {
            existingAnnouncement.setTitle(announcementDetails.getTitle());
        }

        if (announcementDetails.getContent() != null && !announcementDetails.getContent().isEmpty()) {
            existingAnnouncement.setContent(announcementDetails.getContent());
        }

        // You might not want to allow changing the user who posted the announcement,
        // so I'm not including that in the update logic.

        return announcementRepository.save(existingAnnouncement);
    }

    // Add other methods as needed for your application
}
