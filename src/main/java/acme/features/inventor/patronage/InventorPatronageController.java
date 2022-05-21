package acme.features.inventor.patronage;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.components.custom.controllers.AcmeAbstractController;
import acme.entities.patronage.Patronage;
import acme.roles.Inventor;

@Controller
public class InventorPatronageController extends AcmeAbstractController<Inventor, Patronage>{

	@Autowired
	protected InventorPatronageListService listService;
	@Autowired
	protected InventorPatronageShowService showService;
	@Autowired
	protected InventorPatronageModifyService modifyService;
	
	@PostConstruct
	protected void initialise() {
		super.addCommand("list-mine", "list", this.listService);
		super.addCommand("show", this.showService);
		super.addCommand("modify", "update",this.modifyService);
	}

}
