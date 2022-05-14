package acme.features.inventor.item;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.components.custom.controllers.AcmeAbstractController;
import acme.entities.item.Item;
import acme.roles.Inventor;

@Controller
public class InventorItemController extends AcmeAbstractController<Inventor, Item>{

	@Autowired
	protected InventorToolListService toolListService;
	@Autowired
	protected InventorComponentListService componentListService;
	@Autowired
	protected InventorItemShowService showService;
	
	@PostConstruct
	protected void initialise() {
		super.addCommand("list-mine-component", "list", this.componentListService);
		super.addCommand("list-mine-tool", "list", this.toolListService);
		super.addCommand("show", this.showService);
	}
}
