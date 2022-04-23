package acme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import acme.entities.configuration.Configuration;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration,Integer>{

	Configuration findFirstByOrderByIdAsc();
}
