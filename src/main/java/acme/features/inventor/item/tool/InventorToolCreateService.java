
package acme.features.inventor.item.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.item.Item.Type;
import acme.features.inventor.item.AbstractInventorItemCreateService;
import acme.repositories.InventorRepository;
import acme.services.config.AcmeConfigurationService;
import acme.services.item.ItemService;

@Service
@Transactional
public class InventorToolCreateService extends AbstractInventorItemCreateService {

	@Autowired
	public InventorToolCreateService(final ItemService service, final AcmeConfigurationService configService, final InventorRepository inventorRepo) {
		super(Type.TOOL, service, configService, inventorRepo);
	}

}
