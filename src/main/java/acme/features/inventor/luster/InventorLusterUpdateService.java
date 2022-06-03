
package acme.features.inventor.luster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.luster.Luster;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;
import acme.services.luster.LusterService;

@Service
@Transactional
public class InventorLusterUpdateService implements AbstractUpdateService<Inventor, Luster> {

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
		this.service.unbind(request, entity, model);
	}

	@Override
	public Luster findOne(final Request<Luster> request) {
		return this.entity;
	}

	@Override
	public void validate(final Request<Luster> request, final Luster entity, final Errors errors) {
		this.service.validate(request, entity, errors);
	}

	@Override
	public void update(final Request<Luster> request, final Luster entity) {
		this.service.save(entity);
	}

}
