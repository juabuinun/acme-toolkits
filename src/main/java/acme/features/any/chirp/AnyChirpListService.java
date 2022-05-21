package acme.features.any.chirp;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.util.BindHelper;
import acme.entities.chirp.Chirp;
import acme.form.chirp.ChirpDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;
import acme.services.chirp.ChirpService;

@Service
public class AnyChirpListService implements AbstractListService<Any, Chirp> {

	@Autowired
	protected ChirpService service;

	@Override
	public Collection<Chirp> findMany(final Request<Chirp> request) {
		return this.service.findRecent();
	}

	@Override
	public void unbind(final Request<Chirp> request, final Chirp entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, BindHelper.getAllFieldNames(ChirpDto.class));
	}

	@Override
	public boolean authorise(final Request<Chirp> request) {
		return true;
	}

}
