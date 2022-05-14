
package acme.features.inventor.toolkit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.components.custom.controllers.AcmeAbstractController;
import acme.entities.toolkit.Toolkit;
import acme.roles.Inventor;

@Controller
public class InventorToolkitController extends AcmeAbstractController<Inventor, Toolkit> {

	@Autowired
	protected InventorToolkitListService listService;
	@Autowired
	protected InventorToolkitShowService showService;


	@PostConstruct
	protected void initialise() {
		super.addCommand("list-mine","list", this.listService);
		super.addCommand("show", this.showService);
	}

}
