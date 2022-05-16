package acme.features.administrator.dashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.components.custom.controllers.AcmeAbstractController;
import acme.form.administrator.AdminDashboard;
import acme.framework.roles.Administrator;

@Controller
public class AdministratorDashboardController extends AcmeAbstractController<Administrator, AdminDashboard> {

	@Autowired
	protected AdministratorDashboardShowService showService;


	@PostConstruct
	protected void initialise() {
		super.addCommand("show", this.showService);
	}

}