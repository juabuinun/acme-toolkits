package acme.repositories.luster;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import acme.datatypes.CustomMoney;

@NoRepositoryBean
public interface LusterAdvancedRepository {

	List<CustomMoney> findMaxBudget();

	List<CustomMoney> findMinBudget();

	List<CustomMoney> findAvgBudget();

	List<CustomMoney> findStdevBudget();
}
