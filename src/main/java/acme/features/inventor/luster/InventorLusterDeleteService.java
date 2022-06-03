package acme.features.inventor.luster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.luster.Luster;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor;
import acme.services.luster.LusterService;

@Service
@Transactional
public class InventorLusterDeleteService implements AbstractDeleteService<Inventor,Luster>{

	Luster entity;
	
	@Autowired
	protected LusterService service;
	
	@Override
	public boolean authorise(final Request<Luster> request) {
		return this.service.authorise(request, i->this.entity = i);
	}

	@Override
	public void bind(final Request<Luster> request, final Luster entity, final Errors errors) {
		this.service.bind(request, entity, errors);
	}

	@Override
	public void unbind(final Request<Luster> request, final Luster entity, final Model model) {
		//do nothing
	}

	@Override
	public Luster findOne(final Request<Luster> request) {
		return this.entity;
	}

	@Override
	public void validate(final Request<Luster> request, final Luster entity, final Errors errors) {
		//do nothing
	}

	@Override
	public void delete(final Request<Luster> request, final Luster entity) {
		this.service.delete(entity.getId());
	}



}
