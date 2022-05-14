
package acme.features.authenticated.announcement;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.announcement.Announcement;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Authenticated;
import acme.framework.services.AbstractShowService;
import acme.repositories.AnnouncementRepository;
import acme.services.AbstractAcmeAuthoriseAllService;

@Service
public class AuthenticatedAnnouncementShowService extends AbstractAcmeAuthoriseAllService<Authenticated, Announcement, AnnouncementRepository> implements AbstractShowService<Authenticated, Announcement> {

	@Autowired
	protected AuthenticatedAnnouncementShowService(final AnnouncementRepository repo, final ModelMapper mapper) {
		super(repo, mapper);
	}

	@Override
	public Announcement findOne(final Request<Announcement> request) {
		return this.findById(request);
	}

	@Override
	public void unbind(final Request<Announcement> request, final Announcement entity, final Model model) {
		request.unbind(entity, model, "title", "moment", "body", "critical", "link");
	}

}
