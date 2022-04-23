package acme.components.configuration;

import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Component;

import acme.framework.components.database.DatabasePopulator;

@Primary
@Component
public class CustomDatabasePopulator extends DatabasePopulator{

	@Override
	protected boolean isTypeAllowed(final TypeDescriptor descriptor) {
		return true;
	}

}
