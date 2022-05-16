package acme.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import acme.entities.configuration.Configuration;

@Repository
public interface ConfigurationRepository extends GenericJpaRepository<Configuration>{

	Optional<Configuration> findFirstByOrderByIdAsc();
}
