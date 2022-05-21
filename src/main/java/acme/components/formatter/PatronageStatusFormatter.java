package acme.components.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import acme.entities.patronage.Patronage.Status;
import acme.framework.helpers.MessageHelper;

public class PatronageStatusFormatter implements Formatter<Status>{

	@Override
	public String print(final Status object, final Locale locale) {
		return MessageHelper.getMessage(object.getLabel(), null, object.name(), locale);
	}

	
	@Override
	public Status parse(final String text, final Locale locale) throws ParseException {
		//no way to get the status from the string so just return null
		return null;
	}

}
