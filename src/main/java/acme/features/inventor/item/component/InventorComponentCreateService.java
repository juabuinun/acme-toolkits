package acme.features.inventor.item.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;
import acme.services.AuthoriseAll;
import acme.services.item.ItemService;

@Service
public class InventorComponentCreateService extends AuthoriseAll<Inventor,Item> implements AbstractCreateService<Inventor,Item>{

	@Autowired
	protected ItemService service;

	@Override
	public void create(final Request<Item> request, final Item entity) {
		this.service.save(entity);
	}

	@Override
	public void bind(final Request<Item> request, final Item entity, final Errors errors) {
		this.service.bind(request, entity, errors);
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		this.service.unbind(request, entity, model);
	}

	@Override
	public Item instantiate(final Request<Item> request) {
		return new Item();
	}

	@Override
	public void validate(final Request<Item> request, final Item entity, final Errors errors) {
		this.service.validate(request, entity, errors);
	}

}
