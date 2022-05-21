
package acme.features.patron.patronage;

import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.Specifications;
import acme.components.util.BindHelper;
import acme.entities.patronage.Patronage;
import acme.form.patronage.PatronageDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Patron;
import acme.services.AuthoriseAll;
import acme.services.patronage.PatronageService;

@Service
@Transactional
public class PatronPatronageListService extends AuthoriseAll<Patron, Patronage> implements AbstractListService<Patron, Patronage> {

	@Autowired
	protected PatronageService	service;

	@Autowired
	protected ModelMapper		mapper;


	@Override
	public Collection<Patronage> findMany(final Request<Patronage> request) {
		return this.service.findBySpecification(Specifications.patronagePrincipalIsSponsor());
	}

	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		final PatronageDto dto = this.mapper.map(entity, PatronageDto.class);
		dto.setSponsorId(entity.getSponsor().getUserAccount().getId());
		dto.setSponseeId(entity.getSponsee().getUserAccount().getId());
		request.unbind(dto, model, BindHelper.getAllFieldNames(PatronageDto.class));
	}

}
