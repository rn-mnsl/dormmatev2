package com.dormmatev2.dormmatev2.repositories;

import com.dormmatev2.dormmatev2.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
