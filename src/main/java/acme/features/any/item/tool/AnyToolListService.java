
package acme.features.any.item.tool;

import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.entities.item.Item.Type;
import acme.features.any.item.AbstractAnyItemListService;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;

@Service
public class AnyToolListService extends AbstractAnyItemListService implements AbstractListService<Any, Item> {

	@Override
	protected Type getItemType() {
		return Item.Type.TOOL;
	}

}
