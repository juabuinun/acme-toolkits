
package acme.services;

import org.modelmapper.ModelMapper;

import acme.framework.controllers.Request;
import acme.framework.entities.AbstractEntity;
import acme.framework.roles.UserRole;
import acme.framework.services.AuthoriseMethod;
import acme.repositories.GenericJpaRepository;

public abstract class AbstractAcmeAuthoriseAllService<U extends UserRole, E extends AbstractEntity, R extends GenericJpaRepository<E>> extends AbstractCrudServiceImpl<E, R> implements AuthoriseMethod<U, E> {



	protected AbstractAcmeAuthoriseAllService(final R repo, final ModelMapper mapper) {
		super(repo, mapper);
	}

	@Override
	public boolean authorise(final Request<E> request) {
		assert request != null;
		return true;
	}

}
