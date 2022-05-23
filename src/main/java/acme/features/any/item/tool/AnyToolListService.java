
package acme.features.any.item.tool;

import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.Specifications;
import acme.components.util.BindHelper;
import acme.entities.item.Item;
import acme.entities.item.Item.Type;
import acme.form.item.BasicItemDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;
import acme.services.AbstractAuthoriseAll;
import acme.services.item.ItemService;

@Service
public class AnyToolListService extends AbstractAuthoriseAll<Any,Item> implements AbstractListService<Any, Item> {

	@Autowired
	protected ItemService service;
	
	@Autowired
	protected ModelMapper mapper;

	@Override
	@Transactional
	public Collection<Item> findMany(final Request<Item> request) {
		return this.service.findBySpecification(Specifications.itemIsOfType(Type.TOOL, true));
	}

	@Override
	@Transactional
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		request.unbind(this.mapper.map(entity, BasicItemDto.class), model, BindHelper.getAllFieldNames(BasicItemDto.class));
	}


}
