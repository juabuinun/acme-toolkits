
package acme.features.inventor.patronage;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.patronage.Patronage;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.repositories.PatronageRepository;
import acme.roles.Inventor;
import acme.services.patronage.AbstractPatronageUnbindService;

@Service
public class InventorPatronageShowService extends AbstractPatronageUnbindService<Inventor> implements AbstractShowService<Inventor, Patronage> {

	@Autowired
	protected InventorPatronageShowService(final PatronageRepository repo, final ModelMapper mapper) {
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
