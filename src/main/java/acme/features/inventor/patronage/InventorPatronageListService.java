
package acme.features.inventor.patronage;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.Specifications;
import acme.entities.patronage.Patronage;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.repositories.PatronageRepository;
import acme.roles.Inventor;

@Service
public class InventorPatronageListService implements AbstractListService<Inventor, Patronage> {

	@Autowired
	protected PatronageRepository repo;
	
	@Override
	public boolean authorise(final Request<Patronage> request) {
		return true;
	}

	@Override
	public Collection<Patronage> findMany(final Request<Patronage> request) {
		return this.repo.findAll(Specifications.patronagePrincipalIsSponsee());
	}

	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		request.unbind(entity, model, "code","status","creationDate","endDate","budget","patronage.sponsor");
	}

}
