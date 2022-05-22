
package acme.features.administrator.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.util.BindHelper;
import acme.entities.configuration.Configuration;
import acme.form.configuration.ConfigurationDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractUpdateService;
import acme.services.AuthoriseAll;
import acme.services.config.AcmeConfigurationService;

@Service
@Transactional
public class AdministratorConfigurationUpdateService extends AuthoriseAll<Administrator, Configuration> implements AbstractUpdateService<Administrator, Configuration> {

	@Autowired
	protected AcmeConfigurationService service;
	
	@Override
	public void bind(final Request<Configuration> request, final Configuration entity, final Errors errors) {
		request.bind(entity, errors, BindHelper.getAllFieldNames(ConfigurationDto.class));
	}

	@Override
	public void unbind(final Request<Configuration> request, final Configuration entity, final Model model) {
		request.unbind(entity, model, "defaultCurrency","acceptedCurrencies","strongSpamThreshold","weakSpamThreshold");
	}

	@Override
	public Configuration findOne(final Request<Configuration> request) {
		return this.service.findOne();
	}

	@Override
	public void validate(final Request<Configuration> request, final Configuration entity, final Errors errors) {
		//do nothing
	}

	@Override
	public void update(final Request<Configuration> request, final Configuration entity) {
		this.service.save(entity);
	}

}
