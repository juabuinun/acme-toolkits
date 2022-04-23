package acme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import acme.entities.item.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item,Integer>,JpaSpecificationExecutor<Item>{

}
