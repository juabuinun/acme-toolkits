
package acme.features.patron.patronage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.patronage.Patronage;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.controllers.Response;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Patron;
import acme.services.AbstractAuthoriseOwner;
import acme.services.patronage.PatronageService;

@Service
@Transactional
public class PatronPatronageDeleteService extends AbstractAuthoriseOwner<Patron, Patronage> implements AbstractDeleteService<Patron, Patronage> {

	protected PatronPatronageDeleteService() {
		super("sponsor");
	}

	@Autowired
	protected PatronageService			service;



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
		//		do nothing
	}

	@Override
	public void delete(final Request<Patronage> request, final Patronage entity) {
		this.service.delete(entity.getId());
	}

	@Override
	public void onFailure(final Request<Patronage> request, final Response<Patronage> response, final Throwable oops) {
		response.setModel(request.getModel());
	}
}
