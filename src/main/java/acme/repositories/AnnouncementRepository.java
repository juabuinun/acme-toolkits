package acme.repositories;

import java.time.LocalDateTime;
import java.util.List;

import acme.entities.announcement.Announcement;

public interface AnnouncementRepository extends GenericJpaRepository<Announcement>{

	List<Announcement> findByCreationDateAfter(LocalDateTime date);
}
