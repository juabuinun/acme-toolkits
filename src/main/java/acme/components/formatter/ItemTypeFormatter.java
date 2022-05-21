package acme.components.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import acme.entities.item.Item.Type;
import acme.framework.helpers.MessageHelper;

public class ItemTypeFormatter implements Formatter<Type>{

	@Override
	public String print(final Type object, final Locale locale) {
		return MessageHelper.getMessage(object.getLabel(), null, object.name(), locale);
	}

	
	@Override
	public Type parse(final String text, final Locale locale) throws ParseException {
		//no way to get the Type from the string so just return null
		return null;
	}

}
