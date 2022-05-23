
package acme.features.authenticated.announcement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.announcement.Announcement;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Authenticated;
import acme.framework.services.AbstractShowService;
import acme.services.AbstractAuthoriseAll;
import acme.services.announcement.AnnouncementService;

@Service
public class AuthenticatedAnnouncementShowService extends AbstractAuthoriseAll<Authenticated, Announcement> implements AbstractShowService<Authenticated, Announcement> {

	@Autowired
	protected AnnouncementService service;

	@Override
	@Transactional
	public Announcement findOne(final Request<Announcement> request) {
		return this.service.findById(request);
	}

	@Override
	@Transactional
	public void unbind(final Request<Announcement> request, final Announcement entity, final Model model) {
		request.unbind(entity, model, "title", "creationDate", "body", "critical", "link");
	}

}
