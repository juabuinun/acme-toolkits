
package acme.features.inventor.item;

import org.modelmapper.ModelMapper;

import acme.entities.item.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.repositories.ItemRepository;
import acme.roles.Inventor;
import acme.services.AbstractAcmeAuthoriseAllService;

public abstract class AbstractInventorItemListService extends AbstractAcmeAuthoriseAllService<Inventor, Item, ItemRepository> implements AbstractListService<Inventor, Item> {

	
	protected AbstractInventorItemListService(final ItemRepository repo, final ModelMapper mapper) {
		super(repo, mapper);
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		request.unbind(entity, model, "code", "name", "technology", "price");
	}

}
