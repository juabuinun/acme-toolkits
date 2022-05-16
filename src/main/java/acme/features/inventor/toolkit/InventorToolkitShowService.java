
package acme.features.inventor.toolkit;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.toolkit.Toolkit;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.repositories.InventorRepository;
import acme.roles.Inventor;
import acme.services.AuthoriseOwner;
import acme.services.toolkit.ToolkitService;

@Service
public class InventorToolkitShowService extends AuthoriseOwner<Inventor,InventorRepository,Toolkit> implements AbstractShowService<Inventor, Toolkit> {

	@Autowired
	protected ModelMapper mapper;
	
	@Autowired
	protected ToolkitService service;

	@Autowired
	protected InventorToolkitShowService(final InventorRepository roleRepo, final ModelMapper mapper) {
		super(roleRepo, mapper, "owner");
	}

	@Override
	public Toolkit findOne(final Request<Toolkit> request) {
		return this.service.findById(request);
	}

	@Override
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		this.service.unbind(request, entity, model);
	}

}
