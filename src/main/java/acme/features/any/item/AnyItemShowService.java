package acme.features.any.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractShowService;
import acme.services.item.ItemService;

@Service
public class AnyItemShowService  implements AbstractShowService<Any,Item>{

	@Autowired
	protected ItemService service;

	@Override
	public Item findOne(final Request<Item> request) {
		return this.service.findById(request);
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		request.unbind(entity, model, "code", "name", "technology", "description", "price", "info");
		model.setAttribute("confirmation", false);
		model.setAttribute("readonly", true);
	}

	@Override
	public boolean authorise(final Request<Item> request) {
		return true;
	}

}
