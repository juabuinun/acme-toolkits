
package acme.services.config;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import acme.components.spam.SpamFilter;
import acme.entities.configuration.Configuration;
import acme.repositories.ConfigurationRepository;
import acme.services.AbstractCrudServiceImpl;

@Service
@Scope("application")
public class AcmeConfigurationService extends AbstractCrudServiceImpl<Configuration, ConfigurationRepository> {

	@Autowired
	private ApplicationContext context;
	
	protected Configuration configuration;

	@Autowired
	protected AcmeConfigurationService(final ConfigurationRepository repo, final ModelMapper mapper) {
		super(repo, mapper);
	}

	public Configuration findOne() {
		if(this.configuration==null) {
			final Optional<Configuration> config = this.repo.findFirstByOrderByIdAsc();
			if(config.isPresent()) {
				this.configuration = config.get();
			}else {
				this.configuration = new Configuration();
			}
		}
		return this.configuration;
	}

	@Override
	public Configuration save(final Configuration entity) {
		this.configuration = super.save(entity);
		this.context.getBean(SpamFilter.class).updateSpamWordCache(entity.getSpamWords());
		this.context.getBean(SpamFilter.class).updateStrongSpamThresholdCache(entity.getStrongSpamThreshold());
		this.context.getBean(SpamFilter.class).updateWeakSpamThresholdCache(entity.getWeakSpamThreshold());
		return this.configuration;
	}
	
}
