
package acme.features.any.account;

import java.util.Collection;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.Specifications;
import acme.form.account.UserAccountDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.entities.UserAccount;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;
import acme.repositories.UserAccountRepository;

@Service
@Transactional
public class AnyUserAccountListService implements AbstractListService<Any, UserAccount> {

	@Autowired
	UserAccountRepository	repo;

	@Autowired
	ModelMapper					modelMapper;


	@Override
	public boolean authorise(final Request<UserAccount> request) {
		assert request != null;
		return true;
	}

	@Override
	@Transactional
	public Collection<UserAccount> findMany(final Request<UserAccount> request) {
//		final Collection<UserAccount> res= this.repo.findAllEnabled();
//		res.forEach(u -> u.getRoles().size());
//		return res;
		return this.repo.findAll(Specifications.userAccountIsEnabledAndNotSystem());
	}

	@Override
	@Transactional
	public void unbind(final Request<UserAccount> request, final UserAccount entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		final UserAccountDto dto = this.modelMapper.map(entity, UserAccountDto.class);

		request.unbind(dto, model, "id" ,"username", "identity.fullName", "authorityString");
	}

}
