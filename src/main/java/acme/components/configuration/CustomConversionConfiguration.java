package acme.components.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.format.FormatterRegistry;

import acme.components.formatter.BooleanFormatter;
import acme.components.formatter.CustomLocalisedMoneyFormatter;
import acme.components.formatter.ItemTypeFormatter;
import acme.components.formatter.LocalDateTimeFormatter;
import acme.components.formatter.PatronageStatusFormatter;
import acme.framework.configuration.ConversionConfiguration;

@Primary
@Configuration
public class CustomConversionConfiguration extends ConversionConfiguration{

	@Override
	public void addFormatters(final FormatterRegistry registry) {
		super.addFormatters(registry);
		final LocalDateTimeFormatter localDateTimeFormatter = new LocalDateTimeFormatter();
		final CustomLocalisedMoneyFormatter customMoneyFormatter = new CustomLocalisedMoneyFormatter();
		final PatronageStatusFormatter statusFormatter = new PatronageStatusFormatter();
		final ItemTypeFormatter itemTypeFormatter = new ItemTypeFormatter();
		final BooleanFormatter booleanFormatter = new BooleanFormatter();
		registry.addFormatter(localDateTimeFormatter);
		registry.addFormatter(customMoneyFormatter);
		registry.addFormatter(statusFormatter);
		registry.addFormatter(itemTypeFormatter);
		registry.addFormatter(booleanFormatter);
	}

}
