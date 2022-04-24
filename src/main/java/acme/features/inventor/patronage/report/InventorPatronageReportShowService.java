package acme.features.inventor.patronage.report;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.patronage.report.PatronageReport;
import acme.form.patronage.report.PatronageReportDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.repositories.PatronageReportRepository;
import acme.roles.Inventor;

@Service
public class InventorPatronageReportShowService implements AbstractShowService<Inventor,PatronageReport>{

	@Autowired
	protected ModelMapper mapper;
	
	@Autowired
	protected PatronageReportRepository repo;
	
	@Override
	public boolean authorise(final Request<PatronageReport> request) {
		return true;
	}

	@Override
	public PatronageReport findOne(final Request<PatronageReport> request) {
		final Optional<PatronageReport> report = this.repo.findById(request.getModel().getInteger("id"));
		return report.isPresent() ? report.get() : null;
	}

	@Override
	public void unbind(final Request<PatronageReport> request, final PatronageReport entity, final Model model) {
		final PatronageReportDto dto = this.mapper.map(entity, PatronageReportDto.class);
		request.unbind(dto, model, "id","version","creationDate","memorandum","info","sequenceNumber","patronageId");
	}

}
