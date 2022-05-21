package acme.features.inventor.toolkit;

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
import acme.form.toolkit.DetailToolkitDto;
import acme.form.toolkititem.ToolkitItemDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor;
import acme.services.AuthoriseOwner;
import acme.services.toolkit.ToolkitService;

@Service
@Transactional
public class InventorToolkitDeleteService extends AuthoriseOwner<Inventor,Toolkit> implements AbstractDeleteService<Inventor,Toolkit>{

	@Autowired
	protected ModelMapper mapper;
	
	@Autowired
	protected ToolkitService service;
	
	protected InventorToolkitDeleteService() {
		super("owner");
	}

	@Override
	public boolean authorise(final Request<Toolkit> request) {
		this.entity = this.service.findById(request);
		return this.entity != null && !this.entity.isPublished() && super.authorise(request);
	}

	@Override
	public void bind(final Request<Toolkit> request, final Toolkit entity, final Errors errors) {
		//do nothing
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
		//do nothing
	}

	@Override
	public void delete(final Request<Toolkit> request, final Toolkit entity) {
		this.service.delete(entity.getId());
	}

}
