package acme.features.inventor.item;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractShowService;
import acme.repositories.ItemRepository;
import acme.roles.Inventor;

@Service
public class InventorItemShowService implements AbstractShowService<Inventor,Item>{

	@Autowired
	protected ItemRepository repo;
	
	@Override
	public boolean authorise(final Request<Item> request) {
		return PrincipalHelper.get().hasRole(Inventor.class);
	}

	@Override
	public Item findOne(final Request<Item> request) {
		final Optional<Item> res = this.repo.findById(request.getModel().getInteger("id"));
		if(res.isPresent()) {
			return res.get();
		}else {
			return null;
		}
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		request.unbind(entity, model, "code", "name", "technology", "description", "price", "info");
	}

}
