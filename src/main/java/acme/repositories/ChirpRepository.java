package acme.repositories;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.stereotype.Repository;

import acme.entities.chirp.Chirp;

@Repository
public interface ChirpRepository extends GenericJpaRepository<Chirp>{

	Collection<Chirp> findByCreationDateAfter(LocalDateTime date);
}
