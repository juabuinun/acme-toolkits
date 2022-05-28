package acme.features.unknown.chimpum;

import acme.entities.chimpum.Chimpum;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.UserRole;
import acme.framework.services.AbstractShowService;
import acme.services.chimpum.ChimpumService;

public abstract class AbstractChimpumShowService<R extends UserRole> implements AbstractShowService<R,Chimpum>{

	protected ChimpumService service;
	
	@Override
	public boolean authorise(final Request<Chimpum> request) {
		//TODO:
		return true;
	}

	@Override
	public Chimpum findOne(final Request<Chimpum> request) {
		return this.service.findById(request);
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		this.service.unbind(request, entity, model);
	}

	

}
