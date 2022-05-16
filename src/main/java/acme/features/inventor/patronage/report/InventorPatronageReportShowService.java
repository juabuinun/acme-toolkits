package acme.features.inventor.patronage.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.patronagereport.PatronageReport;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;
import acme.services.AuthoriseAll;
import acme.services.patronagereport.PatronageReportService;

@Service
public class InventorPatronageReportShowService extends AuthoriseAll<Inventor,PatronageReport> implements AbstractShowService<Inventor,PatronageReport>{

	@Autowired
	protected PatronageReportService service;

	@Override
	public boolean authorise(final Request<PatronageReport> request) {
		return true;
	}

	@Override
	public PatronageReport findOne(final Request<PatronageReport> request) {
		return this.service.findById(request);
	}

	@Override
	public void unbind(final Request<PatronageReport> request, final PatronageReport entity, final Model model) {
		this.service.unbind(request, entity, model);
	}

}
