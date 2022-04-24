
package acme.form.patron;

import java.io.Serializable;
import java.util.List;

import acme.datatypes.CustomMoney;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatronDashboard implements Serializable {

	private static final long	serialVersionUID	= -869910348310097983L;

	Long						numProposed;
	List<CustomMoney>			maxProposed;
	List<CustomMoney>			minProposed;
	List<CustomMoney>			avgProposed;
	List<CustomMoney>			stdevProposed;

	Long						numDenied;
	List<CustomMoney>			maxDenied;
	List<CustomMoney>			minDenied;
	List<CustomMoney>			avgDenied;
	List<CustomMoney>			stdevDenied;

	Long						numAccepted;
	List<CustomMoney>			maxAccepted;
	List<CustomMoney>			minAccepted;
	List<CustomMoney>			avgAccepted;
	List<CustomMoney>			stdevAccepted;
}
