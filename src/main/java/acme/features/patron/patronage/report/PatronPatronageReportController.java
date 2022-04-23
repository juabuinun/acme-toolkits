
package acme.features.patron.patronage.report;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.patronage.report.PatronageReport;
import acme.framework.controllers.AbstractController;
import acme.roles.Patron;

@Controller
public class PatronPatronageReportController extends AbstractController<Patron, PatronageReport> {

	@Autowired
	protected PatronPatronageReportListService	listService;


	@PostConstruct
	protected void initialise() {
		super.addCommand("list", this.listService);
	}
}