package acme.features.authenticated.announcement;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.announcement.Announcement;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.roles.Authenticated;
import acme.framework.services.AbstractListService;
import acme.repositories.AnnouncementRepository;

@Service
public class AuthenticatedAnnouncementListService implements AbstractListService<Authenticated,Announcement>{

	@Autowired
	protected AnnouncementRepository repo;
	
	@Override
	public boolean authorise(final Request<Announcement> request) {
		return !PrincipalHelper.get().isAnonymous();
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
