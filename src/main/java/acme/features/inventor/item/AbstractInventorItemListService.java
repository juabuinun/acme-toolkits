
package acme.features.inventor.item;

import java.util.Collection;

import org.modelmapper.ModelMapper;

import acme.components.Specifications;
import acme.components.util.BindHelper;
import acme.entities.item.Item;
import acme.entities.item.Item.Type;
import acme.form.item.BasicItemDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;
import acme.services.AbstractAuthoriseAll;
import acme.services.item.ItemService;

public abstract class AbstractInventorItemListService extends AbstractAuthoriseAll<Inventor, Item> implements AbstractListService<Inventor, Item> {

	protected ItemService	service;

	protected Type			itemType;

	protected ModelMapper	mapper;


	protected AbstractInventorItemListService(final ItemService service, final Type itemType, final ModelMapper mapper) {
		super();
		this.service = service;
		this.itemType = itemType;
		this.mapper = mapper;
	}

	@Override
	public Collection<Item> findMany(final Request<Item> request) {
		return this.service.findBySpecification(Specifications.itemIsOfTypeAndPrincipalIsOwner(this.itemType));
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		request.unbind(entity, model, BindHelper.getAllFieldNames(BasicItemDto.class));
	}

}
