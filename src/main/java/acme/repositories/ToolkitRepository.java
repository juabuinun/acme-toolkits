package acme.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import acme.entities.toolkit.Toolkit;

@Repository
public interface ToolkitRepository extends GenericJpaRepository<Toolkit>{

	List<Toolkit> findByPublished(boolean published);
}
