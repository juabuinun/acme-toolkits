
package acme.features.inventor.patronage.report;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.components.custom.controllers.AcmeAbstractController;
import acme.entities.patronagereport.PatronageReport;
import acme.roles.Inventor;

@Controller
public class InventorPatronageReportController extends AcmeAbstractController<Inventor,PatronageReport> {

	@Autowired
	protected InventorPatronageReportListService	listService;
	@Autowired
	protected InventorPatronageReportShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addCommand("list", this.listService);
		super.addCommand("show", this.showService);
	}
}
