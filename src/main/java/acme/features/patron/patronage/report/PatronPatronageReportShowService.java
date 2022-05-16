package acme.features.patron.patronage.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.patronagereport.PatronageReport;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Patron;
import acme.services.AuthoriseAll;
import acme.services.patronagereport.PatronageReportService;

@Service
public class PatronPatronageReportShowService extends AuthoriseAll<Patron,PatronageReport> implements AbstractShowService<Patron,PatronageReport>{

	@Autowired
	protected PatronageReportService service;

	@Override
	public PatronageReport findOne(final Request<PatronageReport> request) {
		return this.service.findById(request);
	}

	@Override
	public void unbind(final Request<PatronageReport> request, final PatronageReport entity, final Model model) {
		this.service.unbind(request, entity, model);
	}

}
