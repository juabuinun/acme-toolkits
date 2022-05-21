package acme.repositories;

import org.springframework.stereotype.Repository;

import acme.entities.patronage.Patronage;

@Repository
public interface PatronageRepository extends GenericJpaRepository<Patronage>{

	long countByCode(String code);
}
