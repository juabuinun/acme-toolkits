package acme.repositories;

import org.springframework.stereotype.Repository;

import acme.roles.Patron;

@Repository
public interface PatronRepository extends GenericJpaRepository<Patron>{

}
