package acme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import acme.entities.patronage.Patronage;

@Repository
public interface PatronageRepository extends JpaRepository<Patronage,Integer>, JpaSpecificationExecutor<Patronage>{

}
