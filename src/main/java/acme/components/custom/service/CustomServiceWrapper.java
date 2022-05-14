package acme.components.custom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import acme.framework.roles.UserRole;
import acme.framework.services.AbstractService;
import acme.framework.services.ServiceWrapper;

public class CustomServiceWrapper<R extends UserRole, E> extends ServiceWrapper<R,E>{

	private static final Logger logger = LoggerFactory.getLogger(CustomServiceWrapper.class);
	
	public CustomServiceWrapper(final AbstractService<R, E> service) {
		super(service);
	}

	@Override
	protected void detachPersistenceContext() {
		CustomServiceWrapper.logger.debug("Skipping this silly thing");
	}

}
