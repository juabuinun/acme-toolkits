
package acme.features.inventor.toolkit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.util.BindHelper;
import acme.entities.item.Item.Type;
import acme.entities.toolkit.Toolkit;
import acme.entities.toolkititem.ToolkitItem;
import acme.form.toolkit.DetailToolkitDto;
import acme.form.toolkit.SaveToolkitDto;
import acme.form.toolkititem.ToolkitItemDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;
import acme.services.AuthoriseOwner;
import acme.services.config.AcmeConfigurationService;
import acme.services.toolkit.ToolkitService;

@Service
@Transactional
public class InventorToolkitUpdateService extends AuthoriseOwner<Inventor, Toolkit> implements AbstractUpdateService<Inventor, Toolkit> {

	List<ToolkitItem> items = new ArrayList<>();
	
	@Autowired
	protected ToolkitService			service;

	@Autowired
	protected ModelMapper				mapper;

	@Autowired
	protected AcmeConfigurationService	configService;


	@Autowired
	protected InventorToolkitUpdateService() {
		super("owner");
	}

	@Override
	public boolean authorise(final Request<Toolkit> request) {
		this.entity = this.service.findById(request);
		return super.authorise(request) && this.entity != null && !this.entity.isPublished();
	}

	@Override
	public void bind(final Request<Toolkit> request, final Toolkit entity, final Errors errors) {
		request.bind(entity, errors,BindHelper.getAllFieldNames(SaveToolkitDto.class));
		final List<ToolkitItem> toolkitItems = new ArrayList<>();
		try {
			this.items.addAll(this.service.jsonArrayToToolkitItemList(entity,request.getModel().getString("binded_components_input"),"component_"));
			this.items.addAll(this.service.jsonArrayToToolkitItemList(entity,request.getModel().getString("binded_tools_input"),"tool_"));
		}catch(final Exception e) {
			
		}
		entity.setItems(toolkitItems);
	}

	@Override
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		final DetailToolkitDto dto = this.mapper.map(entity, DetailToolkitDto.class);
		
		dto.setAvailableComponents(this.service.findAvaliableItems(entity, Type.COMPONENT));
		dto.setBindedComponents(this.mapper.map(entity.getItems().stream().filter(i -> i.getItem().getItemType().equals(Type.COMPONENT)).collect(Collectors.toList()), new TypeToken<List<ToolkitItemDto>>() {
		}.getType()));

		dto.setAvailableTools(this.service.findAvaliableItems(entity, Type.TOOL));
		dto.setBindedTools(this.mapper.map(entity.getItems().stream().filter(i -> i.getItem().getItemType().equals(Type.TOOL)).collect(Collectors.toList()), new TypeToken<List<ToolkitItemDto>>() {
		}.getType()));

		request.unbind(dto, model, BindHelper.getAllFieldNames(DetailToolkitDto.class));

		model.setAttribute("draftMode", !entity.isPublished());
	}

	@Override
	public Toolkit findOne(final Request<Toolkit> request) {
		return this.entity;
	}

	@Override
	public void validate(final Request<Toolkit> request, final Toolkit entity, final Errors errors) {
		this.configService.filter(request, entity, errors);
	}

	@Override
	public void update(final Request<Toolkit> request, final Toolkit entity) {
		entity.setItems(this.items);
		this.service.save(entity);
	}

}
