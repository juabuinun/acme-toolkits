
package acme.features.inventor.patronage.report;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.util.BindHelper;
import acme.entities.patronage.Patronage;
import acme.entities.patronage.Patronage.Status;
import acme.entities.patronagereport.PatronageReport;
import acme.form.patronagereport.PatronageReportDto;
import acme.form.patronagereport.SavePatronageReportDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;
import acme.services.AbstractAuthoriseAll;
import acme.services.patronage.PatronageService;
import acme.services.patronagereport.PatronageReportService;

@Service
@Transactional
public class InventorPatronageReportCreateService extends AbstractAuthoriseAll<Inventor, PatronageReport> implements AbstractCreateService<Inventor, PatronageReport> {

	protected Patronage					patronage;

	@Autowired
	protected PatronageReportService	service;

	@Autowired
	protected PatronageService			patronageService;

	@Autowired
	protected ModelMapper				mapper;


	@Override
	public boolean authorise(final Request<PatronageReport> request) {
		boolean res = false;
		try {
			final int patronageId = request.getModel().getInteger("patronageId");
			this.patronage = this.patronageService.findById(patronageId);
			res = this.patronage != null && this.patronage.getSponsee().getUserAccount().getId() == PrincipalHelper.get().getAccountId() && this.patronage.getStatus().equals(Status.ACCEPTED);
		} catch (final Exception e) {
			// do nothing
			//model get attribute should not have an assert lmao
		}
		return res;
	}

	@Override
	public void bind(final Request<PatronageReport> request, final PatronageReport entity, final Errors errors) {
		request.bind(entity, errors, BindHelper.getAllFieldNames(SavePatronageReportDto.class));
		entity.setPatronage(this.patronage);
		entity.setSerialNumber(this.service.getNextSerialNumber(this.patronage.getCode()));
		entity.setCreationDate(LocalDateTime.now().minus(1,ChronoUnit.SECONDS));
	}

	@Override
	public void unbind(final Request<PatronageReport> request, final PatronageReport entity, final Model model) {
		entity.setSerialNumber(this.service.getNextSerialNumber(this.patronage.getCode()));
		entity.setPatronage(this.patronage);
		final PatronageReportDto dto = this.mapper.map(entity, PatronageReportDto.class);
		dto.setPatronageId(this.patronage.getId());
		request.unbind(dto, model, BindHelper.getAllFieldNames(PatronageReportDto.class));
		
		model.setAttribute("confirmation", false);
	}

	@Override
	public PatronageReport instantiate(final Request<PatronageReport> request) {
		return new PatronageReport();
	}

	@Override
	public void validate(final Request<PatronageReport> request, final PatronageReport entity, final Errors errors) {
		final boolean confirmation = request.getModel().getBoolean("confirmation");
		errors.state(request, confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");
	}

	@Override
	public void create(final Request<PatronageReport> request, final PatronageReport entity) {
		this.service.save(entity);
	}

}
