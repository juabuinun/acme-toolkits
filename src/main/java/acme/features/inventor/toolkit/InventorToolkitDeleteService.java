package acme.features.inventor.toolkit;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.toolkit.Toolkit;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor;
import acme.services.AbstractAuthoriseOwner;
import acme.services.toolkit.ToolkitService;

@Service
@Transactional
public class InventorToolkitDeleteService extends AbstractAuthoriseOwner<Inventor,Toolkit> implements AbstractDeleteService<Inventor,Toolkit>{

	@Autowired
	protected ModelMapper mapper;
	
	@Autowired
	protected ToolkitService service;
	
	protected InventorToolkitDeleteService() {
		super("owner");
	}

	@Override
	public boolean authorise(final Request<Toolkit> request) {
		this.entity = this.service.findById(request);
		return this.entity != null && !this.entity.isPublished() && super.authorise(request);
	}

	@Override
	public void bind(final Request<Toolkit> request, final Toolkit entity, final Errors errors) {
		//do nothing
	}

	@Override
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		this.service.unbind(request, entity, model);
	}

	@Override
	public Toolkit findOne(final Request<Toolkit> request) {
		return this.entity;
	}

	@Override
	public void validate(final Request<Toolkit> request, final Toolkit entity, final Errors errors) {
		//do nothing
	}

	@Override
	public void delete(final Request<Toolkit> request, final Toolkit entity) {
		this.service.delete(entity.getId());
	}

}
