package acme.components.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.format.FormatterRegistry;

import acme.components.formatter.LocalDateTimeFormatter;
import acme.framework.configuration.ConversionConfiguration;

@Primary
@Configuration
public class CustomConversionConfiguration extends ConversionConfiguration{

	@Override
	public void addFormatters(final FormatterRegistry registry) {
		super.addFormatters(registry);
		final LocalDateTimeFormatter localDateTimeFormatter = new LocalDateTimeFormatter();
		registry.addFormatter(localDateTimeFormatter);
	}

}
