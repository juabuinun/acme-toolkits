package acme.features.patron.patronage;

import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.Specifications;
import acme.entities.patronage.Patronage;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.repositories.PatronageRepository;
import acme.roles.Patron;
import acme.services.patronage.AbstractPatronageListUnbindService;

@Service
public class PatronPatronageListService extends AbstractPatronageListUnbindService<Patron> implements AbstractListService<Patron, Patronage> {

	@Autowired
	protected PatronPatronageListService(final PatronageRepository repo, final ModelMapper mapper) {
		super(repo, mapper);
	}
	
	@Override
	public boolean authorise(final Request<Patronage> request) {
		return true;
	}

	@Override
	public Collection<Patronage> findMany(final Request<Patronage> request) {
		return this.findBySpecification(Specifications.patronagePrincipalIsSponsor());
	}

}