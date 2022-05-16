
package acme.services.item;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.spam.SpamFilter;
import acme.entities.item.Item;
import acme.entities.item.Item.Type;
import acme.entities.toolkit.Toolkit;
import acme.entities.toolkit.item.ToolkitItem;
import acme.form.item.ItemDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.helpers.PrincipalHelper;
import acme.repositories.InventorRepository;
import acme.repositories.ItemRepository;
import acme.roles.Inventor;
import acme.services.AbstractCrudServiceImpl;
import acme.services.toolkit.ToolkitService;

@Service
public class ItemService extends AbstractCrudServiceImpl<Item, ItemRepository> {

	@Autowired
	protected InventorRepository	roleRepo;

	@Autowired
	protected ToolkitService		toolkitService;

	@Autowired
	protected SpamFilter			spamFilter;


	@Autowired
	protected ItemService(final ItemRepository repo, final ModelMapper mapper) {
		super(repo, mapper);
	}

	@Transactional
	public void validate(final Request<Item> request, final Item entity, final Errors errors) {
		this.spamFilter.filter(request, entity, errors);
		//TODO: VALIDATE MONEY
	}

	@Transactional
	public void unbindListingRecord(final Request<Item> request, final Item entity, final Model model) {
		request.unbind(entity, model, "code", "name", "technology", "price");
	}

	@Transactional
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		final ItemDto dto = this.mapper.map(entity, ItemDto.class);
		request.unbind(dto, model, "id", "version", "code", "name", "technology", "description", "price", "info");
	}

	@Transactional
	public void bind(final Request<Item> request, final Item entity, final Errors errors) {
		request.bind(entity, errors, "id", "version", "code", "name", "technology", "description", "price", "info");
		this.setPrincipalAsOwner(entity);
	}

	@Transactional
	public void setPrincipalAsOwner(final Item item) {
		final Inventor principal = this.roleRepo.findOneInventorByUserAccountId(PrincipalHelper.get().getAccountId());
		item.setOwner(principal);
	}
	
	@Transactional
	public List<Item> findByToolkit(final Request<Item> request, final Type itemType, final Boolean published) {
		return this.findByToolkit(this.toolkitService.findById(request.getModel().getInteger("masterId")), itemType, published);
	}

	@Transactional
	public List<Item> findByToolkit(final Toolkit kit, final Type itemType, final Boolean published) {
 		return kit.getItems().stream().map(ToolkitItem::getItem).filter(i -> (published != null?kit.isPublished() == published: Boolean.TRUE) && i.getItemType().equals(itemType)).collect(Collectors.toList());
	}
}
