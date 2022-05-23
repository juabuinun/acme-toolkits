package acme.features.patron.patronage.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.util.BindHelper;
import acme.entities.patronagereport.PatronageReport;
import acme.form.patronagereport.BasicPatronageReportDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Patron;
import acme.services.AbstractAuthoriseAll;
import acme.services.patronagereport.PatronageReportService;

@Service
public class PatronPatronageReportShowService extends AbstractAuthoriseAll<Patron,PatronageReport> implements AbstractShowService<Patron,PatronageReport>{

	@Autowired
	protected PatronageReportService service;

	@Override
	public PatronageReport findOne(final Request<PatronageReport> request) {
		return this.service.findById(request);
	}

	@Override
	public void unbind(final Request<PatronageReport> request, final PatronageReport entity, final Model model) {
		request.unbind(entity, model, BindHelper.getAllFieldNames(BasicPatronageReportDto.class));
	}

}
