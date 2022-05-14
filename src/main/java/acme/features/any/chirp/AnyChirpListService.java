package acme.features.any.chirp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chirp.Chirp;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;
import acme.repositories.ChirpRepository;
import acme.services.AbstractAcmeAuthoriseAllService;

@Service
public class AnyChirpListService extends AbstractAcmeAuthoriseAllService<Any,Chirp, ChirpRepository> implements AbstractListService<Any, Chirp> {

	@Autowired
	protected AnyChirpListService(final ChirpRepository repo, final ModelMapper mapper) {
		super(repo, mapper);
	}

	@Override
	public Collection<Chirp> findMany(final Request<Chirp> request) {
		return this.repo.findByCreationDateAfter(LocalDateTime.now().minus(1, ChronoUnit.MONTHS));
	}

	@Override
	public void unbind(final Request<Chirp> request, final Chirp entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "creationDate", "title", "author", "body", "email");
	}

}
