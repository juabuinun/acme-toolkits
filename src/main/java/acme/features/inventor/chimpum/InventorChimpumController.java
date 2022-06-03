
package acme.features.inventor.chimpum;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.components.custom.controllers.AbstractAcmeToolkitsController;
import acme.entities.chimpum.Chimpum;
import acme.roles.Inventor;

@Controller
public class InventorChimpumController extends AbstractAcmeToolkitsController<Inventor, Chimpum> {

	@Autowired
	protected InventorChimpumCreateService		createService;
	@Autowired
	protected InventorChimpumUpdateService		updateService;
	@Autowired
	protected InventorChimpumDeleteService		deleteService;
	@Autowired
	protected InventorChimpumShowService		showService;
	@Autowired
	protected InventorChimpumListService		listService;
	@Autowired
	protected InventorChimpumListByItemService	listByItemService;


	@PostConstruct
	protected void initialise() {
		super.addCommand("create", this.createService);
		super.addCommand("update", this.updateService);
		this.addCommand("delete", this.deleteService);
		this.addCommand("show", this.showService);
		this.addCommand("list-mine", "list", this.listService);
		this.addCommand("list-by-item", "list",this.listByItemService);
	}
}
