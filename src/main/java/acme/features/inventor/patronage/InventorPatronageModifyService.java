
package acme.features.inventor.patronage;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.util.BindHelper;
import acme.entities.patronage.Patronage;
import acme.entities.patronage.Patronage.Status;
import acme.form.patronage.PatronageDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;
import acme.services.AbstractAuthoriseOwner;
import acme.services.config.AcmeConfigurationService;
import acme.services.patronage.PatronageService;

@Service
@Transactional
public class InventorPatronageModifyService extends AbstractAuthoriseOwner<Inventor, Patronage> implements AbstractUpdateService<Inventor, Patronage> {

	Status status;

	@Autowired
	protected ModelMapper				mapper;

	@Autowired
	protected PatronageService			service;

	@Autowired
	protected AcmeConfigurationService	configService;


	protected InventorPatronageModifyService() {
		super("sponsee");
	}

	@Override
	public boolean authorise(final Request<Patronage> request) {
		this.entity = this.service.findById(request);
		this.status = Status.of(request.getModel().getString("state"));
		return this.entity != null && this.status != null && this.entity.getStatus().equals(Status.PROPOSED) && super.authorise(request);
	}

	@Override
	public void bind(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		//do nothing
	}

	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		request.unbind(this.entity, model, BindHelper.getAllFieldNames(PatronageDto.class));
		model.setAttribute("proposed", entity.getStatus().equals(Status.PROPOSED));
	}

	@Override
	public Patronage findOne(final Request<Patronage> request) {
		return this.entity;
	}

	@Override
	public void validate(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		// do nothing
	}

	@Override
	public void update(final Request<Patronage> request, final Patronage entity) {
		this.entity.setStatus(this.status);
		this.service.save(this.entity);
	}

}
