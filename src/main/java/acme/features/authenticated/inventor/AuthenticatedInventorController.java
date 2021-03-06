/*
 * AuthenticatedInventorController.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.inventor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.custom.controllers.AbstractAcmeToolkitsController;
import acme.framework.roles.Authenticated;
import acme.roles.Inventor;

@Controller
@RequestMapping("/authenticated/Inventor/")
public class AuthenticatedInventorController extends AbstractAcmeToolkitsController<Authenticated, Inventor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedInventorCreateService	createService;

	@Autowired
	protected AuthenticatedInventorUpdateService	updateService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCommand("create", this.createService);
		super.addCommand("update", this.updateService);
	}

}
