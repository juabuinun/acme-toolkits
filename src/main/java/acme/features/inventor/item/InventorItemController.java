package acme.features.inventor.item;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.components.custom.controllers.AcmeAbstractController;
import acme.entities.item.Item;
import acme.features.inventor.item.component.InventorComponentCreateService;
import acme.features.inventor.item.component.InventorComponentListService;
import acme.features.inventor.item.tool.InventorToolCreateService;
import acme.features.inventor.item.tool.InventorToolListService;
import acme.roles.Inventor;

@Controller
public class InventorItemController extends AcmeAbstractController<Inventor, Item>{

	@Autowired
	protected InventorToolListService toolListService;
	@Autowired
	protected InventorComponentListService componentListService;
	@Autowired
	protected InventorItemShowService showService;
	@Autowired
	protected InventorToolCreateService toolCreateService;
	@Autowired
	protected InventorComponentCreateService componentCreateService;
	
	@PostConstruct
	protected void initialise() {
		super.addCommand("list-mine-component", "list", this.componentListService);
		super.addCommand("list-mine-tool", "list", this.toolListService);
		super.addCommand("show", this.showService);
		super.addCommand("create-tool","create", this.toolCreateService);
		super.addCommand("create-component", "create", this.componentCreateService);
	}
}
