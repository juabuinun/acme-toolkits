
package acme.components.formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;

import acme.framework.helpers.MessageHelper;

public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

	@Override
	public String print(final LocalDateTime object, final Locale locale) {
		assert object != null;
		assert locale != null;
		final DateTimeFormatter formatter = this.getFormatterForProfile(locale);
		return object.format(formatter);
	}

	@Override
	public LocalDateTime parse(final String text, final Locale locale) throws ParseException {
		assert text != null;
		assert locale != null;
		final DateTimeFormatter formatter = this.getFormatterForProfile(locale);
		return LocalDateTime.parse(text, formatter);
	}

	protected DateTimeFormatter getFormatterForProfile(final Locale locale) {
		if (System.getProperty("spring.profiles.active").contains("populator") || System.getProperty("spring.profiles.active").contains("testing")) {
			return DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		} else {
			return LocalDateTimeFormatter.getFormatter(locale);
		}
	}
	
	public static DateTimeFormatter getFormatter(final Locale locale) {
		return DateTimeFormatter.ofPattern(MessageHelper.getMessage("general.datetime.format", null, DateTimeFormatter.ISO_LOCAL_DATE_TIME.toString(), locale));
	}

}
