package acme.features.administrator.configuration.spam;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.SpamWord;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractListService;
import acme.repositories.SpamWordRepository;

@Service
public class AdministratorSpamWordListService implements AbstractListService<Administrator,SpamWord>{

	@Autowired
	protected SpamWordRepository repo;
	
	@Override
	public boolean authorise(final Request<SpamWord> request) {
		return true;
	}

	@Override
	public Collection<SpamWord> findMany(final Request<SpamWord> request) {
		return this.repo.findAll();
	}

	@Override
	public void unbind(final Request<SpamWord> request, final SpamWord entity, final Model model) {
		request.unbind(entity, model, "word","strong","language");
	}

}
