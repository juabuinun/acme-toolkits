package acme.features.inventor.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.util.BindHelper;
import acme.entities.item.Item;
import acme.form.item.ItemDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;
import acme.services.AbstractAuthoriseAll;
import acme.services.item.ItemService;

@Service
public class InventorItemShowService extends AbstractAuthoriseAll<Inventor,Item> implements AbstractShowService<Inventor,Item>{

	@Autowired
	protected ItemService service;

	@Override
	@Transactional
	public Item findOne(final Request<Item> request) {
		return this.service.findById(request);
	}

	@Override
	@Transactional
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		request.unbind(entity, model, BindHelper.getAllFieldNames(ItemDto.class));
		model.setAttribute("draftMode", !entity.isPublished());
	}

}
