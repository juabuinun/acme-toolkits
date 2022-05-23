package acme.features.patron.patronage;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.patronage.Patronage;
import acme.entities.patronage.Patronage.Status;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Patron;
import acme.services.AbstractAuthoriseOwner;
import acme.services.config.AcmeConfigurationService;
import acme.services.patronage.PatronageService;

@Service
@Transactional
public class PatronPatronageUpdateService extends AbstractAuthoriseOwner<Patron,Patronage> implements AbstractUpdateService<Patron,Patronage>{

	@Autowired
	protected PatronPatronageUpdateService() {
		super( "sponsor");
	}
	
	@Autowired
	protected ModelMapper		mapper;
	
	@Autowired
	protected PatronageService service;
	
	@Autowired
	protected AcmeConfigurationService configService;
	
	

	@Override
	public boolean authorise(final Request<Patronage> request) {
		this.entity = this.service.findById(request);
		return this.entity != null && !this.entity.isPublished() && super.authorise(request);
	}

	@Override
	public void bind(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		this.service.bind(request, entity, errors);
	}

	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		this.service.unbind(request, entity, model);
	}

	@Override
	public Patronage findOne(final Request<Patronage> request) {
		return this.entity;
	}

	@Override
	public void validate(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		this.service.validate(request, entity, errors);
	}

	@Override
	public void update(final Request<Patronage> request, final Patronage entity) {
		entity.setStatus(Status.UNLISTED);
		this.service.save(entity);
	}
	
	
}
