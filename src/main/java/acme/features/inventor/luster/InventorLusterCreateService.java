package acme.features.inventor.luster;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.item.Item;
import acme.entities.luster.Luster;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;
import acme.services.luster.LusterService;

@Service
@Transactional
public class InventorLusterCreateService  implements AbstractCreateService<Inventor,Luster>{

	Item item;
	
	@Autowired
	protected LusterService service;
	
	@Override
	public boolean authorise(final Request<Luster> request) {
		return this.service.authoriseCreate(request, i->this.item = i);
	}
	
	@Override
	public void bind(final Request<Luster> request, final Luster entity, final Errors errors) {
		this.service.bind(request, entity, errors);
//		this.service.setPrincipalAsOwner(entity);
		entity.setItem(this.item);
		entity.setCreationDate(LocalDateTime.now());
		entity.setCode(this.service.generateCode());
		
	}

	@Override
	public void unbind(final Request<Luster> request, final Luster entity, final Model model) {
		this.service.unbind(request, entity, model);
	}

	@Override
	public Luster instantiate(final Request<Luster> request) {
		final Luster res = new Luster();
		res.setItem(this.item);
		return res;
	}

	@Override
	public void validate(final Request<Luster> request, final Luster entity, final Errors errors) {
		this.service.validate(request, entity, errors);
	}

	@Override
	public void create(final Request<Luster> request, final Luster entity) {	
//		entity.setCreationDate(LocalDateTime.now());
		this.service.save(entity);
	}

}
