package acme.repositories;

import org.springframework.stereotype.Repository;

import acme.entities.patronage.report.PatronageReport;

@Repository
public interface PatronageReportRepository extends GenericJpaRepository<PatronageReport>{

}
