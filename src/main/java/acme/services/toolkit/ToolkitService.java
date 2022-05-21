
package acme.services.toolkit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.components.Specifications;
import acme.entities.item.Item;
import acme.entities.item.Item.Type;
import acme.entities.toolkit.Toolkit;
import acme.entities.toolkititem.ToolkitItem;
import acme.form.toolkititem.ToolkitItemDto;
import acme.framework.controllers.Request;
import acme.repositories.ToolkitRepository;
import acme.services.AbstractCrudServiceImpl;
import acme.services.item.ItemService;

@Service
@Transactional
public class ToolkitService extends AbstractCrudServiceImpl<Toolkit, ToolkitRepository> {

	//	private static final Logger	logger	= LoggerFactory.getLogger(ToolkitService.class);

	@Autowired
	protected ItemService	itemService;

	@Autowired
	protected ModelMapper	mapper;


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
		final List<Item> avaliableComponents = this.itemService.findBySpecification(Specifications.itemIsOfType(itemType, true)).stream()
			.filter(i -> entity.getItems().stream().map(ToolkitItem::getItem).mapToInt(Item::getId).noneMatch(j -> j == i.getId())).collect(Collectors.toList());
		return this.mapper.map(avaliableComponents.stream().map(ToolkitItem::new).collect(Collectors.toList()), new TypeToken<List<ToolkitItemDto>>() {
		}.getType());
	}

	public List<ToolkitItem> jsonArrayToToolkitItemList(final Toolkit toolkit, final String jsonArrayStr, final String prefix) {
		ArrayList<ToolkitItem> res = new ArrayList<>();
		try {
			final JSONArray jsonArr = new JSONArray(jsonArrayStr);
			for (int i = 0; i < jsonArr.length(); i++) {
				final JSONObject jsonObj = jsonArr.getJSONObject(i);
				final ToolkitItem toolkitItem = new ToolkitItem();
				toolkitItem.setQuantity(jsonObj.getInt("quantity"));
				final String idStr = jsonObj.getString("id").replace(prefix, "");
				final Item item = this.itemService.findById(Integer.valueOf(idStr));
				if(item != null) {
					toolkitItem.setToolkit(toolkit);
					toolkitItem.setItem(item);
					res.add(toolkitItem);
				}
			}
		} catch (final Exception e) {
			res = null;
		}
		return res;
	}

}
