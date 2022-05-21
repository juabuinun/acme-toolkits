package acme.features.inventor.item.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.item.Item.Type;
import acme.features.inventor.item.AbstractInventorItemPublishService;
import acme.repositories.InventorRepository;
import acme.services.config.AcmeConfigurationService;
import acme.services.item.ItemService;

@Service
@Transactional
public class InventorToolPublishService extends AbstractInventorItemPublishService{

	@Autowired
	protected InventorToolPublishService(final ItemService service, final AcmeConfigurationService configService, final InventorRepository inventorRepo) {
		super(Type.TOOL, service, configService, inventorRepo);
	}


}
