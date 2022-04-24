package acme.features.patron.dashboard;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import acme.datatypes.CustomMoney;
import acme.entities.patronage.Patronage.Status;



@NoRepositoryBean
public interface PatronageDashboardRepository {

	List<CustomMoney> findMaxBudgetByStatusGroupByCurrency(Status status);

	List<CustomMoney> findMinBudgetByStatusGroupByCurrency(Status status);

	List<CustomMoney> findAvgBudgetByStatusGroupByCurrency(Status status);

	List<CustomMoney> findStdevBudgetByStatusGroupByCurrency(Status status);
}
