package acme.repositories;

import java.time.LocalDateTime;
import java.util.Collection;

import acme.entities.announcement.Announcement;

public interface AnnouncementRepository extends GenericJpaRepository<Announcement>{

	Collection<Announcement> findByCreationDateAfter(LocalDateTime date);
}
