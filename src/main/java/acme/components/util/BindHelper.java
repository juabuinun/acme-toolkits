package acme.components.util;

import java.lang.reflect.Field;

public class BindHelper {

	protected BindHelper() {
		super();
	}
	
	public static String[] getAllFieldNames(final Class<?> clazz) {
		final Field[] fields = clazz.getDeclaredFields();
		final String[] res = new String[fields.length];
		for(int i = 0 ;  i<fields.length; i++ ) {
			res[i] = fields[i].getName();
		}
		return res;
	}
}
