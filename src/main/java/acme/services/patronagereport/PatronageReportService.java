package acme.services.patronagereport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import acme.entities.patronagereport.PatronageReport;
import acme.repositories.PatronageReportRepository;
import acme.services.AbstractCrudServiceImpl;

@Service
@Transactional
public class PatronageReportService extends AbstractCrudServiceImpl<PatronageReport,PatronageReportRepository>{

	@Autowired
	protected PatronageReportService(final PatronageReportRepository repo) {
		super(repo);
	}

	public long getNextSerialNumber(final String patronageCode) {
		return this.repo.countByPatronageCode(patronageCode) + 1;
	}
	
}
