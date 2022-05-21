
package acme.services.patronage;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.patronage.Patronage;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.repositories.PatronageRepository;
import acme.services.AbstractCrudServiceImpl;
import acme.services.config.AcmeConfigurationService;

@Service
public class PatronageService extends AbstractCrudServiceImpl<Patronage, PatronageRepository> {

	@Autowired
	protected AcmeConfigurationService configService;


	@Autowired
	protected PatronageService(final PatronageRepository repo) {
		super(repo);
	}

	public boolean isCodeUnique(final Patronage patronage) {
		boolean res=false;
		if(patronage.getId()!=0) 
			res = this.findById(patronage.getId()).getCode().equals(patronage.getCode());
			
		return res || this.repo.countByCode(patronage.getCode()) <= 0;
	}

	public void validate(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		this.configService.filter(request, entity, errors);
		this.configService.checkMoney(request, errors, entity.getBudget(), "budget");
		errors.state(request, entity.getCode() == null ? Boolean.TRUE : this.isCodeUnique(entity), "code", "errors.unique");
		errors.state(request, entity.getEndDate() == null ? Boolean.TRUE : entity.getEndDate().isAfter(entity.getCreationDate().plus(1, ChronoUnit.MONTHS)), "endDate", "errors.patronage.date");
	}
}
