
package acme.features.inventor.patronage;

import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.Specifications;
import acme.entities.patronage.Patronage;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;
import acme.services.AbstractAuthoriseAll;
import acme.services.patronage.PatronageService;

@Service
public class InventorPatronageListService extends AbstractAuthoriseAll<Inventor,Patronage> implements AbstractListService<Inventor, Patronage> {

	@Autowired
	protected PatronageService service;
	
	@Autowired
	protected ModelMapper mapper;

	@Override
	@Transactional
	public Collection<Patronage> findMany(final Request<Patronage> request) {
		return this.service.findBySpecification(Specifications.patronagePrincipalIsSponsee());
	}

	@Override
	@Transactional
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		this.service.unbind(request, entity, model);
	}

}
