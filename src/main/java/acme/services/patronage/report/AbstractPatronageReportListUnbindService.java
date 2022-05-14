package acme.services.patronage.report;

import org.modelmapper.ModelMapper;

import acme.entities.patronage.report.PatronageReport;
import acme.form.patronage.report.PatronageReportDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.UserRole;
import acme.framework.services.UnbindMethod;
import acme.repositories.PatronageReportRepository;
import acme.services.AbstractCrudServiceImpl;

public abstract class AbstractPatronageReportListUnbindService<R extends UserRole> extends AbstractCrudServiceImpl<PatronageReport,PatronageReportRepository> implements UnbindMethod<R,PatronageReport>{

	protected AbstractPatronageReportListUnbindService(final PatronageReportRepository repo, final ModelMapper mapper) {
		super(repo, mapper);
	}

	@Override
	public void unbind(final Request<PatronageReport> request, final PatronageReport entity, final Model model) {
		final PatronageReportDto dto = this.mapper.map(entity, PatronageReportDto.class);
		request.unbind(dto, model, "id","version","creationDate","memorandum","info","sequenceNumber","patronageId");
	}

}
