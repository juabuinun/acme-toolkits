package acme.features.patron.patronage;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.components.custom.controllers.AcmeAbstractController;
import acme.entities.patronage.Patronage;
import acme.roles.Patron;

@Controller
public class PatronPatronageController extends AcmeAbstractController<Patron, Patronage>{

	@Autowired
	protected PatronPatronageListService listService;
	@Autowired
	protected PatronPatronageShowService showService;
	
	@PostConstruct
	protected void initialise() {
		super.addCommand("list-mine", "list", this.listService);
		super.addCommand("show", this.showService);
	}
}