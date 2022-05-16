package acme.services.patronagereport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.patronagereport.PatronageReport;
import acme.form.patronage.report.PatronageReportDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.repositories.PatronageReportRepository;
import acme.services.AbstractCrudServiceImpl;

@Service
public class PatronageReportService extends AbstractCrudServiceImpl<PatronageReport,PatronageReportRepository>{

	@Autowired
	protected PatronageReportService(final PatronageReportRepository repo, final ModelMapper mapper) {
		super(repo, mapper);
	}
	
	@Transactional
	public void unbind(final Request<PatronageReport> request, final PatronageReport entity, final Model model) {
		final PatronageReportDto dto = this.mapper.map(entity, PatronageReportDto.class);
		request.unbind(dto, model, "id","version","creationDate","memorandum","info","sequenceNumber","patronageId");
	}
	
	@Transactional
	public void unbindListingRecord(final Request<PatronageReport> request, final PatronageReport entity, final Model model) {
		final PatronageReportDto dto = this.mapper.map(entity, PatronageReportDto.class);
		request.unbind(dto, model, "id","version","creationDate","memorandum","info","sequenceNumber","patronageId");
	}

}
