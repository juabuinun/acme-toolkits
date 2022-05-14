package acme.repositories;

import org.springframework.stereotype.Repository;

import acme.entities.configuration.Configuration;

@Repository
public interface ConfigurationRepository extends GenericJpaRepository<Configuration>{

	Configuration findFirstByOrderByIdAsc();
}
