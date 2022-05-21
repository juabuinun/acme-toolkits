package acme.features.inventor.item.tool;

import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.Specifications;
import acme.components.util.BindHelper;
import acme.entities.item.Item;
import acme.form.item.BasicItemDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;
import acme.services.AuthoriseAll;
import acme.services.item.ItemService;

@Service
public class InventorToolListService extends AuthoriseAll<Inventor,Item> implements AbstractListService<Inventor,Item>{

	@Autowired
	protected ItemService service;
	
	@Autowired
	protected ModelMapper mapper;
	
	@Override
	@Transactional
	public Collection<Item> findMany(final Request<Item> request) {
		return this.service.findBySpecification(Specifications.itemIsOfTypeAndPrincipalIsOwner(Item.Type.TOOL));
	}


	@Override
	@Transactional
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		request.unbind(this.mapper.map(entity, BasicItemDto.class), model, BindHelper.getAllFieldNames(BasicItemDto.class));
	}



}
