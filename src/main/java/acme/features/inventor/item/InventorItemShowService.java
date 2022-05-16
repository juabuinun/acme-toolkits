package acme.features.inventor.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.item.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;
import acme.services.AuthoriseAll;
import acme.services.item.ItemService;

@Service
public class InventorItemShowService extends AuthoriseAll<Inventor,Item> implements AbstractShowService<Inventor,Item>{

	@Autowired
	protected ItemService service;

	@Override
	@Transactional
	public Item findOne(final Request<Item> request) {
		return this.service.findById(request);
	}

	@Override
	@Transactional
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		this.service.unbind(request, entity, model);
	}

}
