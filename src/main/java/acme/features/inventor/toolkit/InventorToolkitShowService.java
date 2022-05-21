
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
import acme.form.toolkit.ToolkitDto;
import acme.form.toolkititem.ToolkitItemDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;
import acme.services.AuthoriseOwner;
import acme.services.toolkit.ToolkitService;

@Service
public class InventorToolkitShowService extends AuthoriseOwner<Inventor, Toolkit> implements AbstractShowService<Inventor, Toolkit> {

	@Autowired
	protected ModelMapper		mapper;

	@Autowired
	protected ToolkitService	service;


	@Autowired
	protected InventorToolkitShowService() {
		super("owner");
	}

	@Override
	@Transactional
	public boolean authorise(final Request<Toolkit> request) {
		this.entity = this.service.findById(request);
		return super.authorise(request);
	}

	@Override
	@Transactional
	public Toolkit findOne(final Request<Toolkit> request) {
		return this.entity;
	}

	@Override
	@Transactional
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		if (entity.isPublished()) {
			request.unbind(this.mapper.map(entity, ToolkitDto.class), model, BindHelper.getAllFieldNames(ToolkitDto.class));
		} else {
			final DetailToolkitDto dto = this.mapper.map(entity, DetailToolkitDto.class);

			dto.setAvailableComponents(this.service.findAvaliableItems(entity, Type.COMPONENT));
			dto.setBindedComponents(this.mapper.map(entity.getItems().stream().filter(i -> i.getItem().getItemType().equals(Type.COMPONENT)).collect(Collectors.toList()), new TypeToken<List<ToolkitItemDto>>() {
			}.getType()));

			dto.setAvailableTools(this.service.findAvaliableItems(entity, Type.TOOL));
			dto.setBindedTools(this.mapper.map(entity.getItems().stream().filter(i -> i.getItem().getItemType().equals(Type.TOOL)).collect(Collectors.toList()), new TypeToken<List<ToolkitItemDto>>() {
			}.getType()));

			request.unbind(dto, model, BindHelper.getAllFieldNames(DetailToolkitDto.class));
		}
		model.setAttribute("draftMode", !entity.isPublished());
	}

}
