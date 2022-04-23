package acme.features.inventor.item;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.Specifications;
import acme.entities.item.Item;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.repositories.ItemRepository;
import acme.roles.Inventor;

@Service
public class InventorToolListService extends AbstractInventorItemListService implements AbstractListService<Inventor,Item>{

	@Autowired
	protected ItemRepository repo;
	


	@Override
	public Collection<Item> findMany(final Request<Item> request) {
		return this.repo.findAll(Specifications.itemIsOfTypeAndPrincipalIsOwner(Item.Type.TOOL));
	}



}
