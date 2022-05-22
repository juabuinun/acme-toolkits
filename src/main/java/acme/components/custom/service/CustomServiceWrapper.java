package acme.components.custom.service;

import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.helpers.ReflectionHelper;
import acme.framework.roles.UserRole;
import acme.framework.services.AbstractService;
import acme.framework.services.ServiceWrapper;
import acme.framework.services.UnbindMethod;

public class CustomServiceWrapper<R extends UserRole, E> extends ServiceWrapper<R,E>{

	private static final Logger logger = LoggerFactory.getLogger(CustomServiceWrapper.class);
	
	public CustomServiceWrapper(final AbstractService<R, E> service) {
		super(service);
	}

	@Override
	protected void detachPersistenceContext() {
		CustomServiceWrapper.logger.debug("Skipping this silly thing");
	}

	@Override
	@SuppressWarnings("unchecked")
	public void unbindMany(final Request<E> request, final Collection<E> list, final Model model) {
		assert request != null;
//		assert !CollectionHelper.someNull(list);
		assert model != null;
		assert ReflectionHelper.supports(this.service, UnbindMethod.class);

		Iterator<E> iterator;
		int index;

		iterator = list.iterator();
		index = -1;
		while (iterator.hasNext()) {
			E entity;

			entity = iterator.next();
			index++;

			model.setContextIndex(index);
			((UnbindMethod<R, E>) this.service).unbind(request, entity, model);
		}
		model.setDefaultContext();
		model.setAttribute("list$size", list.size());
		((UnbindMethod<R, E>) this.service).unbind(request, list, model);
	}
}
