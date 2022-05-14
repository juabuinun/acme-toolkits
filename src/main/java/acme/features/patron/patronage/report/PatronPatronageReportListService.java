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
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.repositories.PatronageReportRepository;
import acme.repositories.PatronageRepository;
import acme.roles.Patron;
import acme.services.patronage.report.AbstractPatronageReportListUnbindService;

@Service
public class PatronPatronageReportListService extends AbstractPatronageReportListUnbindService<Patron> implements AbstractListService<Patron,PatronageReport>{

	@Autowired
	protected PatronPatronageReportListService(final PatronageReportRepository repo, final ModelMapper mapper) {
		super(repo, mapper);
	}

	@Autowired
	protected PatronageRepository patronageRepo;
	
	@Override
	public boolean authorise(final Request<PatronageReport> request) {
		assert request != null;
		
		return true;
	}

	@Override
	public Collection<PatronageReport> findMany(final Request<PatronageReport> request) {
		Collection<PatronageReport> res = new ArrayList<>();
		final Optional<Patronage> patronage = this.patronageRepo.findById(request.getModel().getInteger("patronageId"));
		if(patronage.isPresent()) {
			res = this.findBySpecification(Specifications.patronageReportByPatronage(patronage.get()));
		}
		return res;
	}

}
