
package acme.features.inventor.item.component;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Item;
import acme.features.inventor.item.AbstractInventorItemListService;
import acme.services.item.ItemService;

@Service
public class InventorComponentListService extends AbstractInventorItemListService {

	@Autowired
	protected InventorComponentListService(final ItemService service, final ModelMapper mapper) {
		super(service, Item.Type.COMPONENT, mapper);
	}


}
