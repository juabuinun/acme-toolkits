package acme.features.any.chirp;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.components.custom.controllers.AcmeAbstractController;
import acme.entities.chirp.Chirp;
import acme.framework.roles.Any;

@Controller
public class AnyChirpController extends AcmeAbstractController<Any, Chirp> {

	@Autowired
	protected AnyChirpListService service;


	@PostConstruct
	protected void initialise() {
		super.addCommand("list-recent", "list", this.service);
	}
}