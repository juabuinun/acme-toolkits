
package acme.features.patron.patronage.report;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.components.custom.controllers.AcmeAbstractController;
import acme.entities.patronage.report.PatronageReport;
import acme.roles.Patron;

@Controller
public class PatronPatronageReportController extends AcmeAbstractController<Patron, PatronageReport> {

	@Autowired
	protected PatronPatronageReportListService	listService;
	@Autowired
	protected PatronPatronageReportShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addCommand("list", this.listService);
		super.addCommand("show", this.showService);
	}
}
