
package acme.components.spam;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import acme.entities.configuration.SpamWord;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;

@Component
@Scope("application")
public class SpamFilter {

	private static final Logger	logger	= LoggerFactory.getLogger(SpamFilter.class);

	protected ModelMapper					mapper;

	protected List<SpamWord>	spamWords;
	protected Integer			strongSpamThreshold;
	protected Integer			weakSpamThreshold;

	public SpamFilter() {
		this.mapper = new ModelMapper();
		this.spamWords = new ArrayList<>();
		this.strongSpamThreshold = 0;
		this.weakSpamThreshold = 0;
	}
	
	public void updateSpamWordCache(final List<SpamWord> spamWords) {
		this.spamWords = spamWords;
	}
	
	public void updateWeakSpamThresholdCache(final Integer weakSpamThreshold) {
		this.weakSpamThreshold = weakSpamThreshold;
	}
	
	public void updateStrongSpamThresholdCache(final Integer strongSpamThreshold) {
		this.strongSpamThreshold = strongSpamThreshold;
	}

	public void filter(final Request<?> request, final Object object, final Errors errors) {
		Integer wordCount = 0;
		Integer weakSpamWordCount = 0;
		Integer strongSpamWordCount = 0;

		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(object.getClass());

			for (final PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors()) {
				final Object value = propertyDesc.getReadMethod().invoke(object);

				if (value instanceof String && !((String) value).isEmpty()) {
					final List<String> words = Arrays.asList(((String) value).split("\\s+"));
					wordCount += words.size();

					for (final SpamWord spamWord : this.spamWords) {
						if (((String) value).toUpperCase().contains(spamWord.getWord().toUpperCase())) {
							if (spamWord.isStrong()) {
								strongSpamWordCount++;
							} else {
								weakSpamWordCount++;
							}
						}
					}
				}
			}
			
			SpamFilter.logger.info("SpamFilterService  - Word count: {}", wordCount);
			SpamFilter.logger.info("SpamFilterService  - Spam word count: (weak:{}, strong:{})", weakSpamWordCount, strongSpamWordCount);

			errors.state(request, (weakSpamWordCount / (wordCount * 1.0)) < (this.weakSpamThreshold / 100.0), "*", "errors.spam");
			errors.state(request, (strongSpamWordCount / (wordCount * 1.0)) < (this.weakSpamThreshold / 100.0), "*", "errors.spam");
		} catch (final Exception e) {
			SpamFilter.logger.error("Error running spam filter", e);
		}
	}

}
