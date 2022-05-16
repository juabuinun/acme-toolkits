package acme.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import acme.framework.controllers.Request;
import acme.framework.entities.AbstractEntity;
import acme.repositories.GenericJpaRepository;

public abstract class AbstractCrudServiceImpl<E extends AbstractEntity, R extends GenericJpaRepository<E>> implements AbstractCrudService<E> {

	private static final Logger logger = LoggerFactory.getLogger(AbstractCrudServiceImpl.class);
	
	protected R repo;
	
	protected ModelMapper mapper;
	
	protected AbstractCrudServiceImpl(final R repo,final ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	
	@Override
	@Transactional
	public E findById(final Request<E> request) {
		return this.findById(request.getModel().getInteger("id"));
	}

	@Override
	@Transactional
	public E findById(final int id) {
		final Optional<E> opt = this.repo.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		}else {
			AbstractCrudServiceImpl.logger.warn("Couldnt find entity with id={}", id);
			return null;
		}
	}

	@Override
	@Transactional
	public E save(final E entity) {
		return this.repo.save(entity);
	}

	@Override
	@Transactional
	public E update(final int id, final E entity) {
		final Optional<E> opt = this.repo.findById(id);
		if(opt.isPresent()) {
			final E persisted = opt.get();
			this.mapper.map(entity, persisted);
			return this.save(persisted);
		}else {
			AbstractCrudServiceImpl.logger.warn("Couldnt find entity with id={}", id);
			return null;
		}
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
