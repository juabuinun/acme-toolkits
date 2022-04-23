package acme.features.patron.patronage.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.Specifications;
import acme.entities.patronage.Patronage;
import acme.entities.patronage.report.PatronageReport;
import acme.form.patronage.report.PatronageReportDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.repositories.PatronageReportRepository;
import acme.repositories.PatronageRepository;
import acme.roles.Patron;

@Service
public class PatronPatronageReportListService implements AbstractListService<Patron,PatronageReport>{

	@Autowired
	protected ModelMapper mapper;
	@Autowired
	protected PatronageReportRepository repo;
	@Autowired
	protected PatronageRepository patronageRepo;
	
	@Override
	public boolean authorise(final Request<PatronageReport> request) {
		return true;
	}

	@Override
	public Collection<PatronageReport> findMany(final Request<PatronageReport> request) {
		Collection<PatronageReport> res = new ArrayList<>();
		final Optional<Patronage> patronage = this.patronageRepo.findById(request.getModel().getInteger("patronageId"));
		if(patronage.isPresent()) {
			res = this.repo.findAll(Specifications.patronageReportByPatronage(patronage.get()));
		}
		return res;
	}

	@Override
	public void unbind(final Request<PatronageReport> request, final PatronageReport entity, final Model model) {
		final PatronageReportDto dto = this.mapper.map(entity, PatronageReportDto.class);
		request.unbind(dto, model, "id","version","creationDate","memorandum","info","sequenceNumber","patronageId");
	}

}
