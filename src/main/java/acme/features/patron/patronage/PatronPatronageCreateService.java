
package acme.features.patron.patronage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.patronage.Patronage;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractCreateService;
import acme.roles.Patron;
import acme.services.AbstractAuthoriseAll;
import acme.services.patronage.PatronageService;

@Service
@Transactional
public class PatronPatronageCreateService extends AbstractAuthoriseAll<Patron, Patronage> implements AbstractCreateService<Patron, Patronage> {

	@Autowired
	protected PatronageService			service;


	@Override
	public void bind(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		this.service.bind(request, entity, errors);
	}

	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		this.service.unbind(request, entity, model);
	}

	@Override
	public Patronage instantiate(final Request<Patronage> request) {
		return this.service.instantiate(request, PrincipalHelper.get().getAccountId(), request.getModel().getInteger("sponseeId"));
		
	}

	@Override
	public void validate(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		this.service.validate(request, entity, errors);
	}

	@Override
	public void create(final Request<Patronage> request, final Patronage entity) {
		this.service.save(entity);
	}

}
