package acme.features.any.item;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.components.Specifications;
import acme.entities.item.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;
import acme.repositories.ItemRepository;


public abstract class AbstractAnyItemListService implements AbstractListService<Any,Item>{

	@Autowired
	ItemRepository repo;
	
	@Override
	public boolean authorise(final Request<Item> request) {
		return true;
	}

	@Override
	public Collection<Item> findMany(final Request<Item> request) {
		return this.repo.findAll(Specifications.itemIsOfType(this.getItemType()));
	}
	
	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		request.unbind(entity, model, "code","name","technology","price");
	}

	protected abstract Item.Type getItemType();
}
