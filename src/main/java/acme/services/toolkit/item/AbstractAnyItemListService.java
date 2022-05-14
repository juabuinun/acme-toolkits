package acme.services.toolkit.item;

import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import acme.components.Specifications;
import acme.entities.item.Item;
import acme.entities.item.Item.Type;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;
import acme.repositories.ItemRepository;
import acme.services.AbstractCrudServiceImpl;


public abstract class AbstractAnyItemListService extends AbstractCrudServiceImpl<Item, ItemRepository> implements AbstractListService<Any,Item>{

	Type itemType;
	
	@Autowired
	protected AbstractAnyItemListService(final ItemRepository repo, final ModelMapper mapper, final Type itemType) {
		super(repo, mapper);
		this.itemType = itemType;
	}
	
	@Override
	public boolean authorise(final Request<Item> request) {
		return true;
	}

	@Override
	public Collection<Item> findMany(final Request<Item> request) {
		return this.findBySpecification(Specifications.itemIsOfType(this.itemType));
	}
	
	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		request.unbind(entity, model, "code","name","technology","price");
	}

}
