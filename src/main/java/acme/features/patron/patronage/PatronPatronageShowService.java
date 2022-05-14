
package acme.features.patron.patronage;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.patronage.Patronage;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.repositories.PatronageRepository;
import acme.roles.Patron;
import acme.services.patronage.AbstractPatronageUnbindService;

@Service
public class PatronPatronageShowService extends AbstractPatronageUnbindService<Patron> implements AbstractShowService<Patron, Patronage> {

	@Autowired
	protected PatronPatronageShowService(final PatronageRepository repo, final ModelMapper mapper) {
		super(repo, mapper);
	}

	@Override
	public boolean authorise(final Request<Patronage> request) {
		return true;
	}

	@Override
	public Patronage findOne(final Request<Patronage> request) {
		return this.findById(request);
	}
}
