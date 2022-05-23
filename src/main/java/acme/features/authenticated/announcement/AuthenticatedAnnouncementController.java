
package acme.features.authenticated.announcement;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.components.custom.controllers.AbstractAcmeToolkitsController;
import acme.entities.announcement.Announcement;
import acme.framework.roles.Authenticated;

@Controller
public class AuthenticatedAnnouncementController extends AbstractAcmeToolkitsController<Authenticated, Announcement> {

	@Autowired
	protected AuthenticatedAnnouncementListService		listService;
	@Autowired
	protected AuthenticatedAnnouncementShowService	showService;



	@PostConstruct
	protected void initialise() {
		super.addCommand("list", this.listService);
		super.addCommand("show", this.showService);
	}
}
