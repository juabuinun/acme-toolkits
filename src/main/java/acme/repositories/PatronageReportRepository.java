package acme.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.entities.patronagereport.PatronageReport;

@Repository
public interface PatronageReportRepository extends GenericJpaRepository<PatronageReport>{

	@Query("SELECT COUNT(pr) FROM PatronageReport pr LEFT JOIN pr.patronage p WHERE p.code = :code")
	long countByPatronageCode(@Param("code") String patronageCode);
}
