
package acme.features.inventor.item;

import org.springframework.transaction.annotation.Transactional;

import acme.components.util.BindHelper;
import acme.entities.item.Item;
import acme.entities.item.Item.Type;
import acme.form.item.ItemDto;
import acme.form.item.SaveItemDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractUpdateService;
import acme.repositories.InventorRepository;
import acme.roles.Inventor;
import acme.services.AuthoriseOwner;
import acme.services.config.AcmeConfigurationService;
import acme.services.item.ItemService;

/**
 * Has to be abstract bc cant fetch item type from post
 *
 */
public abstract class AbstractInventorItemPublishService extends AuthoriseOwner<Inventor, Item> implements AbstractUpdateService<Inventor, Item> {

	Type								itemType;

	protected ItemService				service;

	protected AcmeConfigurationService	configService;

	protected InventorRepository		inventorRepo;


	protected AbstractInventorItemPublishService(final Type itemType, final ItemService service, final AcmeConfigurationService configService, final InventorRepository inventorRepo) {
		super("owner");
		this.itemType = itemType;
		this.service = service;
		this.configService = configService;
		this.inventorRepo = inventorRepo;
	}

	@Override
	@Transactional
	public boolean authorise(final Request<Item> request) {
		this.entity = this.service.findById(request);
		return this.itemType != null && !this.entity.isPublished() && this.entity.getItemType().equals(this.itemType) && super.authorise(request);
	}

	@Override
	@Transactional
	public void bind(final Request<Item> request, final Item entity, final Errors errors) {
		entity.setItemType(this.itemType);
		entity.setOwner(this.inventorRepo.findOneInventorByUserAccountId(PrincipalHelper.get().getAccountId()));
		request.bind(entity, errors, BindHelper.getAllFieldNames(SaveItemDto.class));
	}

	@Override
	@Transactional
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		request.unbind(entity, model, BindHelper.getAllFieldNames(ItemDto.class));
		model.setAttribute("draftMode", !entity.isPublished());
	}

	@Override
	@Transactional
	public Item findOne(final Request<Item> request) {
		return this.entity;
	}

	@Override
	@Transactional
	public void validate(final Request<Item> request, final Item entity, final Errors errors) {
		this.configService.filter(request, entity, errors);
	}

	@Override
	public void update(final Request<Item> request, final Item entity) {
		entity.setPublished(true);
		this.service.save(entity);
	}

}
