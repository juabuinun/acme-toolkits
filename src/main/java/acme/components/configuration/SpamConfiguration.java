package acme.components.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import acme.components.spam.SpamFilter;
import acme.repositories.ConfigurationRepository;
import acme.services.spam.SpamService;

@Configuration
public class SpamConfiguration {

	
	@Bean
	public SpamFilter spamFilter(final ConfigurationRepository configRepo, final SpamService spamService) {
		final SpamFilter res = new SpamFilter();
		final acme.entities.configuration.Configuration configuration = configRepo.findFirstByOrderByIdAsc();
		res.updateSpamWordCache(spamService.findAll());
		res.updateWeakSpamThresholdCache(configuration.getWeakSpamThreshold());
		res.updateStrongSpamThresholdCache(configuration.getStrongSpamThreshold());
		return res;
	}
}
