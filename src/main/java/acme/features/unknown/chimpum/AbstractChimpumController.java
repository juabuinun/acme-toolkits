
package acme.features.unknown.chimpum;

import javax.annotation.PostConstruct;

import acme.components.custom.controllers.AbstractAcmeToolkitsController;
import acme.entities.chimpum.Chimpum;
import acme.framework.roles.UserRole;

public abstract class AbstractChimpumController<R extends UserRole> extends AbstractAcmeToolkitsController<R, Chimpum> {

	protected AbstractChimpumCreateService<R>	createService;
	protected AbstractChimpumUpdateService<R>	updateService;
	protected AbstractChimpumDeleteService<R>	deleteService;
	protected AbstractChimpumShowService<R> showService;

	@PostConstruct
	protected void initialise() {
		super.addCommand("create", this.createService);
		super.addCommand("update", this.updateService);
		this.addCommand("delete", this.deleteService);
		this.addCommand("show", this.showService);
	}
}
