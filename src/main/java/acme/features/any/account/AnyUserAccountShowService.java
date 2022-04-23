
package acme.features.any.account;

import java.util.Optional;

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

@Service
public class AnyUserAccountShowService implements AbstractShowService<Any, UserAccount> {

	@Autowired
	protected UserAccountRepository	repo;

	@Autowired
	protected ModelMapper			modelMapper;


	@Override
	public boolean authorise(final Request<UserAccount> request) {
		return true;
	}

	@Override
	public UserAccount findOne(final Request<UserAccount> request) {
		UserAccount res = null;
		final Optional<UserAccount> user = this.repo.findById(request.getModel().getInteger("id"));
		if (user.isPresent()) {
			res = user.get();
			//force initialize lazy
			res.getRoles().size();
		}
		return res;
	}

	@Override
	public void unbind(final Request<UserAccount> request, final UserAccount entity, final Model model) {
		final UserAccountDto dto = this.modelMapper.map(entity, UserAccountDto.class);
		if(entity.hasRole(Inventor.class)) {
			dto.setProfessionalProfile(this.modelMapper.map(entity.getRole(Inventor.class), ProfessionalProfileDto.class));
			model.setAttribute("displayProfessional", true);
		}else if(entity.hasRole(Patron.class)){
			dto.setProfessionalProfile(this.modelMapper.map(entity.getRole(Patron.class), ProfessionalProfileDto.class));
			model.setAttribute("displayProfessional", true);
		}
		request.unbind(dto, model, "username", "identity.name", "identity.surname", "identity.email","professionalProfile.company","professionalProfile.statement","professionalProfile.info");
		model.setAttribute("readonly", true);
	}

}
