package acme.services.patronage;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.patronage.Patronage;
import acme.form.patronage.PatronageDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.repositories.PatronageRepository;
import acme.services.AbstractCrudServiceImpl;

@Service
public class PatronageService extends AbstractCrudServiceImpl<Patronage,PatronageRepository>{
	
	@Autowired
	protected PatronageService(final PatronageRepository repo, final ModelMapper mapper) {
		super(repo, mapper);
	}
	
	@Transactional
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		final PatronageDto dto = this.mapper.map(entity, PatronageDto.class);
		dto.setSponsorId(entity.getSponsor().getUserAccount().getId());
		dto.setSponseeId(entity.getSponsee().getUserAccount().getId());
		request.unbind(dto, model, "id", "version", "status", "code", "legal", "budget", "creationDate", "endDate", "info", "sponsorId", "sponseeId");	
	}
	
	public void unbindListingRecord(final Request<Patronage> request, final Patronage entity, final Model model) {
		request.unbind(entity, model, "code","status","creationDate","endDate","budget","patronage.sponsor");
	}
}
