
package acme.features.inventor.item.tool;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.features.inventor.item.AbstractInventorItemListService;
import acme.services.item.ItemService;

@Service
public class InventorToolListService extends AbstractInventorItemListService {

	@Autowired
	protected InventorToolListService(final ItemService service, final ModelMapper mapper) {
		super(service, Item.Type.TOOL, mapper);
	}

}
