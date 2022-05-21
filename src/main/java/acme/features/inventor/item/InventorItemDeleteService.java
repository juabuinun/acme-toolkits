
package acme.features.inventor.item;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.util.BindHelper;
import acme.entities.item.Item;
import acme.form.item.ItemDto;
import acme.form.item.SaveItemDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.repositories.InventorRepository;
import acme.roles.Inventor;
import acme.services.AuthoriseOwner;
import acme.services.item.ItemService;

@Service
@Transactional
public class InventorItemDeleteService extends AuthoriseOwner<Inventor, Item> implements AbstractDeleteService<Inventor, Item> {

	@Autowired
	protected ModelMapper			mapper;

	@Autowired
	protected ItemService			service;

	@Autowired
	protected InventorRepository	inventorRepo;


	protected InventorItemDeleteService() {
		super("owner");
	}

	@Override
	public boolean authorise(final Request<Item> request) {
		this.entity = this.service.findById(request);
		return this.entity != null && !this.entity.isPublished() && super.authorise(request);
	}

	@Override
	public void bind(final Request<Item> request, final Item entity, final Errors errors) {
		this.mapper.map(this.entity, entity);
		request.bind(entity, errors, BindHelper.getAllFieldNames(SaveItemDto.class));
	}

	@Override
	public Item findOne(final Request<Item> request) {
		return this.entity;
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		request.unbind(entity, model, BindHelper.getAllFieldNames(ItemDto.class));
		model.setAttribute("draftMode", !entity.isPublished());
	}

	@Override
	public void validate(final Request<Item> request, final Item entity, final Errors errors) {
		//do nothing
	}

	@Override
	public void delete(final Request<Item> request, final Item entity) {
		this.service.delete(entity.getId());
	}

}
