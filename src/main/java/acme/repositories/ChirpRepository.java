package acme.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import acme.entities.chirp.Chirp;

@Repository
public interface ChirpRepository extends GenericJpaRepository<Chirp>{

	List<Chirp> findByCreationDateAfter(LocalDateTime date);
}
