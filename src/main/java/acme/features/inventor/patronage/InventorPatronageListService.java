
package acme.features.inventor.patronage;

import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.Specifications;
import acme.entities.patronage.Patronage;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.repositories.PatronageRepository;
import acme.roles.Inventor;
import acme.services.patronage.AbstractPatronageListUnbindService;

@Service
public class InventorPatronageListService extends AbstractPatronageListUnbindService<Inventor> implements AbstractListService<Inventor, Patronage> {

	@Autowired
	protected InventorPatronageListService(final PatronageRepository repo, final ModelMapper mapper) {
		super(repo, mapper);
	}

	@Override
	public boolean authorise(final Request<Patronage> request) {
		assert request != null;
		return true;
	}

	@Override
	public Collection<Patronage> findMany(final Request<Patronage> request) {
		return this.findBySpecification(Specifications.patronagePrincipalIsSponsee());
	}

}
