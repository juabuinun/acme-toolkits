package acme.services;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import acme.framework.controllers.Request;
import acme.framework.entities.AbstractEntity;

public interface  AbstractCrudService<E extends AbstractEntity> {

	E findById(final int id);
	
	E findById(final Request<E> request);
	
	E save(final E entity);
	
	void delete(final int id);
	
	//lists
	
	List<E> findAll();
	
	List<E> findBySpecification(final Specification<E> specification);

}
