package acme.features.any.toolkits;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.Specifications;
import acme.entities.item.Item;
import acme.entities.toolkit.Toolkit;
import acme.form.toolkit.ToolkitDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;
import acme.repositories.ItemRepository;
import acme.repositories.ToolkitRepository;

@Service
public class AnyToolkitWithItemListService implements AbstractListService<Any,Toolkit>{

	@Autowired
	protected ModelMapper mapper;
	@Autowired
	protected ToolkitRepository repo;
	@Autowired
	protected ItemRepository itemRepo;
	
	@Override
	public boolean authorise(final Request<Toolkit> request) {
		return true;
	}

	@Override
	public Collection<Toolkit> findMany(final Request<Toolkit> request) {
		Collection<Toolkit> res = new ArrayList<>();
		final Optional<Item> item = this.itemRepo.findById(request.getModel().getInteger("itemId"));
		if(item.isPresent()) {
			res = this.repo.findAll(Specifications.toolkitHasItem(item.get()));
		}
		return res;
	}

	@Override
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		final ToolkitDto dto = this.mapper.map(entity, ToolkitDto.class);
		request.unbind(dto, model, "id","version","code","title","price");
	}

}
