package acme.repositories;

import org.springframework.stereotype.Repository;

import acme.entities.item.Item;

@Repository
public interface ItemRepository extends GenericJpaRepository<Item>{

}
