package acme.features.patron.patronage;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.util.BindHelper;
import acme.entities.patronage.Patronage;
import acme.entities.patronage.Patronage.Status;
import acme.form.patronage.PatronageDto;
import acme.form.patronage.SavePatronageDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractCreateService;
import acme.repositories.InventorRepository;
import acme.repositories.PatronRepository;
import acme.roles.Patron;
import acme.services.AuthoriseAll;
import acme.services.config.AcmeConfigurationService;
import acme.services.patronage.PatronageService;

@Service
@Transactional
public class PatronPatronageCreateService extends AuthoriseAll<Patron,Patronage> implements AbstractCreateService<Patron,Patronage>{

	@Autowired
	protected ModelMapper mapper;
	
	@Autowired
	protected PatronageService service;
	
	@Autowired
	protected AcmeConfigurationService configService;
	
	@Autowired
	protected PatronRepository patronRepo;
	
	@Autowired
	protected InventorRepository inventorRepo;
	
	@Override
	public void bind(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		entity.setCreationDate(LocalDateTime.now());
		entity.setStatus(Status.UNLISTED);
		entity.setSponsor(this.patronRepo.findOnePatronByUserAccountId(PrincipalHelper.get().getAccountId()));
		entity.setSponsee(this.inventorRepo.findOneInventorByUserAccountId(request.getModel().getInteger("sponseeId")));
		request.bind(entity, errors, BindHelper.getAllFieldNames(SavePatronageDto.class));
	}

	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
//		final PatronageDto dto = this.mapper.map(entity, PatronageDto.class);

		request.unbind(entity, model, BindHelper.getAllFieldNames(PatronageDto.class));
		model.setAttribute("sponseeId", request.getModel().getInteger("sponseeId"));
		model.setAttribute("sponsorId", PrincipalHelper.get().getAccountId());
		model.setAttribute("draftMode", true);
	}

	@Override
	public Patronage instantiate(final Request<Patronage> request) {
		return new Patronage();
	}

	@Override
	public void validate(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		this.service.validate(request, entity, errors);
	}

	@Override
	public void create(final Request<Patronage> request, final Patronage entity) {
		this.service.save(entity);
	}

}
