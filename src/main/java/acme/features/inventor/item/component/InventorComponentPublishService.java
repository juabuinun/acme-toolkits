package acme.features.inventor.item.component;

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
public class InventorComponentPublishService extends AbstractInventorItemPublishService{

	@Autowired
	protected InventorComponentPublishService(final ItemService service, final AcmeConfigurationService configService, final InventorRepository inventorRepo) {
		super(Type.COMPONENT, service, configService, inventorRepo);
	}

}
