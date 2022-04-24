
package acme.features.patron.dashboard;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import acme.datatypes.CustomMoney;
import acme.entities.patronage.Patronage;

@Repository
@Transactional(readOnly = true)
public class PatronageDashboardRepositoryImpl implements PatronageDashboardRepository {

	@Autowired
	protected EntityManager entityManager;


	@Override
	public List<CustomMoney> findMaxBudgetByStatusGroupByCurrency(final Patronage.Status status) {
		final CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<CustomMoney> query = cb.createQuery(CustomMoney.class);
		final Root<Patronage> root = query.from(Patronage.class);

		final Expression<Double> expression = cb.max(root.get("budget").get("amount"));

		query.select(cb.construct(CustomMoney.class, expression,root.get("budget").get("currency")));
		query.where(cb.equal(root.get("status"), cb.literal(status)));
		query.groupBy(root.get("budget").get("currency"));
		query.orderBy(cb.desc(expression));
		return this.entityManager.createQuery(query).getResultList();
	}

	@Override
	public List<CustomMoney> findMinBudgetByStatusGroupByCurrency(final Patronage.Status status) {
		final CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<CustomMoney> query = cb.createQuery(CustomMoney.class);
		final Root<Patronage> root = query.from(Patronage.class);

		final Expression<Double> expression = cb.min(root.get("budget").get("amount"));

		query.select(cb.construct(CustomMoney.class, expression,root.get("budget").get("currency")));
		query.where(cb.equal(root.get("status"), cb.literal(status)));
		query.groupBy(root.get("budget").get("currency"));
		query.orderBy(cb.desc(expression));
		return this.entityManager.createQuery(query).getResultList();
	}

	@Override
	public List<CustomMoney> findAvgBudgetByStatusGroupByCurrency(final Patronage.Status status) {
		final CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		final CriteriaQuery<CustomMoney> query = cb.createQuery(CustomMoney.class);
		final Root<Patronage> root = query.from(Patronage.class);

		final Expression<Double> expression = cb.avg(root.get("budget").get("amount"));

		query.select(cb.construct(CustomMoney.class, expression,root.get("budget").get("currency")));
		query.where(cb.equal(root.get("status"), cb.literal(status)));
		query.groupBy(root.get("budget").get("currency"));
		query.orderBy(cb.desc(expression));
		return this.entityManager.createQuery(query).getResultList();
	}

	@Override
	public List<CustomMoney> findStdevBudgetByStatusGroupByCurrency(final Patronage.Status status) {
		return this.findAvgBudgetByStatusGroupByCurrency(status).stream().map(avg -> {
			final CustomMoney res = new CustomMoney();
			res.setCurrency(avg.getCurrency());
			final CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
			final CriteriaQuery<Double> query = cb.createQuery(Double.class);
			final Root<Patronage> root = query.from(Patronage.class);
			final Expression<Double> diffWithAvg = cb.diff(root.get("budget").get("amount"), avg.getAmount());

			query.select(cb.sum(cb.prod(diffWithAvg, diffWithAvg)));
			query.where(cb.and(cb.equal(root.get("budget").get("currency"),avg.getCurrency()),cb.equal(root.get("status"), status)));
			
			final Double sum = this.entityManager.createQuery(query).getSingleResult();
			
			final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
			final Root<Patronage> rootCount = countQuery.from(Patronage.class);
			countQuery.select(cb.count(rootCount));
			countQuery.where(cb.and(cb.equal(rootCount.get("budget").get("currency"),avg.getCurrency()),cb.equal(root.get("status"), status)));
			
			final Long count = this.entityManager.createQuery(countQuery).getSingleResult();
			
			res.setAmount(Math.sqrt(sum/count));
			return res;
		}).collect(Collectors.toList());
	}

}