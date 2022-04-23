package acme.features.administrator.configuration.spam;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.configuration.SpamWord;
import acme.framework.controllers.AbstractController;
import acme.framework.roles.Administrator;

@Controller
public class AdministratorSpamWordController extends AbstractController<Administrator, SpamWord>{

	@Autowired
	protected AdministratorSpamWordListService listService;

	@PostConstruct
	protected void initialise() {
		super.addCommand("list", this.listService);
	}
}