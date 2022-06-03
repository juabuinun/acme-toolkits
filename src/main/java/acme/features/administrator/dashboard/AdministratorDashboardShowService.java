package acme.features.administrator.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.item.Item.Type;
import acme.form.administrator.AdminDashboard;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractShowService;
import acme.repositories.ItemRepository;
import acme.repositories.luster.LusterAdvancedRepository;
import acme.repositories.luster.LusterJpaRepository;

@Service
public class AdministratorDashboardShowService implements AbstractShowService<Administrator, AdminDashboard> {

	@Autowired
	protected AdministratorDashboardRepository repository;

	@Autowired 
	protected LusterAdvancedRepository chimpumRepo;
	
	@Autowired
	protected ItemRepository itemRepository;
	
	@Autowired
	protected LusterJpaRepository lusterRepo;

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
		
		final long itemCount = this.itemRepository.countByItemType(Type.COMPONENT);
		final long lusterCount = this.lusterRepo.count();
		if(itemCount > 0 && lusterCount >0) {
			res.setChimpumRatio(String.format("1:%o", (itemCount / lusterCount)));
		}else {
			res.setChimpumRatio("0");
		}
		res.setChimpumMinBudget(this.chimpumRepo.findMinBudget());
		res.setChimpumMaxBudget(this.chimpumRepo.findMaxBudget());
		res.setChimpumAvgBudget(this.chimpumRepo.findAvgBudget());
		res.setChimpumStdevBudget(this.chimpumRepo.findStdevBudget());
		return res;
	}


}