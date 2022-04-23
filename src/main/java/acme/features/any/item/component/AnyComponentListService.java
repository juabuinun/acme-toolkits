package acme.features.any.item.component;

import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.entities.item.Item.Type;
import acme.features.any.item.AbstractAnyItemListService;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;

@Service
public class AnyComponentListService extends AbstractAnyItemListService implements AbstractListService<Any,Item>{

	@Override
	protected Type getItemType() {
		return Item.Type.COMPONENT;
	}

}
