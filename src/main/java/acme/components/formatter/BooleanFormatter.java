
package acme.components.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import acme.framework.helpers.MessageHelper;

public class BooleanFormatter implements Formatter<Boolean> {

	@Override
	public String print(final Boolean object, final Locale locale) {
		if (System.getProperty("spring.profiles.active").contains("testing")) {
			return object.toString();
		} else {
			return MessageHelper.getMessage(object.equals(Boolean.TRUE) ? "general.boolean.true" : "general.boolean.false", null, object.toString(), locale);
		}
	}

	@Override
	public Boolean parse(final String text, final Locale locale) throws ParseException {
		return Boolean.valueOf(text);
	}

}
