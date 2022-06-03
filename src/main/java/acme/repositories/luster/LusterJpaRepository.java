package acme.repositories.luster;

import org.springframework.stereotype.Repository;

import acme.entities.luster.Luster;
import acme.repositories.GenericJpaRepository;

@Repository
public interface LusterJpaRepository extends GenericJpaRepository<Luster>{

	long countByItem_id(int id);
	
}
