package acme.features.administrator.configuration.spam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.configuration.SpamWord;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractDeleteService;
import acme.repositories.SpamWordRepository;
import acme.services.AuthoriseAll;

@Service
@Transactional
public class AdministratorSpamWordDeleteService extends AuthoriseAll<Administrator,SpamWord> implements AbstractDeleteService<Administrator,SpamWord>{

	@Autowired
	protected SpamWordRepository repo;
	
	@Override
	public void bind(final Request<SpamWord> request, final SpamWord entity, final Errors errors) {
		//
	}

	@Override
	public void unbind(final Request<SpamWord> request, final SpamWord entity, final Model model) {
		//
	}

	@Override
	public void validate(final Request<SpamWord> request, final SpamWord entity, final Errors errors) {
		//do nothing
	}

	@Override
	public SpamWord findOne(final Request<SpamWord> request) {
		final SpamWord res =  new SpamWord();
		res.setWord("placeholder");
		return res;
	}

	@Override
	public void delete(final Request<SpamWord> request, final SpamWord entity) {
		this.repo.deleteById(request.getModel().getInteger("id"));
	}

	

}
