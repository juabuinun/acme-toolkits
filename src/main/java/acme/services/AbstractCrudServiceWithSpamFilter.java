package acme.services;

import org.modelmapper.ModelMapper;

import acme.components.spam.SpamFilter;
import acme.framework.entities.AbstractEntity;
import acme.repositories.GenericJpaRepository;

public abstract class AbstractCrudServiceWithSpamFilter<E extends AbstractEntity, R extends GenericJpaRepository<E>> extends AbstractCrudServiceImpl<E,R>{

	protected SpamFilter spamFilter;
	
	protected AbstractCrudServiceWithSpamFilter(final R repo, final ModelMapper mapper, final SpamFilter spamFilter) {
		super(repo, mapper);
		this.spamFilter = spamFilter;
	}

}
