package acme.services.announcement;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.announcement.Announcement;
import acme.repositories.AnnouncementRepository;
import acme.services.AbstractCrudServiceImpl;

@Service
public class AnnouncementService extends AbstractCrudServiceImpl<Announcement,AnnouncementRepository>{

	@Autowired
	protected AnnouncementService(final AnnouncementRepository repo, final ModelMapper mapper) {
		super(repo, mapper);
	}

	@Transactional
	public List<Announcement> findRecent(){
		return this.repo.findByCreationDateAfter(LocalDateTime.now().minus(1, ChronoUnit.MONTHS));
	}
}
