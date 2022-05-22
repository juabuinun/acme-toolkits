
package acme.features.any.chirp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chirp.Chirp;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractCreateService;
import acme.services.chirp.ChirpService;
import acme.services.config.AcmeConfigurationService;

@Service
public class AnyChirpCreateService implements AbstractCreateService<Any, Chirp> {

	@Autowired
	protected ChirpService service;

	@Autowired
	protected AcmeConfigurationService configService;
	
	@Override
	public boolean authorise(final Request<Chirp> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Chirp> request, final Chirp entity, final Errors errors) {
		request.bind(entity, errors, "title", "author", "body", "email");
		entity.setCreationDate(LocalDateTime.now().minus(1,ChronoUnit.SECONDS));
	}

	@Override
	public void unbind(final Request<Chirp> request, final Chirp entity, final Model model) {
		request.unbind(entity, model, "title", "author", "body", "email");
		model.setAttribute("confirmation", false);
	}

	@Override
	public Chirp instantiate(final Request<Chirp> request) {
		return new Chirp();
	}

	@Override
	public void validate(final Request<Chirp> request, final Chirp entity, final Errors errors) {
		this.configService.filter(request, entity, errors);
		
		final boolean confirmation = request.getModel().getBoolean("confirmation");
		errors.state(request, confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");
	}

	@Override
	public void create(final Request<Chirp> request, final Chirp entity) {
		this.service.save(entity);
	}

}
