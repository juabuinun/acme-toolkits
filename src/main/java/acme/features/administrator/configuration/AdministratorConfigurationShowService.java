
package acme.features.administrator.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.configuration.Configuration;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractShowService;
import acme.repositories.ConfigurationRepository;

@Service
public class AdministratorConfigurationShowService implements AbstractShowService<Administrator, Configuration> {

	@Autowired
	protected ConfigurationRepository repo;


	@Override
	public boolean authorise(final Request<Configuration> request) {
		return true;
	}

	@Override
	public Configuration findOne(final Request<Configuration> request) {
		return this.repo.findFirstByOrderByIdAsc();
	}

	@Override
	public void unbind(final Request<Configuration> request, final Configuration entity, final Model model) {
		request.unbind(entity, model, "defaultCurrency","acceptedCurrencies","strongSpamThreshold","weakSpamThreshold");
	}

}
