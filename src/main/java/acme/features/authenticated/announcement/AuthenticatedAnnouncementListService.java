
package acme.features.authenticated.announcement;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.announcement.Announcement;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Authenticated;
import acme.framework.services.AbstractListService;
import acme.repositories.AnnouncementRepository;
import acme.services.AbstractAcmeAuthoriseAllService;

@Service
public class AuthenticatedAnnouncementListService extends AbstractAcmeAuthoriseAllService<Authenticated, Announcement, AnnouncementRepository> implements AbstractListService<Authenticated, Announcement> {

	@Autowired
	protected AuthenticatedAnnouncementListService(final AnnouncementRepository repo, final ModelMapper mapper) {
		super(repo, mapper);
	}

	@Override
	public Collection<Announcement> findMany(final Request<Announcement> request) {
		return this.repo.findByCreationDateAfter(LocalDateTime.now().minus(1, ChronoUnit.MONTHS));
	}

	@Override
	public void unbind(final Request<Announcement> request, final Announcement entity, final Model model) {
		request.unbind(entity, model, "title", "moment", "body");
	}

}
