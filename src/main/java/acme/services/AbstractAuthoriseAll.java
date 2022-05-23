
package acme.services;

import acme.framework.controllers.Request;
import acme.framework.roles.UserRole;
import acme.framework.services.AuthoriseMethod;

public abstract class AbstractAuthoriseAll<U extends UserRole, E> implements AuthoriseMethod<U, E> {

	@Override
	public boolean authorise(final Request<E> request) {
		assert request != null;
		return true;
	}

}
