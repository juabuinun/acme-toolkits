package acme.features.inventor.patronage.report;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.util.BindHelper;
import acme.entities.patronage.Patronage;
import acme.entities.patronagereport.PatronageReport;
import acme.form.patronagereport.PatronageReportDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;
import acme.services.AuthoriseAll;
import acme.services.patronage.PatronageService;
import acme.services.patronagereport.PatronageReportService;

@Service
public class InventorPatronageReportListService extends AuthoriseAll<Inventor,PatronageReport> implements AbstractListService<Inventor,PatronageReport>{

	@Autowired
	protected PatronageReportService service;
	
	@Autowired
	protected PatronageService patronageService;
	
	@Override
	public boolean authorise(final Request<PatronageReport> request) {
		return true;
	}

	@Override
	@Transactional
	public Collection<PatronageReport> findMany(final Request<PatronageReport> request) {
		final Patronage patronage = this.patronageService.findById(request.getModel().getInteger("patronageId"));
		if(patronage != null) {
			return patronage.getReports();
		}else {
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional
	public void unbind(final Request<PatronageReport> request, final PatronageReport entity, final Model model) {
		request.unbind(entity, model, BindHelper.getAllFieldNames(PatronageReportDto.class));
	}

}
