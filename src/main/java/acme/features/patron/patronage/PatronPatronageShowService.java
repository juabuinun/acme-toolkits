package acme.features.patron.patronage;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.patronage.Patronage;
import acme.form.patronage.PatronageDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.repositories.PatronageRepository;
import acme.roles.Patron;

@Service
public class PatronPatronageShowService implements AbstractShowService<Patron, Patronage> {

	@Autowired
	protected ModelMapper			mapper;

	@Autowired
	protected PatronageRepository	repo;


	@Override
	public boolean authorise(final Request<Patronage> request) {
		return true;
	}

	@Override
	public Patronage findOne(final Request<Patronage> request) {
		final Optional<Patronage> res = this.repo.findById(request.getModel().getInteger("id"));
		return res.isPresent() ? res.get() : null;
	}

	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		final PatronageDto dto = this.mapper.map(entity, PatronageDto.class);
		dto.setSponsorId(entity.getSponsor().getUserAccount().getId());
		dto.setSponseeId(entity.getSponsee().getUserAccount().getId());
		request.unbind(dto, model, "id", "version", "status", "code", "legal", "budget", "creationDate", "endDate", "info", "sponsorId", "sponseeId");
	}
}
