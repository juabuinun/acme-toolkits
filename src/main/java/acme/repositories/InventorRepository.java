package acme.repositories;

import org.springframework.stereotype.Repository;

import acme.roles.Inventor;

@Repository
public interface InventorRepository extends GenericJpaRepository<Inventor>{

}
