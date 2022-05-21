
package acme.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import acme.framework.controllers.Request;
import acme.framework.entities.AbstractEntity;
import acme.repositories.GenericJpaRepository;

public abstract class AbstractCrudServiceImpl<E extends AbstractEntity, R extends GenericJpaRepository<E>> implements AbstractCrudService<E> {

	private static final Logger	logger	= LoggerFactory.getLogger(AbstractCrudServiceImpl.class);

	protected R					repo;


	protected AbstractCrudServiceImpl(final R repo) {
		super();
		this.repo = repo;
	}

	@Override
	@Transactional
	public E findById(final Request<E> request) {
		try {
			return this.findById(request.getModel().getInteger("id"));
		} catch (final Exception e) {
			AbstractCrudServiceImpl.logger.error("id was missing from request model",e);
			return null;
		}
	}

	@Override
	@Transactional
	public E findById(final int id) {
		final Optional<E> opt = this.repo.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			AbstractCrudServiceImpl.logger.warn("Couldnt find entity with id={}", id);
			return null;
		}
	}

	@Override
	@Transactional
	public E save(final E entity) {
		return this.repo.saveAndFlush(entity);
	}

	@Override
	@Transactional
	public void delete(final int id) {
		this.repo.deleteById(id);
	}

	@Override
	@Transactional
	public List<E> findAll() {
		return this.repo.findAll();
	}

	@Override
	@Transactional
	public List<E> findBySpecification(final Specification<E> specification) {
		return this.repo.findAll(specification);
	}

}
