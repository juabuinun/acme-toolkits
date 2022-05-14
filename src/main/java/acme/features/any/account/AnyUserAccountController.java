package acme.features.any.account;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.components.custom.controllers.AcmeAbstractController;
import acme.framework.entities.UserAccount;
import acme.framework.roles.Any;

@Controller
public class AnyUserAccountController extends AcmeAbstractController<Any, UserAccount>{

	@Autowired
	protected AnyUserAccountListService listService;
	@Autowired
	protected AnyUserAccountShowService showService;
	
	@PostConstruct
	protected void initialise() {
		super.addCommand("list-enabled", "list", this.listService);
		super.addCommand("show", this.showService);
	}
}
