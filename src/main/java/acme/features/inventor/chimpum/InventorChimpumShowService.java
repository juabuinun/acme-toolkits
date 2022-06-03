package acme.features.inventor.chimpum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.chimpum.Chimpum;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;
import acme.services.AbstractAuthoriseAll;
import acme.services.chimpum.ChimpumService;

@Service
@Transactional
public class InventorChimpumShowService extends AbstractAuthoriseAll<Inventor,Chimpum> implements AbstractShowService<Inventor,Chimpum>{

	@Autowired
	protected ChimpumService service;

	@Override
	public Chimpum findOne(final Request<Chimpum> request) {
		return this.service.findById(request);
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		this.service.unbind(request, entity, model);
	}

	

}
