package acme.features.inventor.item;

import acme.entities.item.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

public abstract class AbstractInventorItemListService implements AbstractListService<Inventor,Item>{

	@Override
	public boolean authorise(final Request<Item> request) {
		return PrincipalHelper.get().hasRole(Inventor.class);
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		request.unbind(entity, model, "code","name","technology","price");
	}

}
