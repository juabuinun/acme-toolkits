package acme.features.administrator.announcement;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.util.BindHelper;
import acme.entities.announcement.Announcement;
import acme.form.announcement.AnnouncementDto;
import acme.form.announcement.SaveAnnouncementDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractCreateService;
import acme.services.AuthoriseAll;
import acme.services.announcement.AnnouncementService;
import acme.services.config.AcmeConfigurationService;

@Service
public class AdministratorAnnouncementCreateService extends AuthoriseAll<Administrator,Announcement> implements AbstractCreateService<Administrator,Announcement>{

	@Autowired
	protected AnnouncementService service;
	
	@Autowired
	protected AcmeConfigurationService configService;
	
	@Override
	public void bind(final Request<Announcement> request, final Announcement entity, final Errors errors) {
		request.bind(entity, errors, BindHelper.getAllFieldNames(SaveAnnouncementDto.class));
		entity.setCreationDate(LocalDateTime.now().minus(1,ChronoUnit.SECONDS));
	}

	@Override
	public void unbind(final Request<Announcement> request, final Announcement entity, final Model model) {
		request.unbind(entity, model, BindHelper.getAllFieldNames(AnnouncementDto.class));
		model.setAttribute("confirmation", false);
	}

	@Override
	public Announcement instantiate(final Request<Announcement> request) {
		return new Announcement();
	}

	@Override
	public void validate(final Request<Announcement> request, final Announcement entity, final Errors errors) {
		this.configService.filter(request, entity, errors);
		final boolean confirmation = request.getModel().getBoolean("confirmation");
		errors.state(request, confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");
	}

	@Override
	public void create(final Request<Announcement> request, final Announcement entity) {
		this.service.save(entity);
	}

}
