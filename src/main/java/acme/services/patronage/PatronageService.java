
package acme.services.patronage;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.util.BindHelper;
import acme.entities.patronage.Patronage;
import acme.form.patronage.PatronageDto;
import acme.form.patronage.SavePatronageDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.helpers.PrincipalHelper;
import acme.repositories.InventorRepository;
import acme.repositories.PatronRepository;
import acme.repositories.PatronageRepository;
import acme.services.AbstractCrudServiceImpl;
import acme.services.config.AcmeConfigurationService;

@Service
public class PatronageService extends AbstractCrudServiceImpl<Patronage, PatronageRepository> {

	@Autowired
	protected AcmeConfigurationService	configService;

	@Autowired
	protected PatronRepository			patronRepo;

	@Autowired
	protected InventorRepository		inventorRepo;


	@Autowired
	protected PatronageService(final PatronageRepository repo) {
		super(repo);
	}

	public boolean isCodeUnique(final Patronage patronage) {
		boolean res = false;
		if (patronage.getId() != 0)
			res = this.findById(patronage.getId()).getCode().equals(patronage.getCode());

		return res || this.repo.countByCode(patronage.getCode()) <= 0;
	}

	public void validate(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		this.configService.filter(request, entity, errors);
		this.configService.checkMoney(request, errors, entity.getBudget(), "budget");
		errors.state(request, entity.getCode() == null ? Boolean.TRUE : this.isCodeUnique(entity), "code", "errors.unique");
		errors.state(request, entity.getEndDate() == null ? Boolean.TRUE : entity.getEndDate().isAfter(entity.getCreationDate().plus(1, ChronoUnit.MONTHS)), "endDate", "errors.patronage.date");
	}

	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		request.unbind(entity, model, BindHelper.getAllFieldNames(PatronageDto.class));
		model.setAttribute("sponseeId", entity.getSponsee().getUserAccount().getId());
		model.setAttribute("sponsorId", entity.getSponsor().getUserAccount().getId());
		model.setAttribute("draftMode", !entity.isPublished());
	}
	
	public Patronage instantiate(final Request<Patronage> request, final Integer sponsorId, final Integer sponseeId) {
		final Patronage res= new Patronage();
		res.setSponsor(this.patronRepo.findOnePatronByUserAccountId(sponsorId));
		res.setSponsee(this.inventorRepo.findOneInventorByUserAccountId(sponseeId));
		return res;
	}

	public void bind(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		entity.setSponsor(this.patronRepo.findOnePatronByUserAccountId(PrincipalHelper.get().getAccountId()));
		entity.setSponsee(this.inventorRepo.findOneInventorByUserAccountId(request.getModel().getInteger("sponseeId")));
		request.bind(entity, errors, BindHelper.getAllFieldNames(SavePatronageDto.class));
	}
}
