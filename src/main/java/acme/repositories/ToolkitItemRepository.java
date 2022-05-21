package acme.repositories;

import org.springframework.stereotype.Repository;

import acme.entities.toolkit.Toolkit;
import acme.entities.toolkititem.ToolkitItem;

@Repository
public interface ToolkitItemRepository extends GenericJpaRepository<ToolkitItem>{

	void deleteByToolkit(Toolkit toolkit);
}
