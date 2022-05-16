
package acme.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import acme.framework.controllers.Request;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.roles.UserRole;
import acme.framework.services.AuthoriseMethod;
import acme.repositories.GenericJpaRepository;

public abstract class AuthoriseOwner<U extends UserRole, S extends GenericJpaRepository<U>,E> implements AuthoriseMethod<U, E> {

	protected String ownerFieldName;

	protected S roleRepo;

	@Autowired
	protected AuthoriseOwner(final S roleRepo, final ModelMapper mapper, final String ownerFieldName) {
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
