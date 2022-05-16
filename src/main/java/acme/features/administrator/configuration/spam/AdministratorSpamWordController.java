package acme.features.administrator.configuration.spam;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.components.custom.controllers.AcmeAbstractController;
import acme.entities.configuration.SpamWord;
import acme.framework.roles.Administrator;

@Controller
public class AdministratorSpamWordController extends AcmeAbstractController<Administrator, SpamWord>{

	@Autowired
	protected AdministratorSpamWordListService listService;

	@PostConstruct
	protected void initialise() {
		super.addCommand("list", this.listService);
	}
}