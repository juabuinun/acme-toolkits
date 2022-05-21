
package acme.features.inventor.item;

import acme.components.util.BindHelper;
import acme.entities.item.Item;
import acme.entities.item.Item.Type;
import acme.form.item.ItemDto;
import acme.form.item.SaveItemDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractCreateService;
import acme.repositories.InventorRepository;
import acme.roles.Inventor;
import acme.services.AuthoriseAll;
import acme.services.config.AcmeConfigurationService;
import acme.services.item.ItemService;

public abstract class AbstractInventorItemCreateService extends AuthoriseAll<Inventor, Item> implements AbstractCreateService<Inventor, Item> {

	protected Type						itemType;

	protected ItemService				service;

	protected AcmeConfigurationService	configService;

	protected InventorRepository		inventorRepo;


	protected AbstractInventorItemCreateService(final Type itemType, final ItemService service, final AcmeConfigurationService configService, final InventorRepository inventorRepo) {
		super();
		this.itemType = itemType;
		this.service = service;
		this.configService = configService;
		this.inventorRepo = inventorRepo;
	}

	@Override
	public void create(final Request<Item> request, final Item entity) {
		entity.setPublished(false);
		this.service.save(entity);
	}

	@Override
	public void bind(final Request<Item> request, final Item entity, final Errors errors) {
		entity.setItemType(this.itemType);
		entity.setOwner(this.inventorRepo.findOneInventorByUserAccountId(PrincipalHelper.get().getAccountId()));
		request.bind(entity, errors, BindHelper.getAllFieldNames(SaveItemDto.class));	
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		entity.setItemType(this.itemType);
		request.unbind(entity, model, BindHelper.getAllFieldNames(ItemDto.class));
		model.setAttribute("draftMode", !entity.isPublished());
	}

	@Override
	public Item instantiate(final Request<Item> request) {
		final Item res = new Item();
		final Money price = new Money();
		price.setCurrency(this.configService.getDefaultCurrency());
		price.setAmount(0d);
		res.setPrice(price);
		return res;
	}

	@Override
	public void validate(final Request<Item> request, final Item entity, final Errors errors) {
		this.configService.filter(request, entity, errors);
		this.configService.checkMoney(request, errors, entity.getPrice(), "price");
	}
}
