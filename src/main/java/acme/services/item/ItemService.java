
package acme.services.item;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.item.Item;
import acme.entities.item.Item.Type;
import acme.entities.toolkit.Toolkit;
import acme.entities.toolkititem.ToolkitItem;
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
	protected ItemService(final ItemRepository repo) {
		super(repo);
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
