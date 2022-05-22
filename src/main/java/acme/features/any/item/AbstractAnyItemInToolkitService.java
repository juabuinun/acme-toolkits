
package acme.features.any.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;

import acme.entities.item.Item;
import acme.entities.item.Item.Type;
import acme.entities.toolkititem.ToolkitItem;
import acme.form.toolkititem.PatchToolkitItemDto;
import acme.form.toolkititem.ToolkitItemDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;
import acme.repositories.ToolkitItemRepository;
import acme.services.AuthoriseAll;

public abstract class AbstractAnyItemInToolkitService extends AuthoriseAll<Any, Item> implements AbstractListService<Any, Item> {

	protected Type itemType;
	
	protected ToolkitItemRepository	repo;

	protected ModelMapper mapper;
	
	protected int					counter	= 0;

	List<ToolkitItem>				items;

	

	protected AbstractAnyItemInToolkitService(final Type itemType, final ToolkitItemRepository repo, final ModelMapper mapper) {
		super();
		this.itemType = itemType;
		this.repo = repo;
		this.mapper = mapper;
		this.counter = 0;
		this.items = new ArrayList<>();
	}

	@Override
	public Collection<Item> findMany(final Request<Item> request) {
		this.items.addAll(this.repo.findByToolkit_idAndItem_itemType(request.getModel().getInteger("masterId"), this.itemType));
		final List<Item> res = new ArrayList<>();
		for(int i=0;i<this.items.size();i++) {
			res.add(null);
		}
		return res;
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		final ToolkitItemDto tiDto = this.mapper.map(this.items.get(this.counter), ToolkitItemDto.class);
		final PatchToolkitItemDto dto = new PatchToolkitItemDto();
		dto.setId(tiDto.getItem().getId());
		dto.setVersion(tiDto.getItem().getVersion());
		dto.setCode(tiDto.getItem().getCode());
		dto.setName(tiDto.getItem().getName());
		dto.setTechnology(tiDto.getItem().getTechnology());
		dto.setPrice(tiDto.getItem().getPrice());
		dto.setQuantity(tiDto.getQuantity());
		request.unbind(dto, model, "id","version","code","price","quantity","name","technology");
	}

}
