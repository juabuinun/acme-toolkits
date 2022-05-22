package acme.features.any.item.component;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.item.Item.Type;
import acme.features.any.item.AbstractAnyItemInToolkitService;
import acme.repositories.ToolkitItemRepository;

@Service
@Transactional
public class AnyComponentInToolkitListService extends AbstractAnyItemInToolkitService{

	@Autowired
	protected AnyComponentInToolkitListService(final ToolkitItemRepository repo, final ModelMapper mapper) {
		super(Type.COMPONENT, repo, mapper);
	}


}
