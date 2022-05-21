
package acme.features.patron.patronage;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.util.BindHelper;
import acme.entities.patronage.Patronage;
import acme.form.patronage.PatronageDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.controllers.Response;
import acme.framework.services.AbstractDeleteService;
import acme.repositories.InventorRepository;
import acme.repositories.PatronRepository;
import acme.roles.Patron;
import acme.services.AuthoriseOwner;
import acme.services.config.AcmeConfigurationService;
import acme.services.patronage.PatronageService;

@Service
@Transactional
public class PatronPatronageDeleteService extends AuthoriseOwner<Patron, Patronage> implements AbstractDeleteService<Patron, Patronage> {

	protected PatronPatronageDeleteService() {
		super("sponsor");
	}


	@Autowired
	protected ModelMapper				mapper;

	@Autowired
	protected PatronageService			service;

	@Autowired
	protected AcmeConfigurationService	configService;

	@Autowired
	protected PatronRepository			patronRepo;

	@Autowired
	protected InventorRepository		inventorRepo;


	@Override
	public boolean authorise(final Request<Patronage> request) {
		this.entity = this.service.findById(request);
		return this.entity != null && !this.entity.isPublished() && super.authorise(request);
	}

	@Override
	public void bind(final Request<Patronage> request, final Patronage entity, final Errors errors) {
//		entity.setSponsor(this.patronRepo.findOnePatronByUserAccountId(PrincipalHelper.get().getAccountId()));
//		entity.setSponsee(this.inventorRepo.findOneInventorByUserAccountId(request.getModel().getInteger("sponseeId")));
//		request.bind(entity, errors, BindHelper.getAllFieldNames(PatronageDto.class));
	}

	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		final PatronageDto dto = this.mapper.map(entity, PatronageDto.class);
		dto.setSponsorId(entity.getSponsor().getUserAccount().getId());
		dto.setSponseeId(entity.getSponsee().getUserAccount().getId());
		request.unbind(dto, model, BindHelper.getAllFieldNames(PatronageDto.class));

		model.setAttribute("draftMode", !entity.isPublished());
	}

	@Override
	public Patronage findOne(final Request<Patronage> request) {
		return this.entity;
	}

	@Override
	public void validate(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		//		do nothing
	}

	@Override
	public void delete(final Request<Patronage> request, final Patronage entity) {
		this.service.delete(entity.getId());
	}

	@Override
	public void onFailure(final Request<Patronage> request, final Response<Patronage> response, final Throwable oops) {
		response.setModel(request.getModel());
	}
}
