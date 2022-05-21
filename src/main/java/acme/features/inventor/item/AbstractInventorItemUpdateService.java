
package acme.features.inventor.item;

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

public abstract class AbstractInventorItemUpdateService extends AuthoriseOwner<Inventor, Item> implements AbstractUpdateService<Inventor, Item> {

	protected Type						itemType;

	protected ItemService				service;

	protected AcmeConfigurationService	configService;

	protected InventorRepository		inventorRepo;


	protected AbstractInventorItemUpdateService(final Type itemType, final ItemService service, final AcmeConfigurationService configService, final InventorRepository inventorRepo) {
		super("owner");
		this.itemType = itemType;
		this.service = service;
		this.configService = configService;
		this.inventorRepo = inventorRepo;
	}

	@Override
	public boolean authorise(final Request<Item> request) {
		this.entity = this.service.findById(request);
		return this.entity != null && !this.entity.isPublished() && this.entity.getItemType().equals(this.itemType) && super.authorise(request);
	}

	@Override
	public void bind(final Request<Item> request, final Item entity, final Errors errors) {
		entity.setItemType(this.itemType);
		entity.setOwner(this.inventorRepo.findOneInventorByUserAccountId(PrincipalHelper.get().getAccountId()));
		request.bind(entity, errors, BindHelper.getAllFieldNames(SaveItemDto.class));
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		request.unbind(entity, model, BindHelper.getAllFieldNames(ItemDto.class));
		model.setAttribute("draftMode", !entity.isPublished());
	}

	@Override
	public void validate(final Request<Item> request, final Item entity, final Errors errors) {
		this.configService.filter(request, entity, errors);
		this.configService.checkMoney(request, errors, entity.getPrice(), "price");
	}

	@Override
	public Item findOne(final Request<Item> request) {
		return this.entity;
	}

	@Override
	public void update(final Request<Item> request, final Item entity) {
		this.service.save(entity);
	}
}
