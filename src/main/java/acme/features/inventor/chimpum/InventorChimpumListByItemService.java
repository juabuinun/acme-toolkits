package acme.features.inventor.chimpum;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.chimpum.Chimpum;
import acme.entities.item.Item;
import acme.entities.item.Item.Type;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;
import acme.services.chimpum.ChimpumService;
import acme.services.item.ItemService;

@Service
@Transactional
public class InventorChimpumListByItemService implements AbstractListService<Inventor,Chimpum>{

	private static final Logger	logger	= LoggerFactory.getLogger(InventorChimpumListByItemService.class);
	
	@Autowired
	protected ChimpumService service;
	
	@Autowired
	protected ItemService itemService;
	
	protected Item item;
	
	@Override
	public boolean authorise(final Request<Chimpum> request) {
		boolean res = false;
		try {
			final int id = request.getModel().getInteger("itemId");
			this.item = this.itemService.findById(id);
			res = this.item != null && Boolean.TRUE.equals(this.item.isPublished()) && this.item.getItemType().equals(Type.COMPONENT) && this.item.getOwner().getUserAccount().getId() == PrincipalHelper.get().getAccountId();
		} catch (final Exception e) {
			// do nothing
			InventorChimpumListByItemService.logger.error("Error authorising list by item",e);
		}
		return res;
	}

	@Override
	public Collection<Chimpum> findMany(final Request<Chimpum> request) {
		return this.item.getChimpums();
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		this.service.unbind(request, entity, model);
	}

}
