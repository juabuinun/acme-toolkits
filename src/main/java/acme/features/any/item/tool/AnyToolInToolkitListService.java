package acme.features.any.item.tool;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.item.Item.Type;
import acme.features.any.item.AbstractAnyItemInToolkitService;
import acme.repositories.ToolkitItemRepository;

@Service
@Transactional
public class AnyToolInToolkitListService extends AbstractAnyItemInToolkitService{

	@Autowired
	protected AnyToolInToolkitListService(final ToolkitItemRepository repo, final ModelMapper mapper) {
		super(Type.TOOL, repo, mapper);
	}

	

}
