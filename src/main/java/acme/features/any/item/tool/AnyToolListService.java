
package acme.features.any.item.tool;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.entities.item.Item.Type;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;
import acme.repositories.ItemRepository;
import acme.services.toolkit.item.AbstractAnyItemListService;

@Service
public class AnyToolListService extends AbstractAnyItemListService implements AbstractListService<Any, Item> {

	protected AnyToolListService(final ItemRepository repo, final ModelMapper mapper) {
		super(repo, mapper, Type.TOOL);
	}


}
