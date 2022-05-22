
package acme.services.config;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.util.ConfigCache;
import acme.components.util.ConfigCache.CacheStatus;
import acme.entities.configuration.Configuration;
import acme.form.spamword.SpamWordDto;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.repositories.ConfigurationRepository;
import acme.services.AbstractCrudServiceImpl;

@Service
@Scope("application")
@Transactional
public class AcmeConfigurationService extends AbstractCrudServiceImpl<Configuration, ConfigurationRepository> {

	private static final Logger	logger	= LoggerFactory.getLogger(AcmeConfigurationService.class);

	protected ConfigCache		cache;

	@Autowired
	protected ModelMapper		mapper;


	@Autowired
	protected AcmeConfigurationService(final ConfigurationRepository repo) {
		super(repo);
		this.cache = new ConfigCache();
	}

	public Configuration findOne() {
		Configuration res = new Configuration();
		final Optional<Configuration> config = this.repo.findFirstByOrderByIdAsc();
		if (config.isPresent()) {
			res = config.get();
		}
		return res;
	}

	public String getDefaultCurrency() {
		if (!this.cache.getStatus().equals(CacheStatus.LIVE))
			this.load();
		return this.cache.getDefaultCurrency();
	}

	@Override
	public Configuration save(final Configuration entity) {
		this.cache.setStatus(CacheStatus.RELOAD);
		return super.save(entity);
	}

	public void load() {
		final Configuration config = this.findOne();
		this.mapper.map(config, this.cache);
		this.cache.setStatus(CacheStatus.LIVE);
	}

	public void checkMoney(final Request<?> request, final Errors errors, final Money money, final String fieldName) {
		if (!this.cache.getStatus().equals(CacheStatus.LIVE))
			this.load();
		if (money != null) {
			errors.state(request, money.getAmount() >= 0, fieldName == null ? "*" : fieldName, "errors.money.positive");
			errors.state(request, Arrays.asList(this.cache.getAcceptedCurrencies().split("\\s*,\\s*")).contains(money.getCurrency().toUpperCase()), fieldName == null ? "*" : fieldName, "errors.money.currency");
		}
	}

	public void filter(final Request<?> request, final Object object, final Errors errors) {
		if (!this.cache.getStatus().equals(CacheStatus.LIVE))
			this.load();
		Integer wordCount = 0;
		Integer weakSpamWordCount = 0;
		Integer strongSpamWordCount = 0;

		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(object.getClass());

			for (final PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors()) {
				final Object value = propertyDesc.getReadMethod().invoke(object);

				if (value instanceof String && !((String) value).isEmpty()) {
					wordCount = wordCount + ((String) value).split("\\s+").length;
					final String str = ((String) value).toUpperCase();
					for (final SpamWordDto spamWord : this.cache.getSpamWords()) {
						final int spamCount = StringUtils.countMatches(str, spamWord.getWord().toUpperCase());
						if(spamCount > 0 && spamWord.isStrong()) {
							strongSpamWordCount++;
						}else if(spamCount > 0 && !spamWord.isStrong()) {
							weakSpamWordCount++;
						}
					}

				}
			}

			AcmeConfigurationService.logger.info("SpamFilterService  - Word count: {}", wordCount);
			AcmeConfigurationService.logger.info("SpamFilterService  - Spam word count: (weak:{}, strong:{})", weakSpamWordCount, strongSpamWordCount);

			errors.state(request, (strongSpamWordCount / (wordCount * 1.0)) < (this.cache.getStrongSpamThreshold() / 100.0), "*", "errors.spam");
			errors.state(request, (weakSpamWordCount / (wordCount * 1.0)) < (this.cache.getWeakSpamThreshold() / 100.0), "*", "errors.spam");
		} catch (final Exception e) {
			AcmeConfigurationService.logger.error("Error running spam filter", e);
		}
	}
}
