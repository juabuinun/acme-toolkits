package acme.features.inventor.chimpum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.chimpum.Chimpum;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor;
import acme.services.chimpum.ChimpumService;

@Service
@Transactional
public class InventorChimpumDeleteService implements AbstractDeleteService<Inventor,Chimpum>{

	Chimpum entity;
	
	@Autowired
	protected ChimpumService service;
	
	@Override
	public boolean authorise(final Request<Chimpum> request) {
		return this.service.authorise(request, i->this.entity = i);
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
		return this.entity;
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
