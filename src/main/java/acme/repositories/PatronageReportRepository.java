package acme.repositories;

import org.springframework.stereotype.Repository;

import acme.entities.patronagereport.PatronageReport;

@Repository
public interface PatronageReportRepository extends GenericJpaRepository<PatronageReport>{

}
