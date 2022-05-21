package acme.services.chirp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chirp.Chirp;
import acme.repositories.ChirpRepository;
import acme.services.AbstractCrudServiceImpl;

@Service
public class ChirpService extends AbstractCrudServiceImpl<Chirp,ChirpRepository>{

	
	@Autowired
	protected ChirpService(final ChirpRepository repo) {
		super(repo);
	}
	
	public List<Chirp> findRecent(){
		return this.repo.findByCreationDateAfter(LocalDateTime.now().minus(1, ChronoUnit.MONTHS));
	}

}
