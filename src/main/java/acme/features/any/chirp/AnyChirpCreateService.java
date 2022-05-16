
package acme.features.any.chirp;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chirp.Chirp;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractCreateService;
import acme.services.chirp.ChirpService;

@Service
public class AnyChirpCreateService implements AbstractCreateService<Any, Chirp> {

	@Autowired
	protected ChirpService service;

	@Override
	public boolean authorise(final Request<Chirp> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Chirp> request, final Chirp entity, final Errors errors) {
		request.bind(entity, errors, "title", "author", "body", "email");
		entity.setCreationDate(LocalDateTime.now());
	}

	@Override
	public void unbind(final Request<Chirp> request, final Chirp entity, final Model model) {
		request.unbind(entity, model, "title", "author", "body", "email");
	}

	@Override
	public Chirp instantiate(final Request<Chirp> request) {
		return new Chirp();
	}

	@Override
	public void validate(final Request<Chirp> request, final Chirp entity, final Errors errors) {
		this.service.validate(request, entity, errors);
	}

	@Override
	public void create(final Request<Chirp> request, final Chirp entity) {
		this.service.save(entity);
	}

}
