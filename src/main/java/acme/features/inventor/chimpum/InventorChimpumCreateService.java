package acme.features.inventor.chimpum;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.chimpum.Chimpum;
import acme.entities.item.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;
import acme.services.chimpum.ChimpumService;

@Service
@Transactional
public class InventorChimpumCreateService  implements AbstractCreateService<Inventor,Chimpum>{

	Item item;
	
	@Autowired
	protected ChimpumService service;
	
	@Override
	public boolean authorise(final Request<Chimpum> request) {
		return this.service.authoriseCreate(request, i->this.item = i);
	}
	
	@Override
	public void bind(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		this.service.bind(request, entity, errors);
//		this.service.setPrincipalAsOwner(entity);
		entity.setItem(this.item);
		entity.setCreationDate(LocalDateTime.now());
		
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		this.service.unbind(request, entity, model);
	}

	@Override
	public Chimpum instantiate(final Request<Chimpum> request) {
		final Chimpum res = new Chimpum();
		res.setItem(this.item);
		return res;
	}

	@Override
	public void validate(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		this.service.validate(request, entity, errors);
	}

	@Override
	public void create(final Request<Chimpum> request, final Chimpum entity) {	
//		entity.setCreationDate(LocalDateTime.now());
		this.service.save(entity);
	}

}
