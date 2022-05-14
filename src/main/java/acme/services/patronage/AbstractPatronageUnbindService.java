package acme.services.patronage;

import org.modelmapper.ModelMapper;

import acme.entities.patronage.Patronage;
import acme.form.patronage.PatronageDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.UserRole;
import acme.framework.services.UnbindMethod;
import acme.repositories.PatronageRepository;
import acme.services.AbstractCrudServiceImpl;

public abstract class AbstractPatronageUnbindService<R extends UserRole> extends AbstractCrudServiceImpl<Patronage,PatronageRepository> implements UnbindMethod<R,Patronage>{

	protected AbstractPatronageUnbindService(final PatronageRepository repo, final ModelMapper mapper) {
		super(repo, mapper);
	}

	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		final PatronageDto dto = this.mapper.map(entity, PatronageDto.class);
		dto.setSponsorId(entity.getSponsor().getUserAccount().getId());
		dto.setSponseeId(entity.getSponsee().getUserAccount().getId());
		request.unbind(dto, model, "id", "version", "status", "code", "legal", "budget", "creationDate", "endDate", "info", "sponsorId", "sponseeId");	
	}

}
