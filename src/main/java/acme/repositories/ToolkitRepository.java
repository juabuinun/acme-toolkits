package acme.repositories;

import org.springframework.stereotype.Repository;

import acme.entities.toolkit.Toolkit;

@Repository
public interface ToolkitRepository extends GenericJpaRepository<Toolkit>{

}
