package acme.features.patron.dashboard;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import acme.entities.patronage.Patronage;

public class PatronDashboardSpecifications {

	private PatronDashboardSpecifications() {
		throw new IllegalStateException("Utility class");
	}
	
	public static Specification<Patronage> patronageByStatus(final Patronage.Status status) {
		return new Specification<Patronage>() {

			private static final long serialVersionUID = 2L;


			@Override
			public Predicate toPredicate(final Root<Patronage> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
				return cb.equal(root.get("status"), cb.literal(status));
			}

		};
	}

}
