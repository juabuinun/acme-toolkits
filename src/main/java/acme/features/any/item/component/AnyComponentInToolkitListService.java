package acme.features.any.item.component;

import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.entities.item.Item.Type;
import acme.features.any.item.AbstractAnyItemInToolkitListService;

@Service
public class AnyComponentInToolkitListService extends AbstractAnyItemInToolkitListService{

	@Override
	protected Type getItemType() {
		return Item.Type.COMPONENT;
	}

}
