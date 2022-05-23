
package acme.features.inventor.toolkit;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.toolkit.Toolkit;
import acme.form.toolkit.SaveToolkitDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;
import acme.services.AbstractAuthoriseOwner;
import acme.services.config.AcmeConfigurationService;
import acme.services.toolkit.ToolkitService;

@Service
@Transactional
public class InventorToolkitUpdateService extends AbstractAuthoriseOwner<Inventor, Toolkit> implements AbstractUpdateService<Inventor, Toolkit> {

	@Autowired
	protected ToolkitService			service;

	@Autowired
	protected ModelMapper				mapper;

	@Autowired
	protected AcmeConfigurationService	configService;


	@Autowired
	protected InventorToolkitUpdateService() {
		super("owner");
	}

	@Override
	public boolean authorise(final Request<Toolkit> request) {
		this.entity = this.service.findById(request);
		return super.authorise(request) && this.entity != null && !this.entity.isPublished();
	}

	@Override
	public void bind(final Request<Toolkit> request, final Toolkit entity, final Errors errors) {
		this.service.bind(request, entity, errors);
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
		this.configService.filter(request, this.mapper.map(entity, SaveToolkitDto.class), errors);	
	}

	@Override
	public void update(final Request<Toolkit> request, final Toolkit entity) {
		
		this.service.save(entity);
	}

}
