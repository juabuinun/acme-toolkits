
package acme.services.toolkit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.Specifications;
import acme.components.util.BindHelper;
import acme.entities.item.Item;
import acme.entities.item.Item.Type;
import acme.entities.toolkit.Toolkit;
import acme.entities.toolkititem.ToolkitItem;
import acme.form.toolkit.SaveToolkitDto;
import acme.form.toolkit.ToolkitDto;
import acme.form.toolkititem.BasicToolkitItemDto;
import acme.form.toolkititem.ToolkitItemDto;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.repositories.ToolkitItemRepository;
import acme.repositories.ToolkitRepository;
import acme.services.AbstractCrudServiceImpl;
import acme.services.item.ItemService;

@Service
@Transactional
public class ToolkitService extends AbstractCrudServiceImpl<Toolkit, ToolkitRepository> {

	//	private static final Logger	logger	= LoggerFactory.getLogger(ToolkitService.class);

	@Autowired
	protected ItemService			itemService;

	@Autowired
	protected ModelMapper			mapper;

	@Autowired
	protected ToolkitItemRepository	toolkitItemRepo;


	@Autowired
	protected ToolkitService(final ToolkitRepository repo) {
		super(repo);
	}

	public List<Toolkit> findAllPublished() {
		return this.repo.findByPublished(true);
	}

	public List<Toolkit> findByItem(final Request<Toolkit> request, final Boolean published) {
		return this.findByItem(request.getModel().getInteger("itemId"), published);
	}

	public List<Toolkit> findByItem(final int itemId, final Boolean published) {
		final Item item = this.itemService.findById(itemId);
		if (item != null) {
			return this.repo.findAll(Specifications.toolkitHasItem(item, published));
		} else {
			return new ArrayList<>();
		}
	}

	public List<ToolkitItemDto> findAvaliableItems(final Toolkit entity, final Type itemType) {
		final List<Item> avaliableComponents = this.itemService.findBySpecification(Specifications.itemIsOfType(itemType, true)).stream().filter(i -> entity.getItems().stream().map(ToolkitItem::getItem).mapToInt(Item::getId).noneMatch(j -> j == i.getId()))
			.collect(Collectors.toList());
		return this.mapper.map(avaliableComponents.stream().map(ToolkitItem::new).collect(Collectors.toList()), new TypeToken<List<ToolkitItemDto>>() {
		}.getType());
	}

	public void addToolkitItems(final Toolkit toolkit, final String jsonArrayStr, final String prefix) throws JSONException {
		final ArrayList<BasicToolkitItemDto> toolkitItems = new ArrayList<>();

		final JSONArray jsonComponentArray = new JSONArray(jsonArrayStr);
		for (int i = 0; i < jsonComponentArray.length(); i++) {
			final JSONObject jsonObj = jsonComponentArray.getJSONObject(i);
			final BasicToolkitItemDto toolkitItem = new BasicToolkitItemDto();
			toolkitItem.setQuantity(jsonObj.getInt("quantity"));
			toolkitItem.setId(Integer.valueOf(jsonObj.getString("id").replace(prefix, "")));
			toolkitItems.add(toolkitItem);
		}

		for (final BasicToolkitItemDto toolkitItem : toolkitItems) {
			final ToolkitItem item = new ToolkitItem();
			item.setQuantity(toolkitItem.getQuantity());
			item.setItem(this.itemService.findById(toolkitItem.getId()));
			item.setToolkit(toolkit);

			toolkit.getItems().add(item);
		}

	}

	public void bind(final Request<Toolkit> request, final Toolkit entity, final Errors errors) {
		request.bind(entity, errors, BindHelper.getAllFieldNames(SaveToolkitDto.class));

		entity.getItems().clear();
		try {
			this.addToolkitItems(entity, request.getModel().getString("binded_components_input"), "component_");

		} catch (final Exception e) {
			errors.state(request, false, "*", "errors.toolkit.components");
		}
		try {
			this.addToolkitItems(entity, request.getModel().getString("binded_tools_input"), "tool_");
		} catch (final Exception e) {
			errors.state(request, false, "*", "errors.toolkit.tools");
		}
	}

	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		final ToolkitDto dto = this.mapper.map(entity, ToolkitDto.class);

		if (entity.isPublished()) {
			request.unbind(entity, model, BindHelper.getAllFieldNames(ToolkitDto.class));
		} else {

			final List<ToolkitItemDto> bindedComponents = this.mapper.map(entity.getItems().stream().filter(i -> i.getItem().getItemType().equals(Type.COMPONENT)).collect(Collectors.toList()), new TypeToken<List<ToolkitItemDto>>() {
			}.getType());

			final List<ToolkitItemDto> bindedTools = this.mapper.map(entity.getItems().stream().filter(i -> i.getItem().getItemType().equals(Type.TOOL)).collect(Collectors.toList()), new TypeToken<List<ToolkitItemDto>>() {
			}.getType());

			request.unbind(entity, model, BindHelper.getAllFieldNames(SaveToolkitDto.class));
			model.setAttribute("availableComponents", this.findAvaliableItems(entity, Type.COMPONENT));
			model.setAttribute("bindedComponents", bindedComponents);
			model.setAttribute("availableTools", this.findAvaliableItems(entity, Type.TOOL));
			model.setAttribute("bindedTools", bindedTools);
		}
		model.setAttribute("price", dto.getPrice());
		model.setAttribute("draftMode", !entity.isPublished());
	}

}
