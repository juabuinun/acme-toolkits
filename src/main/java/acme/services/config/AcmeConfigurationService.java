
package acme.services.config;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		if (money != null) {
			if (!this.cache.getStatus().equals(CacheStatus.LIVE))
				this.load();
			final List<String> acceptedCurrencies = Arrays.asList(this.cache.getAcceptedCurrencies().split("\\s*,\\s*"));
			
			errors.state(request, money.getAmount() >= 0, fieldName == null ? "*" : fieldName, "errors.money.positive");			
			errors.state(request, acceptedCurrencies.contains(money.getCurrency()), fieldName, "errors.money.currency");
		}
	}

	public void filter(final Request<?> request, final Object object, final Errors errors) {
		Integer wordCount = 0;
		Integer weakSpamWordCount = 0;
		Integer strongSpamWordCount = 0;

		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(object.getClass());
			final Set<String> fieldNames = new HashSet<>();
			for (final PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors()) {
				final Object value = propertyDesc.getReadMethod().invoke(object);

				if (value instanceof String && !((String) value).isEmpty()) {
					wordCount = wordCount + ((String) value).split("\\s+").length;
					final String str = ((String) value).toUpperCase();
					if (!this.cache.getStatus().equals(CacheStatus.LIVE))
						this.load();
					for (final SpamWordDto spamWord : this.cache.getSpamWords()) {
						int count = 0;
						final String patternn = String.format("%s%s%s", "([\\s\\t\\r\\n]+|^)", spamWord.getWord().toUpperCase().replaceAll("\\s+", "[\\\\s\\\\t\\\\r\\\\n]+"), "($|[\\s\\t\\r\\n]+)");
						final Matcher matcher = Pattern.compile(patternn).matcher(str);
						while (matcher.find()) {
							count++;
							fieldNames.add(propertyDesc.getName());
						}
						if (count > 0 && spamWord.isStrong()) {
							strongSpamWordCount++;
						} else if (count > 0 && !spamWord.isStrong()) {
							weakSpamWordCount++;
						}
					}

				}
			}

			AcmeConfigurationService.logger.info("SpamFilterService  - Word count: {}", wordCount);
			AcmeConfigurationService.logger.info("SpamFilterService  - Spam word count: (weak:{}, strong:{})", weakSpamWordCount, strongSpamWordCount);
			final boolean isAllowed = (this.cache.getStrongSpamThreshold() <= 0 && this.cache.getWeakSpamThreshold() <= 0) ? Boolean.TRUE
				: ((strongSpamWordCount / (wordCount * 1.0)) < (this.cache.getStrongSpamThreshold() / 100.0) && (weakSpamWordCount / (wordCount * 1.0)) < (this.cache.getWeakSpamThreshold() / 100.0));
			if (!isAllowed) {
				for (final String fieldName : fieldNames) {
					errors.state(request, false, fieldName, "errors.spam");
				}
			}

		} catch (final Exception e) {
			errors.state(request, false, "*", "errors.spam.error");
			AcmeConfigurationService.logger.error("Error running spam filter", e);
		}
	}
}
