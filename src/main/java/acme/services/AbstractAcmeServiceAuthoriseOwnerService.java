
package acme.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;

import acme.framework.controllers.Request;
import acme.framework.entities.AbstractEntity;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.roles.UserRole;
import acme.framework.services.AuthoriseMethod;
import acme.repositories.GenericJpaRepository;

public abstract class AbstractAcmeServiceAuthoriseOwnerService<U extends UserRole, S extends GenericJpaRepository<U>,E extends AbstractEntity, R extends GenericJpaRepository<E>> extends AbstractCrudServiceImpl<E, R> implements AuthoriseMethod<U, E> {

	protected String ownerFieldName;
	
	protected S roleRepo;

	protected AbstractAcmeServiceAuthoriseOwnerService(final S roleRepo, final R repo, final ModelMapper mapper, final String ownerFieldName) {
		super(repo, mapper);
		this.ownerFieldName = ownerFieldName;
		this.roleRepo = roleRepo;
	}

	@Override
	public boolean authorise(final Request<E> request) {
		boolean res = true;
		try {
			final int principalId = PrincipalHelper.get().getAccountId();
			final Optional<U> role = this.roleRepo.findById(request.getModel().getInteger(this.ownerFieldName));
			final Integer ownerId = role.isPresent() ? role.get().getUserAccount().getId() : null;
			if(ownerId == null || principalId != ownerId) {
				res = false;
			}

		} catch (final Exception e) {
			res = false;
		}
		return res;
	}

}
