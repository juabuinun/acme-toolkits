package acme.services.patronage;

import org.modelmapper.ModelMapper;

import acme.entities.patronage.Patronage;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.UserRole;
import acme.framework.services.UnbindMethod;
import acme.repositories.PatronageRepository;
import acme.services.AbstractCrudServiceImpl;

public abstract class AbstractPatronageListUnbindService<R extends UserRole> extends AbstractCrudServiceImpl<Patronage,PatronageRepository> implements UnbindMethod<R,Patronage>{

	protected AbstractPatronageListUnbindService(final PatronageRepository repo, final ModelMapper mapper) {
		super(repo, mapper);
	}

	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		request.unbind(entity, model, "code","status","creationDate","endDate","budget","patronage.sponsor");
	}

}
