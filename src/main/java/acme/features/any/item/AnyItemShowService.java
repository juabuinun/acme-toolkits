package acme.features.any.item;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractShowService;
import acme.repositories.ItemRepository;
import acme.services.AbstractAcmeAuthoriseAllService;

@Service
public class AnyItemShowService extends AbstractAcmeAuthoriseAllService<Any,Item,ItemRepository> implements AbstractShowService<Any,Item>{

	@Autowired
	protected AnyItemShowService(final ItemRepository repo, final ModelMapper mapper) {
		super(repo, mapper);
	}

	@Override
	public Item findOne(final Request<Item> request) {
		return this.findById(request);
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		request.unbind(entity, model, "code", "name", "technology", "description", "price", "info");
		model.setAttribute("confirmation", false);
		model.setAttribute("readonly", true);
	}

}
