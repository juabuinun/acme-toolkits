package acme.services.toolkit;



import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.Specifications;
import acme.entities.item.Item;
import acme.entities.toolkit.Toolkit;
import acme.form.toolkit.ToolkitDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.repositories.ToolkitRepository;
import acme.services.AbstractCrudServiceImpl;
import acme.services.item.ItemService;

@Service
public class ToolkitService extends AbstractCrudServiceImpl<Toolkit,ToolkitRepository>{

	@Autowired
	protected ItemService itemService;
	
	@Autowired
	protected ToolkitService(final ToolkitRepository repo, final ModelMapper mapper) {
		super(repo, mapper);
	}
	
	@Transactional
	public List<Toolkit> findAllPublished(){
		return this.repo.findByPublished(true);
	}
	
	@Transactional
	public List<Toolkit> findByItem(final Request<Toolkit> request, final Boolean published){
		return this.findByItem(request.getModel().getInteger("itemId"), published);
	}
	
	@Transactional
	public List<Toolkit> findByItem(final int itemId, final Boolean published){
		final Item item = this.itemService.findById(itemId);
		if(item != null) {
			return this.repo.findAll(Specifications.toolkitHasItem(item,published));
		}else {
			return  new ArrayList<>();
		}
	}
	
	@Transactional
	public void unbindListingRecord(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		final ToolkitDto dto = this.mapper.map(entity, ToolkitDto.class);
		request.unbind(dto, model, "id","version","code","title","price");
	}
	
	@Transactional
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		final ToolkitDto dto = this.mapper.map(entity, ToolkitDto.class);
		request.unbind(dto, model,"id","version","code","title","price","description","notes","info");
	}

}
