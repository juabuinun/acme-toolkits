
package acme.features.any.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import acme.components.Specifications;
import acme.entities.item.Item;
import acme.entities.toolkit.Toolkit;
import acme.framework.controllers.Request;
import acme.repositories.ToolkitRepository;

public abstract class AbstractAnyItemInToolkitListService extends AbstractAnyItemListService {

	@Autowired
	protected ToolkitRepository toolkitRepo;


	@Override
	public Collection<Item> findMany(final Request<Item> request) {
		final Optional<Toolkit> kit = this.toolkitRepo.findById(request.getModel().getInteger("masterId"));
		if (kit.isPresent()) {
			return this.repo.findAll(Specifications.itemIsOfTypeByToolkit(this.getItemType(), kit.get()));
		} else {
			return new ArrayList<>();
		}
	}
}
