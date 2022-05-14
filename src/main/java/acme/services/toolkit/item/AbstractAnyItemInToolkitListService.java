
package acme.services.toolkit.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import acme.components.Specifications;
import acme.entities.item.Item;
import acme.entities.item.Item.Type;
import acme.entities.toolkit.Toolkit;
import acme.framework.controllers.Request;
import acme.repositories.ItemRepository;
import acme.repositories.ToolkitRepository;

public abstract class AbstractAnyItemInToolkitListService extends AbstractAnyItemListService {


	protected AbstractAnyItemInToolkitListService(final ItemRepository repo, final ModelMapper mapper, final Type itemType) {
		super(repo, mapper, itemType);
	}


	@Autowired
	protected ToolkitRepository toolkitRepo;


	@Override
	public Collection<Item> findMany(final Request<Item> request) {
		final Optional<Toolkit> kit = this.toolkitRepo.findById(request.getModel().getInteger("masterId"));
		if (kit.isPresent()) {
			return this.findBySpecification(Specifications.itemIsOfTypeByToolkit(this.itemType, kit.get()));
		} else {
			return new ArrayList<>();
		}
	}
}
