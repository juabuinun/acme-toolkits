package acme.features.patron.dashboard;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import acme.entities.patronage.Patronage;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.helpers.PrincipalHelper;
import acme.roles.Patron;

public class PatronDashboardSpecifications {

	private PatronDashboardSpecifications() {
		throw new IllegalStateException("Utility class");
	}
	
	public static Specification<Patronage> patronageByStatus(final Patronage.Status status) {
		return new Specification<Patronage>() {

			private static final long serialVersionUID = 2L;


			@Override
			public Predicate toPredicate(final Root<Patronage> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
				final Principal principal = PrincipalHelper.get();
				final Join<Patronage,Patron> patronagePatron = root.join("sponsor", JoinType.INNER);
				final Join<Patron,UserAccount> patronUser = patronagePatron.join("userAccount", JoinType.INNER);
				
				return cb.and(cb.equal(root.get("status"), cb.literal(status)),cb.equal(patronUser.get("id"), cb.literal(principal.getAccountId())));
			}

		};
	}

}
