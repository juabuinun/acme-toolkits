
package acme.features.any.toolkits;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.components.custom.controllers.AbstractAcmeToolkitsController;
import acme.entities.toolkit.Toolkit;
import acme.framework.roles.Any;

@Controller
public class AnyToolkitController extends AbstractAcmeToolkitsController<Any, Toolkit> {

	@Autowired
	protected AnyToolkitListService listService;
	@Autowired
	protected AnyToolkitShowService showService;
	@Autowired
	protected AnyToolkitWithItemListService listItemService;


	@PostConstruct
	protected void initialise() {
		super.addCommand("list", this.listService);
		super.addCommand("list-item","list", this.listService);
		super.addCommand("show", this.showService);
	}

}
