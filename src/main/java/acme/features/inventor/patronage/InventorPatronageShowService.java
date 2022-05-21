
package acme.features.inventor.patronage;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.util.BindHelper;
import acme.entities.patronage.Patronage;
import acme.form.patronage.PatronageDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;
import acme.services.AuthoriseOwner;
import acme.services.patronage.PatronageService;

@Service
public class InventorPatronageShowService extends AuthoriseOwner<Inventor,Patronage> implements AbstractShowService<Inventor, Patronage> {

	@Autowired
	protected PatronageService service;
	
	@Autowired
	protected ModelMapper mapper;
	
	protected InventorPatronageShowService() {
		super("sponsee");
	}
	
	@Override
	public boolean authorise(final Request<Patronage> request) {
		this.entity = this.service.findById(request);
		return this.entity!=null && super.authorise(request);
	}

	@Override
	@Transactional
	public Patronage findOne(final Request<Patronage> request) {
		return this.entity;
	}

	@Override
	@Transactional
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		final PatronageDto dto = this.mapper.map(entity, PatronageDto.class);
		dto.setSponsorId(entity.getSponsor().getUserAccount().getId());
		dto.setSponseeId(entity.getSponsee().getUserAccount().getId());
		request.unbind(dto, model, BindHelper.getAllFieldNames(PatronageDto.class));
	}

}
