package acme.features.any.item.component;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.entities.item.Item.Type;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;
import acme.repositories.ItemRepository;
import acme.services.toolkit.item.AbstractAnyItemListService;

@Service
public class AnyComponentListService extends AbstractAnyItemListService implements AbstractListService<Any,Item>{

	protected AnyComponentListService(final ItemRepository repo, final ModelMapper mapper) {
		super(repo, mapper, Type.COMPONENT);
	}


}
