package acme.features.inventor.item;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.components.custom.controllers.AcmeAbstractController;
import acme.entities.item.Item;
import acme.features.inventor.item.component.InventorComponentCreateService;
import acme.features.inventor.item.component.InventorComponentListService;
import acme.features.inventor.item.component.InventorComponentPublishService;
import acme.features.inventor.item.component.InventorComponentUpdateService;
import acme.features.inventor.item.tool.InventorToolCreateService;
import acme.features.inventor.item.tool.InventorToolListService;
import acme.features.inventor.item.tool.InventorToolPublishService;
import acme.features.inventor.item.tool.InventorToolUpdateService;
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
	@Autowired
	protected InventorToolPublishService toolPublishService;
	@Autowired
	protected InventorComponentPublishService componentPublishService;
	@Autowired
	protected InventorToolUpdateService toolUpdateService;
	@Autowired
	protected InventorComponentUpdateService componentUpdateService;
	@Autowired
	protected InventorItemDeleteService deleteService;

	
	@PostConstruct
	protected void initialise() {
		super.addCommand("list-mine-component", "list", this.componentListService);
		super.addCommand("list-mine-tool", "list", this.toolListService);
		super.addCommand("show", this.showService);
		super.addCommand("create-tool","create", this.toolCreateService);
		super.addCommand("create-component", "create", this.componentCreateService);
		super.addCommand("publish-tool","update", this.toolPublishService);
		super.addCommand("publish-component", "update", this.componentPublishService);
		super.addCommand("update-tool","update", this.toolUpdateService);
		super.addCommand("update-component", "update", this.componentUpdateService);
		super.addCommand("delete", this.deleteService);
	}
}
