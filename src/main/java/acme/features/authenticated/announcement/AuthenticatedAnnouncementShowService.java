package acme.features.authenticated.announcement;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.announcement.Announcement;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.roles.Authenticated;
import acme.framework.services.AbstractShowService;
import acme.repositories.AnnouncementRepository;

@Service
public class AuthenticatedAnnouncementShowService implements AbstractShowService<Authenticated,Announcement>{

	@Autowired
	protected AnnouncementRepository repo;
	
	@Override
	public boolean authorise(final Request<Announcement> request) {
		return !PrincipalHelper.get().isAnonymous();
	}

	@Override
	public Announcement findOne(final Request<Announcement> request) {
		final Optional<Announcement> res = this.repo.findById(request.getModel().getInteger("id"));
		if(res.isPresent()) {
			return res.get();
		}else {
			return null;
		}
	}

	@Override
	public void unbind(final Request<Announcement> request, final Announcement entity, final Model model) {
		request.unbind(entity, model, "title", "moment", "body", "critical", "link");
	}

}
