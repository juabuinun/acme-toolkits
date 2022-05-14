package acme.features.inventor.patronage.report;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.patronage.report.PatronageReport;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.repositories.PatronageReportRepository;
import acme.roles.Inventor;
import acme.services.patronage.report.AbstractPatronageReportUnbindService;

@Service
public class InventorPatronageReportShowService extends AbstractPatronageReportUnbindService<Inventor> implements AbstractShowService<Inventor,PatronageReport>{

	@Autowired
	protected InventorPatronageReportShowService(final PatronageReportRepository repo, final ModelMapper mapper) {
		super(repo, mapper);
	}

	@Override
	public boolean authorise(final Request<PatronageReport> request) {
		return true;
	}

	@Override
	public PatronageReport findOne(final Request<PatronageReport> request) {
		return this.findById(request);
	}

}
