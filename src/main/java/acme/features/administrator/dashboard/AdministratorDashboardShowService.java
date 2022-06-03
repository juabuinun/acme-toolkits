package acme.features.administrator.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.form.administrator.AdminDashboard;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractShowService;
import acme.repositories.chimpum.ChimpumAdvancedRepository;

@Service
public class AdministratorDashboardShowService implements AbstractShowService<Administrator, AdminDashboard> {

	@Autowired
	protected AdministratorDashboardRepository repository;

	@Autowired 
	protected ChimpumAdvancedRepository chimpumRepo;

	@Override
	public boolean authorise(final Request<AdminDashboard> request) {
		return true;
	}

	@Override
	public void unbind(final Request<AdminDashboard> request, final AdminDashboard entity, final Model model) {
		request.unbind(entity, model, "chimpumRatio","chimpumMinBudget","chimpumMaxBudget","chimpumAvgBudget","chimpumStdevBudget");
	}

	@Override
	public AdminDashboard findOne(final Request<AdminDashboard> request) {
		final AdminDashboard res = new AdminDashboard();
		res.setChimpumRatio("1:1");
		res.setChimpumMinBudget(this.chimpumRepo.findMinBudget());
		res.setChimpumMaxBudget(this.chimpumRepo.findMaxBudget());
		res.setChimpumAvgBudget(this.chimpumRepo.findAvgBudget());
		res.setChimpumStdevBudget(this.chimpumRepo.findStdevBudget());
		return res;
	}


}