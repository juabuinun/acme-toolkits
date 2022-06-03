package acme.features.inventor.luster;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.item.Item;
import acme.entities.item.Item.Type;
import acme.entities.luster.Luster;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;
import acme.services.item.ItemService;
import acme.services.luster.LusterService;

@Service
@Transactional
public class InventorLusterListByItemService implements AbstractListService<Inventor,Luster>{

	private static final Logger	logger	= LoggerFactory.getLogger(InventorLusterListByItemService.class);
	
	@Autowired
	protected LusterService service;
	
	@Autowired
	protected ItemService itemService;
	
	protected Item item;
	
	@Override
	public boolean authorise(final Request<Luster> request) {
		boolean res = false;
		try {
			final int id = request.getModel().getInteger("itemId");
			this.item = this.itemService.findById(id);
			res = this.item != null && Boolean.TRUE.equals(this.item.isPublished()) && this.item.getItemType().equals(Type.COMPONENT) && this.item.getOwner().getUserAccount().getId() == PrincipalHelper.get().getAccountId();
		} catch (final Exception e) {
			// do nothing
			InventorLusterListByItemService.logger.error("Error authorising list by item",e);
		}
		return res;
	}

	@Override
	public Collection<Luster> findMany(final Request<Luster> request) {
		return this.item.getChimpums();
	}

	@Override
	public void unbind(final Request<Luster> request, final Luster entity, final Model model) {
		this.service.unbind(request, entity, model);
	}

}
