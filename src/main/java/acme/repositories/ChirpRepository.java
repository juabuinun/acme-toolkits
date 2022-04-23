package acme.repositories;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import acme.entities.chirp.Chirp;

@Repository
public interface ChirpRepository extends JpaRepository<Chirp,Integer>{

	Collection<Chirp> findByCreationDateAfter(LocalDateTime date);
}
