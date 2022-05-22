
package acme.components.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import acme.entities.item.Item.Type;
import acme.framework.helpers.MessageHelper;

public class ItemTypeFormatter implements Formatter<Type> {

	@Override
	public String print(final Type object, final Locale locale) {
		if (System.getProperty("spring.profiles.active").contains("testing")) {
			return object.getLabel();
		} else {
			return MessageHelper.getMessage(object.getLabel(), null, object.name(), locale);
		}
	}

	@Override
	public Type parse(final String text, final Locale locale) throws ParseException {
		return Type.of(text);
	}

}
