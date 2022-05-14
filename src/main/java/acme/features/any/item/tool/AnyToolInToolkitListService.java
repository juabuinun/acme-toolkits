package acme.features.any.item.tool;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import acme.entities.item.Item.Type;
import acme.repositories.ItemRepository;
import acme.services.toolkit.item.AbstractAnyItemInToolkitListService;

@Service
public class AnyToolInToolkitListService extends AbstractAnyItemInToolkitListService{

	protected AnyToolInToolkitListService(final ItemRepository repo, final ModelMapper mapper) {
		super(repo, mapper, Type.TOOL);
	}

}
