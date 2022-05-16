package acme.features.any.item.component;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.Specifications;
import acme.entities.item.Item;
import acme.entities.item.Item.Type;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;
import acme.services.AuthoriseAll;
import acme.services.item.ItemService;

@Service
public class AnyComponentListService extends AuthoriseAll<Any,Item> implements AbstractListService<Any,Item>{

	@Autowired
	protected ItemService service;

	@Override
	@Transactional
	public Collection<Item> findMany(final Request<Item> request) {
		return this.service.findBySpecification(Specifications.itemIsOfType(Type.COMPONENT, true));
	}

	@Override
	@Transactional
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		this.service.unbindListingRecord(request, entity, model);
	}


}
