
package acme.services.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.item.Item;
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

}
