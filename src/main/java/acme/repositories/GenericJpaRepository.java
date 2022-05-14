package acme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import acme.framework.entities.AbstractEntity;

@NoRepositoryBean
public interface GenericJpaRepository<E extends AbstractEntity> extends JpaRepository<E,Integer>,JpaSpecificationExecutor<E> {

}
