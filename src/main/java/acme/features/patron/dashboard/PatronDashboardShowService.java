package acme.features.patron.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.patronage.Patronage;
import acme.form.patron.PatronDashboard;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.repositories.PatronageRepository;
import acme.roles.Patron;

@Service
public class PatronDashboardShowService implements AbstractShowService<Patron,PatronDashboard>{

	@Autowired
	protected PatronageRepository repo;
	@Autowired
	protected PatronageDashboardRepository advancedRepo;
	
	@Override
	public boolean authorise(final Request<PatronDashboard> request) {
		return true;
	}

	@Override
	public PatronDashboard findOne(final Request<PatronDashboard> request) {
		final PatronDashboard res = new PatronDashboard();
		res.setNumAccepted(this.repo.count(PatronDashboardSpecifications.patronageByStatus(Patronage.Status.ACCEPTED)));
		res.setMaxAccepted(this.advancedRepo.findMaxBudgetByStatusGroupByCurrency(Patronage.Status.ACCEPTED));
		res.setMinAccepted(this.advancedRepo.findMinBudgetByStatusGroupByCurrency(Patronage.Status.ACCEPTED));
		res.setAvgAccepted(this.advancedRepo.findAvgBudgetByStatusGroupByCurrency(Patronage.Status.ACCEPTED));
		res.setStdevAccepted(this.advancedRepo.findStdevBudgetByStatusGroupByCurrency(Patronage.Status.ACCEPTED));
		
		res.setNumDenied(this.repo.count(PatronDashboardSpecifications.patronageByStatus(Patronage.Status.DENIED)));
		res.setMaxDenied(this.advancedRepo.findMaxBudgetByStatusGroupByCurrency(Patronage.Status.DENIED));
		res.setMinDenied(this.advancedRepo.findMinBudgetByStatusGroupByCurrency(Patronage.Status.DENIED));
		res.setAvgDenied(this.advancedRepo.findAvgBudgetByStatusGroupByCurrency(Patronage.Status.DENIED));
		res.setStdevDenied(this.advancedRepo.findStdevBudgetByStatusGroupByCurrency(Patronage.Status.DENIED));
		
		res.setNumProposed(this.repo.count(PatronDashboardSpecifications.patronageByStatus(Patronage.Status.PROPOSED)));
		res.setMaxProposed(this.advancedRepo.findMaxBudgetByStatusGroupByCurrency(Patronage.Status.PROPOSED));
		res.setMinProposed(this.advancedRepo.findMinBudgetByStatusGroupByCurrency(Patronage.Status.PROPOSED));
		res.setAvgProposed(this.advancedRepo.findAvgBudgetByStatusGroupByCurrency(Patronage.Status.PROPOSED));
		res.setStdevProposed(this.advancedRepo.findStdevBudgetByStatusGroupByCurrency(Patronage.Status.PROPOSED));	
		
		return res;
	}

	@Override
	public void unbind(final Request<PatronDashboard> request, final PatronDashboard entity, final Model model) {
		request.unbind(entity, model, "numAccepted","avgAccepted","minAccepted","maxAccepted","stdevAccepted","numProposed","avgProposed","minProposed","maxProposed","stdevProposed","numDenied","avgDenied","minDenied","maxDenied","stdevDenied");
	}

}
