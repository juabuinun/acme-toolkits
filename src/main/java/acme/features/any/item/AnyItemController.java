package acme.features.any.item;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.components.custom.controllers.AcmeAbstractController;
import acme.entities.item.Item;
import acme.features.any.item.component.AnyComponentInToolkitListService;
import acme.features.any.item.component.AnyComponentListService;
import acme.features.any.item.tool.AnyToolInToolkitListService;
import acme.features.any.item.tool.AnyToolListService;
import acme.framework.roles.Any;

@Controller
public class AnyItemController extends AcmeAbstractController<Any, Item>{

	@Autowired
	protected AnyToolListService toolListService;
	@Autowired
	protected AnyComponentListService componentListService;
	@Autowired
	protected AnyItemShowService showService;
	@Autowired
	protected AnyToolInToolkitListService toolInKitListService;
	@Autowired
	protected AnyComponentInToolkitListService componentInKitListService;
	
	@PostConstruct
	protected void initialise() {
		super.addCommand("list-component", "list", this.componentListService);
		super.addCommand("list-component-toolkit", "list", this.componentInKitListService);
		super.addCommand("list-tool", "list", this.toolListService);
		super.addCommand("list-tool-toolkit", "list", this.toolInKitListService);
		super.addCommand("show", this.showService);
	}
}
