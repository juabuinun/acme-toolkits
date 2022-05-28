package acme.features.unknown.chimpum;

import acme.entities.chimpum.Chimpum;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.UserRole;
import acme.framework.services.AbstractDeleteService;
import acme.services.chimpum.ChimpumService;

public abstract class AbstractChimpumDeleteService<R extends UserRole> implements AbstractDeleteService<R,Chimpum>{

	protected ChimpumService service;
	
	protected AbstractChimpumDeleteService(final ChimpumService service) {
		super();
		this.service = service;
	}

	@Override
	public void bind(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		this.service.bind(request, entity, errors);
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		//do nothing
	}

	@Override
	public Chimpum findOne(final Request<Chimpum> request) {
		return this.service.findById(request);
	}

	@Override
	public void validate(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		//do nothing
	}

	@Override
	public void delete(final Request<Chimpum> request, final Chimpum entity) {
		this.service.delete(entity.getId());
	}

}
