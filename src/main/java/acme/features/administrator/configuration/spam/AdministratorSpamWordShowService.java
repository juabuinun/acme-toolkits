package acme.features.administrator.configuration.spam;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.util.BindHelper;
import acme.entities.configuration.SpamWord;
import acme.form.spamword.SpamWordDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractShowService;
import acme.repositories.SpamWordRepository;
import acme.services.AuthoriseAll;

@Service
@Transactional
public class AdministratorSpamWordShowService extends AuthoriseAll<Administrator,SpamWord> implements AbstractShowService<Administrator,SpamWord>{

	@Autowired
	protected SpamWordRepository repo;
	

	@Override
	public void unbind(final Request<SpamWord> request, final SpamWord entity, final Model model) {
		request.unbind(entity, model, BindHelper.getAllFieldNames(SpamWordDto.class));
	}


	@Override
	public SpamWord findOne(final Request<SpamWord> request) {
		final Optional<SpamWord> res = this.repo.findById(request.getModel().getInteger("id"));
		return res.isPresent()?res.get():null;
	}




}
