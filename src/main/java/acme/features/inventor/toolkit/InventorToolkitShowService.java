
package acme.features.inventor.toolkit;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.toolkit.Toolkit;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;
import acme.services.AbstractAuthoriseOwner;
import acme.services.toolkit.ToolkitService;

@Service
public class InventorToolkitShowService extends AbstractAuthoriseOwner<Inventor, Toolkit> implements AbstractShowService<Inventor, Toolkit> {

	@Autowired
	protected ModelMapper		mapper;

	@Autowired
	protected ToolkitService	service;


	@Autowired
	protected InventorToolkitShowService() {
		super("owner");
	}

	@Override
	@Transactional
	public boolean authorise(final Request<Toolkit> request) {
		this.entity = this.service.findById(request);
		return super.authorise(request);
	}

	@Override
	@Transactional
	public Toolkit findOne(final Request<Toolkit> request) {
		return this.entity;
	}

	@Override
	@Transactional
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		this.service.unbind(request, entity, model);
	}

}
