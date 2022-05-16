package acme.features.inventor.item.tool;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.item.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.repositories.InventorRepository;
import acme.roles.Inventor;
import acme.services.AuthoriseOwner;
import acme.services.item.ItemService;

public class InventorToolPublishService extends AuthoriseOwner<Inventor,InventorRepository, Item> implements AbstractUpdateService<Inventor,Item>{

	@Autowired
	ItemService service;
	
	@Autowired
	protected InventorToolPublishService(final InventorRepository roleRepo, final ModelMapper mapper) {
		super(roleRepo, mapper, "owner");
	}

	@Override
	public void bind(final Request<Item> request, final Item entity, final Errors errors) {
		this.service.bind(request, entity, errors);
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		this.service.unbind(request, entity, model);
	}

	@Override
	public Item findOne(final Request<Item> request) {
		return this.service.findById(request);
	}

	@Override
	public void validate(final Request<Item> request, final Item entity, final Errors errors) {
		this.service.validate(request, entity, errors);
	}

	@Override
	public void update(final Request<Item> request, final Item entity) {
		entity.setPublished(true);
		this.service.save(entity);
	}

}
