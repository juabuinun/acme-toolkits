package acme.features.patron.dashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.components.custom.controllers.AcmeAbstractController;
import acme.form.patron.PatronDashboard;
import acme.roles.Patron;

@Controller
public class PatronDashboardController extends AcmeAbstractController<Patron, PatronDashboard> {

	@Autowired
	protected PatronDashboardShowService showService;


	@PostConstruct
	protected void initialise() {
		super.addCommand("show", this.showService);
	}

}