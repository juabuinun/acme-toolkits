package acme.services.spam;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import acme.entities.configuration.SpamWord;
import acme.repositories.SpamWordRepository;
import acme.services.AbstractCrudServiceImpl;

@Service
public class SpamService extends AbstractCrudServiceImpl<SpamWord,SpamWordRepository>{

	protected SpamService(final SpamWordRepository repo, final ModelMapper mapper) {
		super(repo, mapper);
	}

}
