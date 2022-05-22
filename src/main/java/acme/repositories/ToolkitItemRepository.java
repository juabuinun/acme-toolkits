package acme.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import acme.entities.item.Item.Type;
import acme.entities.toolkititem.ToolkitItem;

@Repository
public interface ToolkitItemRepository extends GenericJpaRepository<ToolkitItem>{

	List<ToolkitItem> findByToolkit_idAndItem_itemType(int toolkit_id,Type item_itemType);
}
