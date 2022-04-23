package acme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import acme.entities.patronage.report.PatronageReport;

@Repository
public interface PatronageReportRepository extends JpaRepository<PatronageReport,Integer>,JpaSpecificationExecutor<PatronageReport>{

}
