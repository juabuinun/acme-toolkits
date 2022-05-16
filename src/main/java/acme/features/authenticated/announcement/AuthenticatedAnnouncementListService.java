
package acme.features.authenticated.announcement;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.announcement.Announcement;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Authenticated;
import acme.framework.services.AbstractListService;
import acme.services.AuthoriseAll;
import acme.services.announcement.AnnouncementService;

@Service
public class AuthenticatedAnnouncementListService extends AuthoriseAll<Authenticated, Announcement> implements AbstractListService<Authenticated, Announcement> {

	@Autowired
	protected AnnouncementService service;

	@Override
	@Transactional
	public Collection<Announcement> findMany(final Request<Announcement> request) {
		return this.service.findRecent();
	}

	@Override
	@Transactional
	public void unbind(final Request<Announcement> request, final Announcement entity, final Model model) {
		request.unbind(entity, model, "title", "moment", "body");
	}

}
