package acme.features.unknown.chimpum;

import acme.entities.chimpum.Chimpum;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.UserRole;
import acme.framework.services.AbstractCreateService;
import acme.services.AbstractAuthoriseAll;
import acme.services.chimpum.ChimpumService;

public abstract class AbstractChimpumCreateService<R extends UserRole> extends AbstractAuthoriseAll<R,Chimpum> implements AbstractCreateService<R,Chimpum>{

	protected ChimpumService service;
	
	protected AbstractChimpumCreateService(final ChimpumService service) {
		super();
		this.service = service;
	}
	
	@Override
	public void bind(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		this.service.bind(request, entity, errors);
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		this.service.unbind(request, entity, model);
	}

	@Override
	public Chimpum instantiate(final Request<Chimpum> request) {
		return new Chimpum();
	}

	@Override
	public void validate(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		this.service.validate(request, entity, errors);
	}

	@Override
	public void create(final Request<Chimpum> request, final Chimpum entity) {
		this.service.save(entity);
	}

}
