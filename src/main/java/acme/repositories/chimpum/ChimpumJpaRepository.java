package acme.repositories.chimpum;

import org.springframework.stereotype.Repository;

import acme.entities.chimpum.Chimpum;
import acme.repositories.GenericJpaRepository;

@Repository
public interface ChimpumJpaRepository extends GenericJpaRepository<Chimpum>{

}
