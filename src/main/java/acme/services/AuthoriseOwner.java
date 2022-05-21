
package acme.services;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;

import acme.framework.controllers.Request;
import acme.framework.entities.UserAccount;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.roles.UserRole;
import acme.framework.services.AuthoriseMethod;

public abstract class AuthoriseOwner<U extends UserRole,E> implements AuthoriseMethod<U, E> {

	protected String ownerFieldName;
	
	protected E entity;

	@Autowired
	protected AuthoriseOwner(final String ownerFieldName) {
		this.ownerFieldName = ownerFieldName;
	}

	@Override
	public boolean authorise(final Request<E> request) {
		boolean res = true;
		try {
			final int principalId = PrincipalHelper.get().getAccountId();
			final Class<?> clazz = this.entity.getClass();
			final Field field = clazz.getDeclaredField(this.ownerFieldName);
			field.setAccessible(true);
			final UserAccount user = ((UserRole) field.get(this.entity)).getUserAccount();
			if(user == null || principalId != user.getId()) {
				res = false;
			}
		} catch (final Exception e) {
			res = false;
		}
		return res;
	}

}
