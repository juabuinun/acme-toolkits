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
import acme.framework.services.AbstractUpdateService;
import acme.repositories.InventorRepository;
import acme.repositories.PatronRepository;
import acme.roles.Patron;
import acme.services.AuthoriseOwner;
import acme.services.config.AcmeConfigurationService;
import acme.services.patronage.PatronageService;

@Service
@Transactional
public class PatronPatronagePublishService extends AuthoriseOwner<Patron,Patronage> implements AbstractUpdateService<Patron,Patronage>{

	@Autowired
	protected PatronPatronagePublishService() {
		super( "sponsor");
	}
	
	@Autowired
	protected ModelMapper		mapper;
	
	@Autowired
	protected PatronageService service;
	
	@Autowired
	protected AcmeConfigurationService configService;
	
	@Autowired
	protected PatronRepository patronRepo;
	
	@Autowired
	protected InventorRepository inventorRepo;

	@Override
	public boolean authorise(final Request<Patronage> request) {
		this.entity = this.service.findById(request);
		return this.entity != null && !this.entity.isPublished() && super.authorise(request);
	}

	@Override
	public void bind(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		entity.setCreationDate(LocalDateTime.now());
		entity.setSponsor(this.patronRepo.findOnePatronByUserAccountId(PrincipalHelper.get().getAccountId()));
		entity.setSponsee(this.inventorRepo.findOneInventorByUserAccountId(request.getModel().getInteger("sponseeId")));
		request.bind(entity, errors, BindHelper.getAllFieldNames(SavePatronageDto.class));
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
		this.service.validate(request, entity, errors);
	}

	@Override
	public void update(final Request<Patronage> request, final Patronage entity) {
		entity.setStatus(Status.PROPOSED);
		this.service.save(entity);
	}
}
