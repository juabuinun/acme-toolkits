package acme.repositories;

import org.springframework.stereotype.Repository;

import acme.entities.item.Item;
import acme.entities.item.Item.Type;

@Repository
public interface ItemRepository extends GenericJpaRepository<Item>{

	long countByItemType(Type itemType);
}
