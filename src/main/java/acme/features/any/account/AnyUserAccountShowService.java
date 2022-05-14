
package acme.features.any.account;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.form.account.ProfessionalProfileDto;
import acme.form.account.UserAccountDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.entities.UserAccount;
import acme.framework.roles.Any;
import acme.framework.services.AbstractShowService;
import acme.repositories.UserAccountRepository;
import acme.roles.Inventor;
import acme.roles.Patron;
import acme.services.AbstractAcmeAuthoriseAllService;

@Service
public class AnyUserAccountShowService extends AbstractAcmeAuthoriseAllService<Any, UserAccount, UserAccountRepository> implements AbstractShowService<Any, UserAccount> {

	@Autowired
	protected AnyUserAccountShowService(final UserAccountRepository repo, final ModelMapper mapper) {
		super(repo, mapper);

	}

	@Override
	public boolean authorise(final Request<UserAccount> request) {
		return true;
	}

	@Override
	@Transactional
	public UserAccount findOne(final Request<UserAccount> request) {
//		UserAccount res = null;
//		final Optional<UserAccount> user = this.repo.findById(request.getModel().getInteger("id"));
//		if (user.isPresent()) {
//			res = user.get();
//			//force initialize lazy
//			res.getRoles().size();
//		}
//		return res;
		return this.findById(request);
	}

	@Override
	@Transactional
	public void unbind(final Request<UserAccount> request, final UserAccount entity, final Model model) {
		final UserAccountDto dto = this.mapper.map(entity, UserAccountDto.class);
		if (entity.hasRole(Inventor.class)) {
			dto.setProfessionalProfile(this.mapper.map(entity.getRole(Inventor.class), ProfessionalProfileDto.class));
			model.setAttribute("displayProfessional", true);
		} else if (entity.hasRole(Patron.class)) {
			dto.setProfessionalProfile(this.mapper.map(entity.getRole(Patron.class), ProfessionalProfileDto.class));
			model.setAttribute("displayProfessional", true);
		}
		request.unbind(dto, model, "username", "identity.name", "identity.surname", "identity.email", "professionalProfile.company", "professionalProfile.statement", "professionalProfile.info");
		model.setAttribute("readonly", true);
	}

}
