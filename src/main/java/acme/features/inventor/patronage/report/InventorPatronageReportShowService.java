package acme.features.inventor.patronage.report;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.util.BindHelper;
import acme.entities.patronagereport.PatronageReport;
import acme.form.patronagereport.BasicPatronageReportDto;
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
	
	@Autowired
	protected ModelMapper mapper;

	@Override
	public PatronageReport findOne(final Request<PatronageReport> request) {
		return this.service.findById(request);
	}

	@Override
	public void unbind(final Request<PatronageReport> request, final PatronageReport entity, final Model model) {
		request.unbind(entity, model, BindHelper.getAllFieldNames(BasicPatronageReportDto.class));
	}

}
