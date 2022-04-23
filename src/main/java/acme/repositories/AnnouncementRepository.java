package acme.repositories;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import acme.entities.announcement.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement,Integer>{

	Collection<Announcement> findByCreationDateAfter(LocalDateTime date);
}
