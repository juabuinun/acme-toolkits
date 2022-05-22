package acme.features.administrator.configuration.spam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.util.BindHelper;
import acme.entities.configuration.Configuration;
import acme.entities.configuration.SpamWord;
import acme.form.spamword.SpamWordDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractCreateService;
import acme.services.AuthoriseAll;
import acme.services.config.AcmeConfigurationService;

@Service
@Transactional
public class AdministratorSpamWordCreateService extends AuthoriseAll<Administrator,SpamWord> implements AbstractCreateService<Administrator,SpamWord>{

	@Autowired
	protected AcmeConfigurationService service;
	
	@Override
	public void bind(final Request<SpamWord> request, final SpamWord entity, final Errors errors) {
		request.bind(entity, errors, BindHelper.getAllFieldNames(SpamWordDto.class));
	}

	@Override
	public void unbind(final Request<SpamWord> request, final SpamWord entity, final Model model) {
		request.unbind(entity, model, BindHelper.getAllFieldNames(SpamWordDto.class));
	}

	@Override
	public SpamWord instantiate(final Request<SpamWord> request) {
		return new SpamWord();
	}

	@Override
	public void validate(final Request<SpamWord> request, final SpamWord entity, final Errors errors) {
		//do nothing
	}

	@Override
	public void create(final Request<SpamWord> request, final SpamWord entity) {
		final Configuration config = this.service.findOne();
		entity.setConfiguration(config);
		config.getSpamWords().add(entity);
		this.service.save(config);
	}

}
