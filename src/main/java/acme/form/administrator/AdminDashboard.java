package acme.form.administrator;

import java.util.List;

import acme.datatypes.CustomMoney;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminDashboard{

	protected String chimpumRatio;
	protected List<CustomMoney> chimpumMinBudget;
	protected List<CustomMoney> chimpumMaxBudget;
	protected List<CustomMoney> chimpumAvgBudget;
	protected List<CustomMoney> chimpumStdevBudget;
}
