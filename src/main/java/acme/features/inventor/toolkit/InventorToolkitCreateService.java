package acme.features.inventor.toolkit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.toolkit.Toolkit;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;
import acme.services.AuthoriseAll;

@Service
@Transactional
public class InventorToolkitCreateService extends AuthoriseAll<Inventor,Toolkit> implements AbstractCreateService<Inventor,Toolkit>{

	@Override
	public void bind(final Request<Toolkit> request, final Toolkit entity, final Errors errors) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Toolkit instantiate(final Request<Toolkit> request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validate(final Request<Toolkit> request, final Toolkit entity, final Errors errors) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(final Request<Toolkit> request, final Toolkit entity) {
		// TODO Auto-generated method stub
		
	}

}
