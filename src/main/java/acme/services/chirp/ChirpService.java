package acme.services.chirp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.spam.SpamFilter;
import acme.entities.chirp.Chirp;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.repositories.ChirpRepository;
import acme.services.AbstractCrudServiceImpl;

@Service
public class ChirpService extends AbstractCrudServiceImpl<Chirp,ChirpRepository>{

	@Autowired
	protected SpamFilter filter;
	
	@Autowired
	protected ChirpService(final ChirpRepository repo, final ModelMapper mapper) {
		super(repo, mapper);
	}
	
	public List<Chirp> findRecent(){
		return this.repo.findByCreationDateAfter(LocalDateTime.now().minus(1, ChronoUnit.MONTHS));
	}

	public void validate(final Request<Chirp> request, final Chirp entity, final Errors errors) {
		this.filter.filter(request, entity, errors);
	}
}
