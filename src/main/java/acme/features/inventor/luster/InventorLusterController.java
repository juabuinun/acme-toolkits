
package acme.features.inventor.luster;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.components.custom.controllers.AbstractAcmeToolkitsController;
import acme.entities.luster.Luster;
import acme.roles.Inventor;

@Controller
public class InventorLusterController extends AbstractAcmeToolkitsController<Inventor, Luster> {

	@Autowired
	protected InventorLusterCreateService		createService;
	@Autowired
	protected InventorLusterUpdateService		updateService;
	@Autowired
	protected InventorLusterDeleteService		deleteService;
	@Autowired
	protected InventorLusterShowService		showService;
	@Autowired
	protected InventorLusterListService		listService;
	@Autowired
	protected InventorLusterListByItemService	listByItemService;


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
