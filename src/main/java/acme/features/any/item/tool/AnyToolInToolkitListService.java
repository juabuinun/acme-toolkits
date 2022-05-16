package acme.features.any.item.tool;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.item.Item;
import acme.entities.item.Item.Type;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;
import acme.services.AuthoriseAll;
import acme.services.item.ItemService;

@Service
public class AnyToolInToolkitListService extends AuthoriseAll<Any,Item> implements AbstractListService<Any, Item>{

	@Autowired
	protected ItemService service;

	@Override
	@Transactional
	public Collection<Item> findMany(final Request<Item> request) {
		return this.service.findByToolkit(request, Type.TOOL, true);
	}

	@Override
	@Transactional
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		this.service.unbindListingRecord(request, entity, model);
	}

}
