package acme.features.administrator.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.components.custom.controllers.AbstractAcmeToolkitsController;
import acme.entities.configuration.Configuration;
import acme.framework.roles.Administrator;

@Controller
public class AdministratorConfigurationController extends AbstractAcmeToolkitsController<Administrator, Configuration>{

	@Autowired
	protected AdministratorConfigurationShowService showService;
	
	@Autowired
	protected AdministratorConfigurationUpdateService updateService;

	@PostConstruct
	protected void initialise() {
		super.addCommand("show", this.showService);
		super.addCommand("update", this.updateService);
	}
}